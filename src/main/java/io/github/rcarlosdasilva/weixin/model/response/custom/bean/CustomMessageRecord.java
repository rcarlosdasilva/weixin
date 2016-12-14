package io.github.rcarlosdasilva.weixin.model.response.custom.bean;

import com.google.gson.annotations.SerializedName;

public class CustomMessageRecord {

  @SerializedName("openid")
  private String openId;
  @SerializedName("opercode")
  private int operationCode;
  private String text;
  private long time;
  @SerializedName("worker")
  private String account;

  /**
   * 关注用户open_id.
   * 
   * @return open_id
   */
  public String getOpenId() {
    return openId;
  }

  /**
   * 操作码，2002（客服发送信息），2003（客服接收消息）.
   * 
   * @return code
   */
  public int getOperationCode() {
    return operationCode;
  }

  /**
   * 聊天记录内容.
   * 
   * @return text
   */
  public String getText() {
    return text;
  }

  /**
   * 操作时间，unix时间戳.
   * 
   * @return time
   */
  public long getTime() {
    return time;
  }

  /**
   * 完整客服帐号，格式为：帐号前缀@公众号微信号.
   * 
   * @return account
   */
  public String getAccount() {
    return account;
  }

}
