package io.github.rcarlosdasilva.weixin.model.request.open.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

public class OpenCertificateWaAccessTokenRequest extends BasicOpenPlatformRequest {

  private String appId;
  private String code;
  private String openPlatformAppId;

  public OpenCertificateWaAccessTokenRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_WEB_AUTHORIZE_TOKEN;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setOpenPlatformAppId(String openPlatformAppId) {
    this.openPlatformAppId = openPlatformAppId;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?appid=").append(this.appId).append("&code=")
        .append(this.code).append("&grant_type=authorization_code&component_appid=")
        .append(this.openPlatformAppId).append("&component_access_token=").append(this.accessToken)
        .toString();
  }

}
