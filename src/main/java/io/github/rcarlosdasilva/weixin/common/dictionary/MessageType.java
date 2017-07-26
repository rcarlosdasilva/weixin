package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 客服消息推送类型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum MessageType {

  /**
   * 文本.
   */
  TEXT("text"),
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
   * 消息推送时的视频类型.
   */
  MPVIDEO("mpvideo"),
  /**
   * 音乐.
   */
  MUSIC("music"),
  /**
   * 图文消息（点击跳转到外链）.
   */
  NEWS_EXTERNAL("news"),
  /**
   * 图文消息（点击跳转到图文消息页面）.
   */
  NEWS_INTERNAL("mpnews"),
  /**
   * 卡券.
   */
  CARD("wxcard");

  private String text;

  private MessageType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public static MessageType byValue(String text) {
    for (MessageType result : values()) {
      if (result.text.equalsIgnoreCase(text)) {
        return result;
      }
    }
    return null;
  }
  
  @Override
  public String toString() {
    return this.text;
  }

}
