package io.github.rcarlosdasilva.weixin.core.listener;

/**
 * 开放平台中授权方AccessToken更新监听器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface OpenPlatformLisensorAccessTokenUpdatedListener extends WeixinListener {

  /**
   * 授权方access_token更新.
   * 
   * @param appid
   *          授权方appid
   * @param token
   *          授权方令牌
   * @param refresh
   *          刷新令牌
   * @param expiredIn
   *          有效期
   */
  void updated(String appid, String token, String refresh, long expiredIn);

}
