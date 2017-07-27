package io.github.rcarlosdasilva.weixin.model.request.comment;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.CommentType;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

@SuppressWarnings("unused")
public class CommentListRequest extends BasicWeixinRequest {

  @SerializedName("msg_data_id")
  private String messageDataId;
  private int index;
  private int begin;
  private int count;
  private int type;

  public CommentListRequest() {
    this.path = ApiAddress.URL_COMMENT_LIST;
  }

  public void setMessageDataId(String messageDataId) {
    this.messageDataId = messageDataId;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setBegin(int begin) {
    this.begin = begin;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setType(CommentType type) {
    this.type = type.getCode();
  }

}
