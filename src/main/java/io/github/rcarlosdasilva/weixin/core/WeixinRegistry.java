package io.github.rcarlosdasilva.weixin.core;

import com.google.common.base.Preconditions;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.holder.SimpleRedisHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCacheHandler;
import io.github.rcarlosdasilva.weixin.core.registry.Setting;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.OpenPlatform;

/**
 * 微信公众号注册器，支持多公众号
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WeixinRegistry {

  private static Registration registration = Registration.getInstance();

  private WeixinRegistry() {
  }

  public static void withSetting(Setting setting) {
    Preconditions.checkNotNull(setting);
    registration.setSetting(setting);

    if (setting.isUseRedisCache() && !setting.isUseSpringRedis()) {
      SimpleRedisHandler.init(setting.getRedisSetting());
    }

    // 如果使用Redis缓存（普通Redis配置或Spring的Redis配置），则将之前注册到Map中（默认使用Map）的公众号信息迁移到Redis中
    if (setting.isUseRedisCache()) {
      AccountCacheHandler.getInstance().migrateAccount();
    }
  }

  /**
   * 注册开放平台信息，注册后，所有的公众号将统归于开放平台代理.
   * 
   * @param appId
   *          appid
   * @param appSecret
   *          appsecret
   * @param token
   *          加密token
   * @param aesKey
   *          加密aesKey
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
