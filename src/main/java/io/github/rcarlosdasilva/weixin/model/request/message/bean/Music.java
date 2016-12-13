package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Music implements Message {

  @SerializedName("musicurl")
  private String url;
  @SerializedName("hqmusicurl")
  private String url4hq;
  @SerializedName("thumb_media_id")
  private String mediaThumbId;
  private String title;
  private String description;

  public void setUrl(String url) {
    this.url = url;
  }

  public void setUrl4hq(String url4hq) {
    this.url4hq = url4hq;
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
