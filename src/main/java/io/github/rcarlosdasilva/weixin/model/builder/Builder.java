package io.github.rcarlosdasilva.weixin.model.builder;

/**
 * 请求模型构建器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Builder {

  /**
   * 构建一个自定义菜单.
   */
  public static MenuBuilder buildMenu() {
    return new MenuBuilder();
  }

  /**
   * 构建一个模板消息.
   */
  public static TemplateMessageBuilder buildTemplateMessage() {
    return new TemplateMessageBuilder();
  }

  /**
   * 构建客服消息内容.
   */
  public static MassOrCustomMessageBuilder buildMessage() {
    return new MassOrCustomMessageBuilder();
  }

  /**
   * 构建微信推送通知回复消息构建器.
   */
  public static NotificationResponseBuilder buildNotificationResponse() {
    return new NotificationResponseBuilder();
  }

}
