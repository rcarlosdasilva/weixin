package io.github.rcarlosdasilva.weixin.model.response.media;

import com.google.gson.annotations.SerializedName;

public class MediaTransformMassVideoResponse {

  private String type;
  @SerializedName("media_id")
  private String mediaId;
  private long createAt;

  /**
   * 素材类型.
   * 
   * @return type
   */
  public String getType() {
    return type;
  }

  /**
   * 可用于群发的视频media_id.
   * 
   * @return media id
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 创建时间.
   * 
   * @return time
   */
  public long getCreateAt() {
    return createAt;
  }

}
