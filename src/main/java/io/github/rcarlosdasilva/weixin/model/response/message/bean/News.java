package io.github.rcarlosdasilva.weixin.model.response.message.bean;

import io.github.rcarlosdasilva.weixin.common.Convention;

public class News {

  private String title;
  private String digest;
  private String author;
  private int showCover;
  private String coverUrl;
  private String contentUrl;
  private String sourceUrl;

  /**
   * 标题.
   */
  public String getTitle() {
    return title;
  }

  /**
   * 摘要.
   */
  public String getDigest() {
    return digest;
  }

  /**
   * 作者.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * 是否显示封面，0为不显示，1为显示.
   */
  public boolean isShowCover() {
    return showCover == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 封面图片的URL.
   */
  public String getCoverUrl() {
    return coverUrl;
  }

  /**
   * 正文的URL.
   */
  public String getContentUrl() {
    return contentUrl;
  }

  /**
   * 原文的URL，若置空则无查看原文入口.
   */
  public String getSourceUrl() {
    return sourceUrl;
  }

}
