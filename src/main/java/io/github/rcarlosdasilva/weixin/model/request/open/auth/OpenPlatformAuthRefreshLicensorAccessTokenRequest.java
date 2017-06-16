package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

/**
 * 刷新授权方access_token请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class OpenPlatformAuthRefreshLicensorAccessTokenRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;
  @SerializedName("authorizer_appid")
  private String licensorAppId;
  @SerializedName("authorizer_refresh_token")
  private String licensorRefreshToken;

  public OpenPlatformAuthRefreshLicensorAccessTokenRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_REFRESH_LICENSOR_ACCESS_TOKEN;
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
   * 授权方appid.
   * 
   * @param licensorAppId
   *          licensor appid
   */
  public void setLicensorAppId(String licensorAppId) {
    this.licensorAppId = licensorAppId;
  }

  /**
   * 授权方的刷新令牌，刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，
   * 只会在授权时刻提供，请妥善保存。一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌.
   * 
   * @param licensorRefreshToken
   *          刷新token
   */
  public void setLicensorRefreshToken(String licensorRefreshToken) {
    this.licensorRefreshToken = licensorRefreshToken;
  }

}
