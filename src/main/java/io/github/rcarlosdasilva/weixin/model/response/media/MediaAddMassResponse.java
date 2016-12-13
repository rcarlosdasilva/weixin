package io.github.rcarlosdasilva.weixin.model.response.media;

public class MediaAddMassResponse {

  private String mediaId;
  private String type;
  private long createAt;
  private String url;

  /**
   * 媒体文件/图文消息上传后获取的唯一标识.
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息（news）.
   */
  public String getType() {
    return type;
  }

  /**
   * 媒体文件上传时间.
   */
  public long getCreateAt() {
    return createAt;
  }

  /**
   * 上传图片的URL，可用于后续群发中，放置到图文消息中.
   */
  public String getUrl() {
    return url;
  }

}
