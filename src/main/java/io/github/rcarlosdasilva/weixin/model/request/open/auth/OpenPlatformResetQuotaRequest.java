package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

public class OpenPlatformResetQuotaRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;

  public OpenPlatformResetQuotaRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_RESET_QUOTA;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

}
