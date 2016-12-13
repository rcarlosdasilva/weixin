package io.github.rcarlosdasilva.weixin.model.request.media.bean;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.Convention;

@SuppressWarnings("unused")
public class Article {

  private String title;
  @SerializedName("thumb_media_id")
  private String thumbnailMediaId;
  private String author;
  private String digest;
  @SerializedName("show_cover_pic")
  private int showCover;
  private String content;
  @SerializedName("content_source_url")
  private String sourceUrl;

  /**
   * 构造函数.
   * 
   * @param title
   *          标题
   * @param thumbnailMediaId
   *          图文消息的封面图片素材id（必须是永久mediaID）
   * @param author
   *          作者
   * @param digest
   *          图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
   * @param isShowCover
   *          是否显示封面，0为false，即不显示，1为true，即显示
   * @param content
   *          图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
   * @param sourceUrl
   *          图文消息的原文地址，即点击“阅读原文”后的URL
   */
  public Article(String title, String thumbnailMediaId, String author, String digest,
      boolean isShowCover, String content, String sourceUrl) {
    this.title = title;
    this.thumbnailMediaId = thumbnailMediaId;
    this.author = author;
    this.digest = digest;
    this.showCover = isShowCover ? Convention.GLOBAL_TRUE_NUMBER : Convention.GLOBAL_FALSE_NUMBER;
    this.content = content;
    this.sourceUrl = sourceUrl;
  }

  /*
   * 标题.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 图文消息的封面图片素材id（必须是永久mediaID）.
   */
  public void setThumbnailMediaId(String thumbnailMediaId) {
    this.thumbnailMediaId = thumbnailMediaId;
  }

  /**
   * 作者.
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空.
   */
  public void setDigest(String digest) {
    this.digest = digest;
  }

  /**
   * 是否显示封面，0为false，即不显示，1为true，即显示.
   */
  public void setShowCover(boolean isShowCover) {
    this.showCover = isShowCover ? Convention.GLOBAL_TRUE_NUMBER : Convention.GLOBAL_FALSE_NUMBER;
  }

  /**
   * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS.
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 图文消息的原文地址，即点击“阅读原文”后的URL.
   */
  public void setSourceUrl(String sourceUrl) {
    this.sourceUrl = sourceUrl;
  }

}
