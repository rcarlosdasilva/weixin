package io.github.rcarlosdasilva.weixin.model.request.custom;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 邀请绑定客服账号请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CustomAccountBindingRequest extends BasicRequest {

  @SerializedName("kf_account")
  private String account;
  @SerializedName("invite_wx")
  private String wxId;

  public CustomAccountBindingRequest() {
    this.path = ApiAddress.URL_CUSTOM_ACCOUNT_INVITE_BINDING;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setWxId(String wxId) {
    this.wxId = wxId;
  }

}
