package io.github.rcarlosdasilva.weixin.model.request.custom;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 关闭客服会话请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CustomSessionCloseRequest extends BasicRequest {

  @SerializedName("kf_account")
  private String account;
  @SerializedName("openid")
  private String openId;

  public CustomSessionCloseRequest() {
    this.path = ApiAddress.URL_CUSTOM_SESSION_CLOSE;
  }

  /**
   * 账号.
   */
  public void setAccount(String account) {
    this.account = account;
  }

  /**
   * 用户OpenId.
   */
  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
