package io.github.rcarlosdasilva.weixin.model.response.custom.bean;

import com.google.gson.annotations.SerializedName;

public class CustomAccount {

  @SerializedName("kf_id")
  private int id;
  @SerializedName("kf_account")
  private String account;
  @SerializedName("kf_nick")
  private String nickname;
  @SerializedName("kf_headimgurl")
  private String avatar;
  private int status;
  @SerializedName("invite_wx")
  private String invitee;
  @SerializedName("invite_expire_time")
  private String inviteExpire;
  @SerializedName("invite_status")
  private String inviteStatus;
  @SerializedName("accepted_case")
  private int acceptedCase;

  /**
   * 客服编号.
   * 
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * 完整客服帐号，格式为：帐号前缀@公众号微信号.
   * 
   * @return account
   */
  public String getAccount() {
    return account;
  }

  /**
   * 客服昵称.
   * 
   * @return nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * 客服头像.
   * 
   * @return avatar
   */
  public String getAvatar() {
    return avatar;
  }

  /**
   * 客服在线状态，目前为：1、web 在线.
   * 
   * @return status
   */
  public int getStatus() {
    return status;
  }

  /**
   * 如果客服帐号尚未绑定微信号，但是已经发起了一个绑定邀请，则此处显示绑定邀请的微信号.
   * 
   * @return invitee
   */
  public String getInvitee() {
    return invitee;
  }

  /**
   * 如果客服帐号尚未绑定微信号，但是已经发起过一个绑定邀请，邀请的过期时间，为unix 时间戳.
   * 
   * @return expire
   */
  public String getInviteExpire() {
    return inviteExpire;
  }

  /**
   * 邀请的状态，有等待确认“waiting”，被拒绝“rejected”，过期“expired”.
   * 
   * @return invite status
   */
  public String getInviteStatus() {
    return inviteStatus;
  }

  /**
   * 客服当前正在接待的会话数.
   * 
   * @return case
   */
  public int getAcceptedCase() {
    return acceptedCase;
  }

}
