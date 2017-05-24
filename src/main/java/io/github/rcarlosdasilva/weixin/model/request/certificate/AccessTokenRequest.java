package io.github.rcarlosdasilva.weixin.model.request.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 微信Token请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AccessTokenRequest extends BasicWeixinRequest {

  private static final String grantType = Convention.ACCESS_TOKEN_GRANT_TYPE;
  private String appId;
  private String appSecret;

  /**
   * AppId.
   * 
   * @param appId
   *          appid
   */
  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * AppSecret.
   * 
   * @param appSecret
   *          appsecret
   */
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  @Override
  public String toString() {
    return new StringBuilder(ApiAddress.URL_CERTIFICATE_TOKEN).append("?grant_type=")
        .append(grantType).append("&appid=").append(this.appId).append("&secret=")
        .append(this.appSecret).toString();
  }

}
