package io.github.rcarlosdasilva.weixin.core.listener;

/**
 * 公众号AccessToken更新监听器
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface AccessTokenUpdatedListener extends WeixinListener {

  /**
   * access_token更新.
   * 
   * @param key
   *          注册时的key
   * @param appid
   *          公众号appid
   * @param token
   *          凭证
   * @param expiredIn
   *          凭证有效时间，单位：秒
   */
  void updated(String key, String appid, String token, long expiredIn);

}
