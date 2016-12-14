package io.github.rcarlosdasilva.weixin.model.response.message;

import com.google.gson.annotations.SerializedName;

public class MessageSendWithTemplateResponse {

  @SerializedName("msgid")
  private long messageId;

  /**
   * 消息id.
   * 
   * @return message id
   */
  public long getMessageId() {
    return messageId;
  }

}
