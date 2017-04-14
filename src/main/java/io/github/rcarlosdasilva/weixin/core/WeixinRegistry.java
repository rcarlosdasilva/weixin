package io.github.rcarlosdasilva.weixin.core;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.holder.SimpleRedisHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccessTokenCacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCacheHandler;
import io.github.rcarlosdasilva.weixin.core.config.Configuration;
import io.github.rcarlosdasilva.weixin.core.exception.UnmakableAccountKeyException;
import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 微信公众号注册器，支持多公众号
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WeixinRegistry {

  private static Configuration configuration = Configuration.DEFAULT_CONFIG;
  private static boolean initialized = false;

  public static void config(Configuration configuration) {
    WeixinRegistry.configuration = configuration;
  }

  public static Configuration getConfiguration() {
    return configuration;
  }

  private static void init() {
    if (initialized) {
      return;
    }

    if (configuration.isUseRedisCache() && !configuration.isUseSpringRedis()) {
      SimpleRedisHandler.init(configuration.getRedisConfiguration());
    }

    initialized = true;
  }

  /**
   * 注册一个公众号配置.
   * 
   * @param key
   *          公众号键，在之后调用接口时，通过该键决定使用哪一个公众号
   * @param account
   *          公众号
   * @return {@link Account}
   */
  public static Account register(String key, Account account) {
    if (Strings.isNullOrEmpty(key)) {
      throw new UnmakableAccountKeyException();
    }

    init();
    unregister(key);

    return AccountCacheHandler.getInstance().put(key, account);
  }

  /**
   * 注册一个公众号配置.
   * 
   * <p>
   * 具体配置可以修改{@link Account}
   * 
   * @param key
   *          公众号键，在之后调用接口时，通过该键决定使用哪一个公众号
   * @param appId
   *          appid
   * @param appSecret
   *          appsecret
   * @return {@link Account}
   */
  public static Account register(String key, String appId, String appSecret) {
    return register(key, new Account(appId, appSecret));
  }

  /**
   * 注册一个唯一的公众号配置.
   * 
   * <p>
   * 当只需要操作一个公众号时，可使用该方法，使用 {@link Weixin#withUnique()}获取注册的配置.
   * 
   * @param account
   *          公众号
   * @return {@link Account}
   */
  public static Account registerUnique(Account account) {
    return register(Convention.DEFAULT_UNIQUE_WEIXIN_KEY, account);
  }

  /**
   * 注册一个唯一的公众号配置.
   * 
   * <p>
   * 当只需要操作一个公众号时，可使用该方法，使用 {@link Weixin#withUnique()}获取注册的配置.
   * 
   * @param appId
   *          appid
   * @param appSecret
   *          appsecret
   * @return {@link Account}
   */
  public static Account registerUnique(String appId, String appSecret) {
    return register(Convention.DEFAULT_UNIQUE_WEIXIN_KEY, appId, appSecret);
  }

  public static void unregister(String key) {
    if (Strings.isNullOrEmpty(key)) {
      throw new UnmakableAccountKeyException();
    }

    AccountCacheHandler.getInstance().remove(key);
    AccessTokenCacheHandler.getInstance().remove(key);
  }

  public static void unregisterUnique() {
    unregister(Convention.DEFAULT_UNIQUE_WEIXIN_KEY);
  }

  /**
   * 通过appId或原始ID获取账号配置信息.
   * 
   * @param id
   *          可以为公众号appId或者公众号原始ID
   * @return {@link Account}
   */
  public static Account lookup(String id) {
    id = id == null ? "" : id;
    Account account = new Account(id, null);
    account.setMpId(id);
    String key = AccountCacheHandler.getInstance().lookup(account);
    return AccountCacheHandler.getInstance().get(key);
  }

}
