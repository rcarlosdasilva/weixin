package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Video implements Message {

  @SerializedName("media_id")
  private String mediaId;
  @SerializedName("thumb_media_id")
  private String mediaThumbId;
  private String title;
  private String description;

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public void setMediaThumbId(String mediaThumbId) {
    this.mediaThumbId = mediaThumbId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
