package io.github.rcarlosdasilva.weixin.model.request.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 网页授权Token刷新请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WaAccessTokenRefreshRequest extends BasicRequest {

  private String appId;
  private String refreshToken;

  public WaAccessTokenRefreshRequest() {
    this.path = ApiAddress.URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_REFRESH;
  }

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
   * 刷新Token.
   * 
   * @param refreshToken
   *          refresh_token
   */
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?appid=").append(this.appId).append("&grant_type=")
        .append(Convention.WEB_AUTHORIZE_ACCESS_TOKEN_REFRESH_GRANT_TYPE).append("&refresh_token=")
        .append(this.refreshToken).toString();
  }

}
