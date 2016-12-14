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
   * 
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * 摘要.
   * 
   * @return digest
   */
  public String getDigest() {
    return digest;
  }

  /**
   * 作者.
   * 
   * @return author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * 是否显示封面，0为不显示，1为显示.
   * 
   * @return is show
   */
  public boolean isShowCover() {
    return showCover == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 封面图片的URL.
   * 
   * @return url
   */
  public String getCoverUrl() {
    return coverUrl;
  }

  /**
   * 正文的URL.
   * 
   * @return url
   */
  public String getContentUrl() {
    return contentUrl;
  }

  /**
   * 原文的URL，若置空则无查看原文入口.
   * 
   * @return url
   */
  public String getSourceUrl() {
    return sourceUrl;
  }

}
