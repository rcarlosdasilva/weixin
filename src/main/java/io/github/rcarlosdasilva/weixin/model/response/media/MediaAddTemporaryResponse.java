package io.github.rcarlosdasilva.weixin.model.response.media;

public class MediaAddTemporaryResponse {

  private String type;
  private String mediaId;
  private long createdAt;

  /**
   * 媒体文件上传后，获取时的唯一标识.
   */
  public String getType() {
    return type;
  }

  /**
   * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）.
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 媒体文件上传时间戳.
   */
  public long getCreatedAt() {
    return createdAt;
  }

}
