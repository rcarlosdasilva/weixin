package io.github.rcarlosdasilva.weixin.core;

import com.google.common.base.Preconditions;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.holder.SimpleRedisHandler;
import io.github.rcarlosdasilva.weixin.core.registry.Configuration;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.OpenPlatform;

/**
 * 微信公众号注册器，支持多公众号
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WeixinRegistry {

  private static final Configuration DEFAULT_CONFIG = new Configuration();

  private static Registration registration = Registration.getInstance();

  private WeixinRegistry() {
  }

  public static void withConfig(Configuration configuration) {
    Preconditions.checkNotNull(configuration);
    registration.setConfiguration(configuration);
  }

  public static void withDefaultConfig() {
    withConfig(DEFAULT_CONFIG);
  }

  public static void done() {
    Preconditions.checkNotNull(registration);
    Preconditions.checkNotNull(registration.getConfiguration());

    if (registration.getConfiguration().isUseRedisCache()
        && !registration.getConfiguration().isUseSpringRedis()) {
      SimpleRedisHandler.init(registration.getConfiguration().getRedisConfiguration());
    }

    registration.process();
  }

  /**
   * 注册开放平台信息，注册后，所有的公众号将统归于开放平台代理.
   * 
   * @param appId
   *          appid
   * @param appSecret
   *          appsecret
   */
  public static void openPlatform(String appId, String appSecret, String token, String aesKey) {
    registration.setOpenPlatform(new OpenPlatform(appId, appSecret, token, aesKey));
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
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(account);
    account.setKey(key);
    registration.addAccount(account);

    return account;
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

}
