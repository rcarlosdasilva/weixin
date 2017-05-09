package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import com.google.gson.annotations.SerializedName;

public class Voice implements Message {

  @SerializedName("media_id")
  private String mediaId;

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
