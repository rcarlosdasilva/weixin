package io.github.rcarlosdasilva.weixin.terms.data

import com.google.gson.annotations.SerializedName

/**
 * 网页授权Scope
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class WebAuthorizeScope(val text: String) {

  /**
   * snsapi_base.
   *
   * <P>
   * 以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的， 并且是静默授权并自动跳转到回调页的。
   * 用户感知的就是直接进入了回调页（往往是业务页面）
  </P> */
  BASE("snsapi_base"),
  /**
   * snsapi_userinfo.
   *
   * <P>
   * 以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意， 并且由于用户同意过，
   * 所以无须关注，就可在授权后获取该用户的基本信息。
  </P> */
  USERINFO("snsapi_userinfo");

  override fun toString(): String = text

}

/**
 * 客户端平台类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class ClientPlatform(val code: Int) {

  IOS(1), ANDROID(2), OTHER(3)

}

/**
 * 消息加解密模式
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class EncryptionType {

  /**
   * 明文模式.
   */
  PLAIN_TEXT,
  /**
   * 安全模式（推荐）.
   */
  SAFETY,
  /**
   * 兼容模式.
   */
  COMPATIBLE

}

/**
 * 语言
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class Language(val text: String) {

  /** 简体中文.  */
  @SerializedName("zh_CN")
  ZH_CN("zh_CN"),
  /** 繁体中文TW.  */
  @SerializedName("zh_TW")
  ZH_TW("zh_TW"),
  /** 繁体中文HK.  */
  @SerializedName("zh_HK")
  ZH_HK("zh_HK"),
  /** 英文.  */
  @SerializedName("en")
  EN_US("en"),
  /** 印尼语.  */
  @SerializedName("id")
  ID("id"),
  /** 马来语.  */
  @SerializedName("ms")
  MS("ms"),
  /** 西班牙语.  */
  @SerializedName("es")
  ES("es"),
  /** 韩语.  */
  @SerializedName("ko")
  KO("ko"),
  /** 意大利语.  */
  @SerializedName("it")
  IT("it"),
  /** 日语.  */
  @SerializedName("ja")
  JA("ja"),
  /** 波兰语.  */
  @SerializedName("pl")
  PL("pl"),
  /** 葡萄牙语.  */
  @SerializedName("pt")
  PT("pt"),
  /** 俄语.  */
  @SerializedName("ru")
  RU("ru"),
  /** 泰文.  */
  @SerializedName("th")
  TH("th"),
  /** 越南语.  */
  @SerializedName("vi")
  VI("vi"),
  /** 阿拉伯语.  */
  @SerializedName("ar")
  AR("ar"),
  /** 北印度.  */
  @SerializedName("hi")
  HI("hi"),
  /** 希伯来语.  */
  @SerializedName("he")
  HE("he"),
  /** 土耳其语.  */
  @SerializedName("tr")
  TR("tr"),
  /** 德语.  */
  @SerializedName("de")
  DE("de"),
  /** 法语.  */
  @SerializedName("fr")
  FR("fr");

  override fun toString(): String = text

}

/**
 * 性别
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class Sex(val code: Int) {

  /**
   * 未知.
   */
  UNKNOWN(0),
  /**
   * 男性.
   */
  MALE(1),
  /**
   * 女性.
   */
  FEMALE(2)

}

/**
 * 二维码类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class QrCodeAction(val text: String) {

  /** 临时二维码.  */
  TEMPORARY("QR_SCENE"),
  /** 带id参数的永久二维码.  */
  UNLIMITED_WITH_ID("QR_LIMIT_SCENE"),
  /** 带字符串参数的永久二维码.  */
  UNLIMITED_WITH_STRING("QR_LIMIT_STR_SCENE");

  override fun toString(): String = text

}