package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

/**
 * 获取授权方选项信息请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class OpenPlatformAuthGetLicensorOptionRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;
  @SerializedName("authorizer_appid")
  private String licensorAppId;
  @SerializedName("option_name")
  private String optionName;

  public OpenPlatformAuthGetLicensorOptionRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_GET_LICENSOR_OPTION;
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

}
