package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;

public class MessageContainer {

  private MessageType type;
  private Message bean;
  private String customServiceAccount;

  public MessageContainer(MessageType type, Message bean) {
    this.type = type;
    this.bean = bean;
  }

  public MessageContainer() {
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  public Message getBean() {
    return bean;
  }

  public void setBean(Message bean) {
    this.bean = bean;
  }

  public String getCustomServiceAccount() {
    return customServiceAccount;
  }

  public void setCustomServiceAccount(String customServiceAccount) {
    this.customServiceAccount = customServiceAccount;
  }

}
