package io.github.rcarlosdasilva.weixin.model.request.message;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 删除群发请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MessageDeleteMassRequest extends BasicWeixinRequest {

  @SerializedName("msg_id")
  private String messageId;
  @SerializedName("article_idx")
  private int index;

  public MessageDeleteMassRequest() {
    this.path = ApiAddress.URL_MESSAGE_DELETE_MASS;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public void setIndex(int index) {
    this.index = index;
  }

}
