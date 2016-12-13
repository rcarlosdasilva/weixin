package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 自定义菜单按钮类型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum MenuType {

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
  /** 下发消息（除文本消息）. */
  SEND_MEDIA("media_id"),
  /** 跳转图文消息URL. */
  REDIRECT_VIEW("view_limited");

  private String text;

  private MenuType(String text) {
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
