package io.github.rcarlosdasilva.weixin.model.response.media;

import com.google.gson.annotations.SerializedName;

public class MediaGetTemporaryWithVideoResponse {

  @SerializedName("video_url")
  private String videoUrl;

  /**
   * 视频素材的路径
   * 
   * @return 路径
   */
  public String getVideoUrl() {
    return videoUrl;
  }

}
