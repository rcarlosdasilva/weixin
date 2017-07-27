package io.github.rcarlosdasilva.weixin.model.request.comment;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

@SuppressWarnings("unused")
public class CommentCloseRequest extends BasicWeixinRequest {

  @SerializedName("msg_data_id")
  private String messageDataId;
  private int index;

  public CommentCloseRequest() {
    this.path = ApiAddress.URL_COMMENT_ABILITY_CLOSE;
  }

  public void setMessageDataId(String messageDataId) {
    this.messageDataId = messageDataId;
  }

  public void setIndex(int index) {
    this.index = index;
  }

}
