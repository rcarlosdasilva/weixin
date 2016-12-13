package io.github.rcarlosdasilva.weixin.model.response.menu.bean;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.Convention;

public class Media {

  private String title;
  private String author;
  private String digest;
  @SerializedName("show_cover")
  private int showCover;
  @SerializedName("cover_url")
  private String coverUrl;
  @SerializedName("content_url")
  private String contentUrl;
  @SerializedName("source_url")
  private String sourceUrl;

  /** 图文消息的标题. */
  public String getTitle() {
    return title;
  }

  /** 作者. */
  public String getAuthor() {
    return author;
  }

  /** 摘要. */
  public String getDigest() {
    return digest;
  }

  /** 是否显示封面，0为不显示，1为显示. */
  public Boolean getShowCover() {
    return showCover == Convention.GLOBAL_TRUE_NUMBER;
  }

  /** 封面图片的URL. */
  public String getCoverUrl() {
    return coverUrl;
  }

  /** 正文的URL. */
  public String getContentUrl() {
    return contentUrl;
  }

  /** 原文的URL，若置空则无查看原文入口. */
  public String getSourceUrl() {
    return sourceUrl;
  }

}
