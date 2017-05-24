package io.github.rcarlosdasilva.weixin.model.response.open.auth;

import com.google.gson.annotations.SerializedName;

public class OpenPlatformAuthGetLicensorOptionResponse {

  @SerializedName("authorizer_appid")
  private String licensorAppId;
  @SerializedName("option_name")
  private String optionName;
  @SerializedName("option_value")
  private String value;

  /**
   * 授权公众号或小程序的appid.
   * 
   * @return licensor appid
   */
  public String getLicensorAppId() {
    return licensorAppId;
  }

  /**
   * 选项名称.
   * 
   * @return name
   */
  public String getOptionName() {
    return optionName;
  }

  /**
   * 选项值.
   * 
   * @return value
   */
  public String getValue() {
    return value;
  }

}
