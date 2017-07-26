package io.github.rcarlosdasilva.weixin.common.dictionary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信推送通知类型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum NotificationMessageType {

  /**
   * 事件消息.
   */
  EVENT("event"),
  /**
   * 文本消息.
   */
  TEXT("text"),
  /**
   * 图片消息.
   */
  IMAGE("image"),
  /**
   * 语音消息.
   */
  VOICE("voice"),
  /**
   * 视频消息.
   */
  VIDEO("video"),
  /**
   * 小视频消息.
   */
  SHORT_VIDEO("shortvideo"),
  /**
   * 地理位置消息.
   */
  LOCATION("location"),
  /**
   * 链接消息.
   */
  LINK("link");

  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationMessageType.class);

  private String text;

  private NotificationMessageType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return this.text;
  }

  /**
   * 根据code获取枚举对象.
   * 
   * @param text
   *          内容
   * @return 枚举
   */
  public static NotificationMessageType byValue(String text) {
    for (NotificationMessageType result : values()) {
      if (result.text.equalsIgnoreCase(text)) {
        return result;
      }
    }

    LOGGER.warn("No matching result for [{}]", text);
    return null;
  }

}
