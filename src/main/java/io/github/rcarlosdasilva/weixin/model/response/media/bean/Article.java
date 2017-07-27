package io.github.rcarlosdasilva.weixin.model.response.media.bean;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.Convention;

public class Article {

  private String title;
  @SerializedName("thumb_media_id")
  private String thumbnailMediaId;
  private String author;
  private String digest;
  @SerializedName("show_cover_pic")
  private int showCover;
  private String content;
  private String url;
  @SerializedName("content_source_url")
  private String sourceUrl;
  @SerializedName("need_open_comment")
  private int openComment;
  @SerializedName("only_fans_can_comment")
  private int onlyFansComment;

  /**
   * 图文消息的标题.
   * 
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * 图文消息的封面图片素材id（必须是永久mediaID）.
   * 
   * @return media id
   */
  public String getThumbnailMediaId() {
    return thumbnailMediaId;
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
   * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空.
   * 
   * @return digest
   */
  public String getDigest() {
    return digest;
  }

  /**
   * 是否显示封面，0为false，即不显示，1为true，即显示.
   * 
   * @return is show
   */
  public boolean isShowCover() {
    return showCover == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS.
   * 
   * @return content
   */
  public String getContent() {
    return content;
  }

  /**
   * 图文页的URL.
   * 
   * @return url
   */
  public String getUrl() {
    return url;
  }

  /**
   * 图文消息的原文地址，即点击“阅读原文”后的URL.
   * 
   * @return url
   */
  public String getSourceUrl() {
    return sourceUrl;
  }

  /**
   * 是否打开评论，0不打开，1打开.
   * 
   * @return open comment
   */
  public boolean isOpenComment() {
    return openComment == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 是否粉丝才可评论，0所有人可评论，1粉丝才可评论.
   * 
   * @return only fans comment
   */
  public boolean isOnlyFansComment() {
    return onlyFansComment == Convention.GLOBAL_TRUE_NUMBER;
  }

}
