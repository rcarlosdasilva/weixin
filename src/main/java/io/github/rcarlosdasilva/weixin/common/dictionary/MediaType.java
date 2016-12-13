package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 素材类型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum MediaType {

  /**
   * 图片.
   */
  IMAGE("image"),
  /**
   * 语音.
   */
  VOICE("voice"),
  /**
   * 视频.
   */
  VIDEO("video"),
  /**
   * 缩略图.
   */
  THUMBNAIL("thumb"),
  /**
   * 图文.
   */
  NEWS("news");

  private String text;

  private MediaType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return this.text;
  }

}
