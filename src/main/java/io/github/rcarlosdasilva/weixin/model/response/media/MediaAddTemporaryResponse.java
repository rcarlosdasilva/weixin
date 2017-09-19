package io.github.rcarlosdasilva.weixin.model.response.media;

import com.google.gson.annotations.SerializedName;

public class MediaAddTemporaryResponse {

  private String type;
  @SerializedName("media_id")
  private String mediaId;
  @SerializedName("created_at")
  private long createdAt;
  @SerializedName("thumb_media_id")
  private String thumbMediaId;

  /**
   * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）.
   * 
   * @return media id
   */
  public String getType() {
    return type;
  }

  /**
   * 媒体文件上传后，获取时的唯一标识.
   * 
   * @return type
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 媒体文件上传时间戳.
   * 
   * @return time
   */
  public long getCreatedAt() {
    return createdAt;
  }

  /**
   * 媒体文件上传后，获取时的唯一标识(用于缩略图文件).
   * 
   * @return
   */
  public String getThumbMediaId() {
    return thumbMediaId;
  }

}
