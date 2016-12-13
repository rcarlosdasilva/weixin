package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Article {

  private String title;
  private String url;
  private String description;
  @SerializedName("picurl")
  private String picUrl;

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

}
