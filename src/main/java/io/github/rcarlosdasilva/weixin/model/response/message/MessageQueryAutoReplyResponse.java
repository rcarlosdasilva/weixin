package io.github.rcarlosdasilva.weixin.model.response.message;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.response.message.bean.AutoReply4AddedFriendInfo;
import io.github.rcarlosdasilva.weixin.model.response.message.bean.AutoReply4KeywordsInfo;
import io.github.rcarlosdasilva.weixin.model.response.message.bean.AutoReply4ReceivedMessageInfo;

public class MessageQueryAutoReplyResponse {

  @SerializedName("is_add_friend_reply_open")
  private int isEnableWhenAddedFriend;
  @SerializedName("is_autoreply_open")
  private int isEnableWhenReceivedMessage;
  @SerializedName("add_friend_autoreply_info")
  private AutoReply4AddedFriendInfo addedFriendInfo;
  @SerializedName("message_default_autoreply_info")
  private AutoReply4ReceivedMessageInfo receivedMessageInfo;
  @SerializedName("keyword_autoreply_info")
  private AutoReply4KeywordsInfo keywordsInfo;

  /**
   * 关注后自动回复是否开启，0代表未开启，1代表开启.
   */
  public boolean isEnableWhenAddedFriend() {
    return isEnableWhenAddedFriend == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 消息自动回复是否开启，0代表未开启，1代表开启.
   */
  public boolean isEnableWhenReceivedMessage() {
    return isEnableWhenReceivedMessage == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 关注后自动回复的信息.
   */
  public AutoReply4AddedFriendInfo getAddedFriendInfo() {
    return addedFriendInfo;
  }

  /**
   * 消息自动回复的信息.
   */
  public AutoReply4ReceivedMessageInfo getReceivedMessageInfo() {
    return receivedMessageInfo;
  }

  /**
   * 关键词自动回复的信息.
   */
  public AutoReply4KeywordsInfo getKeywordsInfo() {
    return keywordsInfo;
  }

}
