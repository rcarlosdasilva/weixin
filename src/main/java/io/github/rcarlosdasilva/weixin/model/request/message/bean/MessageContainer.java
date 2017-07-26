package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;

public class MessageContainer {

  private MessageType type;
  private Message bean;
  private boolean canReprint = true;
  private String businessMark;
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

  public boolean isCanReprint() {
    return canReprint;
  }

  /**
   * 图文消息被判定为转载时，是否继续群发.
   * <p>
   * 继续群发（转载）为true，否则停止群发。 默认为true
   * 
   * @param breakForReprint
   *          boolean
   */
  public void setCanReprint(boolean canReprint) {
    this.canReprint = canReprint;
  }

  public String getBusinessMark() {
    return businessMark;
  }

  /**
   * 群发接口新增 clientmsgid 参数.
   * <p>
   * 开发者调用群发接口时可以主动设置 clientmsgid 参数，避免重复推送。 群发时，微信后台将对 24 小时内的群发记录进行检查，如果该
   * clientmsgid已经存在一条群发记录，则会拒绝本次群发请求，返回已存在的群发msgid，开发者可以调用“查询群发消息发送状态”接口查看该条群发的状态。
   * 
   * @param businessMark
   *          mark
   */
  public void setBusinessMark(String businessMark) {
    this.businessMark = businessMark;
  }

  public String getCustomServiceAccount() {
    return customServiceAccount;
  }

  public void setCustomServiceAccount(String customServiceAccount) {
    this.customServiceAccount = customServiceAccount;
  }

}
