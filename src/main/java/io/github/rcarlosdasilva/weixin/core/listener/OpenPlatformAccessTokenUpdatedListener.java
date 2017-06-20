package io.github.rcarlosdasilva.weixin.core.listener;

/**
 * 开放平台AccessToken更新监听器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface OpenPlatformAccessTokenUpdatedListener extends WeixinListener {

  /**
   * access_token更新.
   * 
   * @param token
   *          第三方平台access_token
   * @param expiredIn
   *          有效期
   */
  void updated(String token, long expiredIn);

}
