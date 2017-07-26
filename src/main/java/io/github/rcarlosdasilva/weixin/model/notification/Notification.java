package io.github.rcarlosdasilva.weixin.model.notification;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 微信推送通知模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
@XStreamAlias("notification")
public class Notification extends NotificationMeta {

  private String plaintext;
  private Event event;
  private Message message;
  private OpenInfo openInfo;
  private Account account;

  /**
   * xml明文原文，在安全模式下，微信验证URL时解密后有值，对应URL中的echostr.
   * 
   * @return 明文
   */
  public String getPlaintext() {
    return plaintext;
  }

  public void setPlaintext(String plaintext) {
    this.plaintext = plaintext;
  }

  /**
   * 获取事件相关内容.
   * 
   * @return {@link Event}
   */
  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  /**
   * 获取消息相关内容.
   * 
   * @return {@link Message}
   */
  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  /**
   * 获取开放平台相关内容.
   * 
   * @return {@link OpenInfo}
   */
  public OpenInfo getOpenInfo() {
    return openInfo;
  }

  public void setOpenInfo(OpenInfo open) {
    this.openInfo = open;
  }

  /**
   * 获取推送对应的公众号配置信息.
   * 
   * @return {@link Account}
   */
  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

}