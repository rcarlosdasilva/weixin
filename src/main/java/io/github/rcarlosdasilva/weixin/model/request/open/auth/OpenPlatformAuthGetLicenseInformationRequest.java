package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

/**
 * 获取接口调用凭据和授权信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class OpenPlatformAuthGetLicenseInformationRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;
  @SerializedName("authorization_code")
  private String authCode;

  public OpenPlatformAuthGetLicenseInformationRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_LICENSE_INFORMATION;
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
   * 授权code,会在授权成功时返回给第三方平台，详见第三方平台授权流程说明.
   * 
   * @param authCode
   *          auth_code
   */
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

}
