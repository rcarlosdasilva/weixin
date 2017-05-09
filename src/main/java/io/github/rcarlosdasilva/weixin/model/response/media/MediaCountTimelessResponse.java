package io.github.rcarlosdasilva.weixin.model.response.media;

import com.google.gson.annotations.SerializedName;

public class MediaCountTimelessResponse {

  @SerializedName("voice_count")
  private int voiceCount;
  @SerializedName("video_count")
  private int videoCount;
  @SerializedName("image_count")
  private int imageCount;
  @SerializedName("news_count")
  private int newsCount;

  /**
   * 语音总数量.
   * 
   * @return count
   */
  public int getVoiceCount() {
    return voiceCount;
  }

  /**
   * 视频总数量.
   * 
   * @return count
   */
  public int getVideoCount() {
    return videoCount;
  }

  /**
   * 图片总数量.
   * 
   * @return count
   */
  public int getImageCount() {
    return imageCount;
  }

  /**
   * 图文总数量.
   * 
   * @return count
   */
  public int getNewsCount() {
    return newsCount;
  }

}
