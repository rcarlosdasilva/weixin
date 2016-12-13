package io.github.rcarlosdasilva.weixin.model.response.media;

public class MediaTransformMassVideoResponse {

  private String type;
  private String mediaId;
  private long createAt;

  /**
   * 素材类型.
   */
  public String getType() {
    return type;
  }

  /**
   * 可用于群发的视频media_id.
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 创建时间.
   */
  public long getCreateAt() {
    return createAt;
  }

}
