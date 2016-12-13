package io.github.rcarlosdasilva.weixin.model.response.media;

public class MediaCountTimelessResponse {

  private int voiceCount;
  private int videoCount;
  private int imageCount;
  private int newsCount;

  /**
   * 语音总数量.
   */
  public int getVoiceCount() {
    return voiceCount;
  }

  /**
   * 视频总数量.
   */
  public int getVideoCount() {
    return videoCount;
  }

  /**
   * 图片总数量.
   */
  public int getImageCount() {
    return imageCount;
  }

  /**
   * 图文总数量.
   */
  public int getNewsCount() {
    return newsCount;
  }

}
