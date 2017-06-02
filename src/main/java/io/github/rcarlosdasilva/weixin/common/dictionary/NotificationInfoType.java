package io.github.rcarlosdasilva.weixin.common.dictionary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信开放平台InfoType
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum NotificationInfoType {

  /**
   * 推送component_verify_ticket协议ticket内容.
   */
  VERIFY_TICKET("component_verify_ticket"),
  /**
   * 取消授权通知.
   */
  AUTHORIZE_CANCELED("unauthorized"),
  /**
   * 授权成功通知.
   */
  AUTHORIZE_SUCCEEDED("authorized"),
  /**
   * 授权更新通知.
   */
  AUTHORIZE_UPDATED("updateauthorized");

  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationInfoType.class);

  private String text;

  private NotificationInfoType(String text) {
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
  public static NotificationInfoType byValue(String text) {
    for (NotificationInfoType result : values()) {
      if (result.text.equalsIgnoreCase(text)) {
        return result;
      }
    }

    LOGGER.warn("No matching result for [{}]", text);
    return null;
  }

}
