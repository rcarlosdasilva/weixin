package io.github.rcarlosdasilva.weixin.model.request.custom;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 创建客服会话请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CustomSessionCreateRequest extends BasicRequest {

  @SerializedName("kf_account")
  private String account;
  @SerializedName("openid")
  private String openId;

  public CustomSessionCreateRequest() {
    this.path = ApiAddress.URL_CUSTOM_SESSION_CREATE;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
