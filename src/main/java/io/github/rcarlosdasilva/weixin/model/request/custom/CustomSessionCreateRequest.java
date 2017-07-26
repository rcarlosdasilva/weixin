package io.github.rcarlosdasilva.weixin.model.request.custom;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 创建客服会话请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CustomSessionCreateRequest extends BasicWeixinRequest {

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
