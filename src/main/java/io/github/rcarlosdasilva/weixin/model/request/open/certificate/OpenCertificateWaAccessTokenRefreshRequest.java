package io.github.rcarlosdasilva.weixin.model.request.open.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

public class OpenCertificateWaAccessTokenRefreshRequest extends BasicOpenPlatformRequest {

  private String appId;
  private String refreshToken;
  private String openPlatformAppId;

  public OpenCertificateWaAccessTokenRefreshRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_WEB_AUTHORIZE_TOKEN_REFRESH;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void setOpenPlatformAppId(String openPlatformAppId) {
    this.openPlatformAppId = openPlatformAppId;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?appid=").append(this.appId)
        .append("&grant_type=refresh_token&component_appid=").append(this.openPlatformAppId)
        .append("&component_access_token=").append(this.accessToken).append("&refresh_token=")
        .append(this.refreshToken).toString();
  }

}
