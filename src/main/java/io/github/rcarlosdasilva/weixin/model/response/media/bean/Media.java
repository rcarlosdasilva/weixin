package io.github.rcarlosdasilva.weixin.model.response.media.bean;

public class Media {

  private String mediaId;
  private String name;
  private String url;
  private long updateTime;
  private ArticleCollection content;

  /**
   * 素材media_id.
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 文件名称.
   */
  public String getName() {
    return name;
  }

  /**
   * 素材url.
   */
  public String getUrl() {
    return url;
  }

  /**
   * 这篇图文消息素材的最后更新时间.
   */
  public long getUpdateTime() {
    return updateTime;
  }

  /**
   * 图文信息.
   */
  public ArticleCollection getContent() {
    return content;
  }

}
