package io.github.rcarlosdasilva.weixin.model.request.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 网页授权Token请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WaAccessTokenRequest extends BasicRequest {

  private String appId;
  private String appSecret;
  private String code;

  public WaAccessTokenRequest() {
    this.path = ApiAddress.URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN;
  }

  /**
   * AppId.
   */
  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * AppSecret.
   */
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  /**
   * Code.
   */
  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?appid=").append(this.appId).append("&secret=")
        .append(this.appSecret).append("&code=").append(this.code).append("&grant_type=")
        .append(Convention.WEB_AUTHORIZE_ACCESS_TOKEN_GRANT_TYPE).toString();
  }

}
