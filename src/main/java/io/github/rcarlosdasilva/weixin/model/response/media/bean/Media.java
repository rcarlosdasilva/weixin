package io.github.rcarlosdasilva.weixin.model.response.media.bean;

import com.google.gson.annotations.SerializedName;

public class Media {

  @SerializedName("media_id")
  private String mediaId;
  private String name;
  private String url;
  @SerializedName("update_time")
  private long updateTime;
  private ArticleCollection content;

  /**
   * 素材media_id.
   * 
   * @return media id
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 文件名称.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * 素材url.
   * 
   * @return url
   */
  public String getUrl() {
    return url;
  }

  /**
   * 这篇图文消息素材的最后更新时间.
   * 
   * @return time
   */
  public long getUpdateTime() {
    return updateTime;
  }

  /**
   * 图文信息.
   * 
   * @return {@link ArticleCollection}
   */
  public ArticleCollection getContent() {
    return content;
  }

}
