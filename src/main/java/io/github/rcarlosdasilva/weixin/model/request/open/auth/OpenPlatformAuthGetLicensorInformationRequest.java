package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

public class OpenPlatformAuthGetLicensorInformationRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;
  @SerializedName("authorizer_appid")
  private String licensorAppId;

  public OpenPlatformAuthGetLicensorInformationRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_LICENSOR_INFORMATION;
  }

  /**
   * 第三方平台appid.
   * 
   * @param appId
   *          appid
   */
  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * 授权公众号或小程序的appid.
   * 
   * @param licensorAppId
   *          licensor appid
   */
  public void setLicensorAppId(String licensorAppId) {
    this.licensorAppId = licensorAppId;
  }

}