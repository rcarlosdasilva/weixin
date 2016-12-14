package io.github.rcarlosdasilva.weixin.model.response.custom;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomSession;

public class CustomSessionStatusResponse extends CustomSession {

  @SerializedName("kf_account")
  private String account;
  @SerializedName("createtime")
  private long createTime;

  /**
   * 正在接待的客服，为空表示没有人在接待.
   * 
   * @return account
   */
  public String getAccount() {
    return account;
  }

  /**
   * 会话接入的时间.
   * 
   * @return time
   */
  public long getCreateTime() {
    return createTime;
  }

}
