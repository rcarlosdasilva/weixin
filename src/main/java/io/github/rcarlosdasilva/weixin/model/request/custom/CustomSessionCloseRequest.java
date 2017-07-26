package io.github.rcarlosdasilva.weixin.model.request.custom;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 关闭客服会话请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CustomSessionCloseRequest extends BasicWeixinRequest {

  @SerializedName("kf_account")
  private String account;
  @SerializedName("openid")
  private String openId;

  public CustomSessionCloseRequest() {
    this.path = ApiAddress.URL_CUSTOM_SESSION_CLOSE;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
