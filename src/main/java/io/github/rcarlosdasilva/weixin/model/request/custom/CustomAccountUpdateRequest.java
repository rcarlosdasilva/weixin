package io.github.rcarlosdasilva.weixin.model.request.custom;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 更新客服信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@SuppressWarnings("unused")
public class CustomAccountUpdateRequest extends BasicRequest {

  @SerializedName("kf_account")
  private String account;
  private String nickname;

  public CustomAccountUpdateRequest() {
    this.path = ApiAddress.URL_CUSTOM_ACCOUNT_UPDATE;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

}
