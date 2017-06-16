package io.github.rcarlosdasilva.weixin.common.dictionary;

public enum OpenPlatformLisensableFunction {

  /**
   * 消息管理权限
   */
  MESSAGE_MANAGEMENT(1),
  /**
   * 用户管理权限
   */
  FANS(2),
  /**
   * 帐号服务权限
   */
  ACCOUNT(3),
  /**
   * 网页服务权限
   */
  WEB_AUTH(4),
  /**
   * 微信小店权限
   */
  WX_SHOP(5),
  /**
   * 微信多客服权限
   */
  WX_CUSTOM(6),
  /**
   * 群发与通知权限
   */
  MESSAGE_SEND(7),
  /**
   * 微信卡券权限
   */
  WX_CARD(8),
  /**
   * 微信扫一扫权限
   */
  QR_SCAN(9),
  /**
   * 微信连WIFI权限
   */
  WX_WIFI(10),
  /**
   * 素材管理权限
   */
  MESSAGE_MATERIAL(11),
  /**
   * 微信摇周边权限
   */
  WX_SHAKE(12),
  /**
   * 微信门店权限
   */
  WX_STORE(13),
  /**
   * 微信支付权限
   */
  WX_PAY(14),
  /**
   * 自定义菜单权限
   */
  MENU(15);

  private int code;

  OpenPlatformLisensableFunction(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  /**
   * 根据code获取枚举对象.
   * 
   * @param code
   *          代码
   * @return 枚举
   */
  public static OpenPlatformLisensableFunction byCode(int code) {
    for (OpenPlatformLisensableFunction function : values()) {
      if (function.code == code) {
        return function;
      }
    }
    return null;
  }

}
