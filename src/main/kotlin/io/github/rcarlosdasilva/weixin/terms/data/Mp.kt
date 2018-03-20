package io.github.rcarlosdasilva.weixin.terms.data

/**
 * 公众号认证类型，兼容开放平台的各种认证类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class MpAuthentication {

  /**
   * 未认证
   */
  UNC,
  /**
   * 微信认证
   */
  CWX,
  /**
   * 新浪微博认证
   */
  CSW,
  /**
   * 腾讯微博认证
   */
  CTW,
  /**
   * 已资质认证通过但还未通过名称认证
   */
  CFN,
  /**
   * 已资质认证通过、还未通过名称认证，但通过了新浪微博认证
   */
  CFS,
  /**
   * 代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
   */
  CFT

}

/**
 * 公众号类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class MpType(val code: Int) {

  /** 订阅号 */
  SUBSCRIPTION(0),
  /** 由历史老帐号升级后的订阅号  */
  SUBSCRIPTION_OLD(1),
  /** 服务号 */
  SERVICE(2),
  /** 企业号 */
  ENTERPRISE(3);

  companion object {
    fun with(code: Int): MpType? = values().find { it.code == code }
  }

}
