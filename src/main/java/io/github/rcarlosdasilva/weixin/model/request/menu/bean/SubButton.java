package io.github.rcarlosdasilva.weixin.model.request.menu.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SubButton {

  private String name;
  private String type;
  private String key;
  private String url;
  @SerializedName("media_id")
  private String mediaId;

  public void setName(String name) {
    this.name = name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}