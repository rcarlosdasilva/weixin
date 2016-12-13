package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import com.google.gson.annotations.SerializedName;

public class CustomService {

  @SerializedName("kf_account")
  private String account;

  public void setAccount(String account) {
    this.account = account;
  }

}
