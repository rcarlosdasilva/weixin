package io.github.rcarlosdasilva.weixin.model.response.custom.bean;

import com.google.gson.annotations.SerializedName;

public class CustomSession {

  @SerializedName("kf_account")
  private String account;
  @SerializedName("openid")
  private String openId;
  @SerializedName("createtime")
  private long createTime;
  @SerializedName("latest_time")
  private long latestTime;

  /** 完整客服帐号，格式为：帐号前缀@公众号微信号. */
  public String getAccount() {
    return account;
  }

  /** 关注的用户open_id. */
  public String getOpenId() {
    return openId;
  }

  /** 会话接入的时间. */
  public long getCreateTime() {
    return createTime;
  }

  /** 关注用户的最后一条消息的时间. */
  public long getLatestTime() {
    return latestTime;
  }

}
