package io.github.rcarlosdasilva.weixin.model.request.open.auth;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicOpenPlatformRequest;

/**
 * 获取第三方平台component_access_token请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class OpenPlatformAuthAccessTokenRequest extends BasicOpenPlatformRequest {

  @SerializedName("component_appid")
  private String appId;
  @SerializedName("component_appsecret")
  private String appSecret;
  @SerializedName("component_verify_ticket")
  private String ticket;

  public OpenPlatformAuthAccessTokenRequest() {
    this.path = ApiAddress.URL_OPEN_PLATFORM_ACCESS_TOKEN;
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
   * 第三方平台appsecret.
   * 
   * @param appSecret
   *          appsecret
   */
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  /**
   * 微信后台推送的ticket，此ticket会定时推送.
   * 
   * @param ticket
   *          ticket
   */
  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  @Override
  public String toString() {
    return this.path;
  }

}
