package io.github.rcarlosdasilva.weixin.model.notification;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationType;

public class NotificationMeta {

  @XStreamAlias("ToUserName")
  private String toUser;
  @XStreamAlias("FromUserName")
  private String fromUser;
  @XStreamAlias("CreateTime")
  private long time;
  @XStreamAlias("MsgType")
  private String type;
  @XStreamAlias("Encrypt")
  private String ciphertext;

  /**
   * 开发者微信号 (ToUserName).
   */
  public String getToUser() {
    return toUser;
  }

  /**
   * 发送方帐号（一个OpenID）(FromUserName).
   */
  public String getFromUser() {
    return fromUser;
  }

  /**
   * 消息创建时间 （整型）(CreateTime).
   */
  public Date getTime() {
    return new Date(time);
  }

  /**
   * 消息类型 (MsgType).
   */
  public NotificationType getType() {
    try {
      return NotificationType.byValue(type);
    } catch (Exception ex) {
      return null;
    }
  }

  /**
   * 获取密文.
   */
  public String getCiphertext() {
    return ciphertext;
  }

}
