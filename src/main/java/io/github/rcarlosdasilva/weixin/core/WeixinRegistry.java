package io.github.rcarlosdasilva.weixin.core;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccessTokenCache;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCache;
import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 微信公众号注册器，支持多公众号
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WeixinRegistry {

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
  public static Account registry(String key, String appId, String appSecret) {
    AccountCache.instance().remove(key);
    AccessTokenCache.instance().remove(key);
    return AccountCache.instance().put(key, new Account(appId, appSecret));
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
  public static Account registryUnique(String appId, String appSecret) {
    return registry(Convention.DEFAULT_UNIQUE_WEIXIN_KEY, appId, appSecret);
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
    return AccountCache.instance().lookup(id);
  }

}
