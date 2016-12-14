package io.github.rcarlosdasilva.weixin.model.response.message;

import com.google.gson.annotations.SerializedName;

public class MessageSendWithMassResponse {

  @SerializedName("msg_id")
  private String messageId;
  @SerializedName("msg_data_id")
  private String messageDataId;

  /**
   * 消息发送任务的ID.
   * 
   * @return message id
   */
  public String getMessageId() {
    return messageId;
  }

  /**
   * 消息的数据ID，，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，
   * 获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍.
   * 
   * @return message data id
   */
  public String getMessageDataId() {
    return messageDataId;
  }

}
