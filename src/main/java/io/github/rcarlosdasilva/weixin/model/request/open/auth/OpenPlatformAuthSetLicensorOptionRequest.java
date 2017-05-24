package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

/**
 * 设置授权方选项信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class OpenPlatformAuthSetLicensorOptionRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;
  @SerializedName("authorizer_appid")
  private String licensorAppId;
  @SerializedName("option_name")
  private String optionName;
  @SerializedName("option_value")
  private String value;

  public OpenPlatformAuthSetLicensorOptionRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_SET_LICENSOR_OPTION;
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

  /**
   * 选项名称.
   * 
   * @param optionName
   *          name
   */
  public void setOptionName(String optionName) {
    this.optionName = optionName;
  }

  /**
   * 设置的选项值.
   * 
   * @param value
   *          value
   */
  public void setValue(String value) {
    this.value = value;
  }

}
