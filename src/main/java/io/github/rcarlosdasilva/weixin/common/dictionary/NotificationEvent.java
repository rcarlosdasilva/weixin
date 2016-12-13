package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 微信推送通知事件
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum NotificationEvent {

  /** 点击推事件. */
  CLICK("click"),
  /** 跳转URL. */
  VIEW("view"),
  /** 扫码推事件. */
  SCAN_QR_PUSH("scancode_push"),
  /** 扫码推事件且弹出“消息接收中”提示框. */
  SCAN_QR_WAIT_MSG("scancode_waitmsg"),
  /** 弹出系统拍照发图. */
  PIC_PHOTO("pic_sysphoto"),
  /** 弹出拍照或者相册发图. */
  PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),
  /** 弹出微信相册发图器. */
  PIC_WX_ALBUM("pic_weixin"),
  /** 弹出地理位置选择器. */
  LOCATION("location_select"),
  /** 订阅. */
  SUBSCRIBE("subscribe"),
  /** 取消订阅. */
  UNSUBSCRIBE("unsubscribe"),
  /** 用户已关注时的事件推送. */
  SCAN("scan"),
  /** 上报地理位置. */
  REPORT_LOCATION("location"),
  /** 群发消息发送结束. */
  MASS_SEND_FINISH("masssendjobfinish"),
  /** 模板消息发送结束. */
  TEMPLATE_SEND_FINISH("templatesendjobfinish"),
  /** 资质认证成功（此时立即获得接口权限）. */
  VERIFY_QUALIFICATION_SUCCESS("qualification_verify_success"),
  /** 资质认证失败. */
  VERIFY_QUALIFICATION_FAIL("qualification_verify_fail"),
  /** 名称认证成功（即命名成功）. */
  VERIFY_NAMING_SUCCESS("naming_verify_success"),
  /** 名称认证失败（这时虽然客户端不打勾，但仍有接口权限）. */
  VERIFY_NAMING_FAIL("naming_verify_fail"),
  /** 年审通知，提醒公众号需要去年审了. */
  VERIFY_ANNUAL("annual_renew"),
  /** 认证过期失效通知. */
  VERIFY_EXPIRED("verify_expired"),
  /** 卡券微信买单事件. */
  CARD_PAID("User_pay_from_pay_cell"),
  /** 未知. */
  UNKNOWN("");

  private String text;

  private NotificationEvent(String text) {
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
  public static NotificationEvent byValue(String text) {
    for (NotificationEvent result : values()) {
      if (result.text.equalsIgnoreCase(text)) {
        return result;
      }
    }
    throw new IllegalArgumentException("No matching result for [" + text + "]");
  }

}
