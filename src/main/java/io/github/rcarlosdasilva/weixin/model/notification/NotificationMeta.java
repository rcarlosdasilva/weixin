package io.github.rcarlosdasilva.weixin.model.notification;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationInfoType;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationMessageType;

public class NotificationMeta {

  @XStreamAlias("AppId")
  private String appId;
  @XStreamAlias("ToUserName")
  private String toUser;
  @XStreamAlias("FromUserName")
  private String fromUser;
  @XStreamAlias("CreateTime")
  private long time;
  @XStreamAlias("MsgType")
  private String messageType;
  @XStreamAlias("InfoType")
  private String infoType;
  @XStreamAlias("Encrypt")
  private String ciphertext;

  /**
   * appid.
   * <p>
   * 公众号平台的通知：appid = 公众号appid<br>
   * 开放平台的通知：appid = 第三方appid
   * 
   * @return appid
   */
  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * 开发者微信号 (ToUserName).
   * 
   * @return to user
   */
  public String getToUser() {
    return toUser;
  }

  /**
   * 发送方帐号（一个OpenID）(FromUserName).
   * 
   * @return from user
   */
  public String getFromUser() {
    return fromUser;
  }

  /**
   * 消息创建时间 （整型）(CreateTime).
   * 
   * @return time
   */
  public Date getTime() {
    return new Date(time);
  }

  /**
   * 消息类型 (MsgType).
   * 
   * @return {@link NotificationMessageType}
   */
  public NotificationMessageType getMessageType() {
    return NotificationMessageType.byValue(messageType);
  }

  /**
   * 消息类型 (MsgType).
   * 
   * @return {@link NotificationMessageType}
   */
  public NotificationInfoType getInfoType() {
    return NotificationInfoType.byValue(infoType);
  }

  /**
   * 获取密文.
   * 
   * @return 密文
   */
  public String getCiphertext() {
    return ciphertext;
  }

}
