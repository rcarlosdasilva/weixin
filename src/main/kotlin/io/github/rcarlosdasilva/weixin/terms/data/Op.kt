package io.github.rcarlosdasilva.weixin.terms.data

/**
 * 公众号认证状态类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class OpMpAuthentication(val code: Int) {

  /** 未认证  */
  UNVERIFIED(-1),
  /** 微信认证  */
  VERIFIED(0),
  /** 新浪微博认证  */
  SINA_WEIBO_VERIFIED(1),
  /** 腾讯微博认证  */
  TENCENT_WEIBO_VERIFIED(2),
  /** 已资质认证通过但还未通过名称认证  */
  QUALIFICATION_VERIFIED(3),
  /** 已资质认证通过、还未通过名称认证，但通过了新浪微博认证  */
  QUALIFICATION_AND_SINA_WEIBO_VERIFIED(4),
  /** 已资质认证通过、还未通过名称认证，但通过了腾讯微博认证  */
  QUALIFICATION_AND_TENCENT_WEIBO_VERIFIED(5);

  companion object {
    fun with(code: Int): OpMpAuthentication? = values().find { it.code == code }
  }

}

enum class OpLicenseFunction(val code: Int) {

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

  companion object {
    fun with(code: Int): OpLicenseFunction? = values().find { it.code == code }
  }

}
