package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

/**
 * 获取预授权码pre_auth_code请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class OpenPlatformAuthPreAuthCodeRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;

  public OpenPlatformAuthPreAuthCodeRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_PRE_AUTH_CODE;
  }

  /**
   * 第三方平台方appid
   * 
   * @param appId
   *          appid
   */
  public void setAppId(String appId) {
    this.appId = appId;
  }

}
