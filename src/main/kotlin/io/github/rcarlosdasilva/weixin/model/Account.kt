package io.github.rcarlosdasilva.weixin.model

import io.github.rcarlosdasilva.weixin.handler.Cacheable
import io.github.rcarlosdasilva.weixin.terms.ACCOUNT_PLATFORM_TYPE_MP
import io.github.rcarlosdasilva.weixin.terms.ACCOUNT_PLATFORM_TYPE_OP
import io.github.rcarlosdasilva.weixin.terms.UNIQUE_OP_ACCOUNT_CACHE_KEY
import io.github.rcarlosdasilva.weixin.terms.data.EncryptionType
import io.github.rcarlosdasilva.weixin.terms.data.MpAuthentication
import io.github.rcarlosdasilva.weixin.terms.data.MpType
import java.io.Serializable

open class Account(
    open val appId: String,
    internal val ap: String
) : Serializable, Cacheable {
  /**
   * 注册到缓存器中的key值，方便日志查找，默认为appid
   */
  open lateinit var key: String

  companion object {
    private const val serialVersionUID = 1L
  }
}

data class Op(
    override val appId: String,
    val appSecret: String,
    val aesToken: String,
    val aesKey: String
) : Account(appId, ACCOUNT_PLATFORM_TYPE_OP) {
  override var key = UNIQUE_OP_ACCOUNT_CACHE_KEY
}

data class Mp(override val appId: String) : Account(appId, ACCOUNT_PLATFORM_TYPE_MP) {

  /**
   * 不推荐使用appid与appsecret调用微信API （如开发自持有单公众号例外）
   */
  constructor(
      appId: String,
      appSecret: String
  ) : this(appId) {
    this.appSecret = appSecret
    this.proxyWithOp = false
  }

  override var key = appId

  var appSecret: String? = null
  /**
   * 原始id
   */
  var mpId: String? = null
  /**
   * 公众号类型
   */
  var type: MpType? = null
  /**
   * 认证方式
   */
  var authentication: MpAuthentication? = null
  /**
   * 公众号昵称
   */
  var nickname: String? = null

  //-------------------- 条件允许下，推荐使用开放平台 ----------------------
  /**
   * 推荐使用安全模式，参考[消息加解密说明](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1434696670)
   */
  var encryptionType = EncryptionType.PLAIN_TEXT
  /**
   * 加密用令牌
   */
  var aesToken: String? = null
  /**
   * 加密用密钥
   */
  var aesKey: String? = null
  //===================== 条件允许下，推荐使用开放平台 =====================

  /**
   * 是否将公众号授权给开放平台，API的调用将通过开放平台第三方平台代理调用
   */
  var proxyWithOp = true
  /**
   * 公众号在开放平台的RefreshTokeno
   */
  var refreshToken: String? = null

  val extension by lazy { mutableMapOf<String, Any>() }

  companion object {
    private const val serialVersionUID = 1L
  }

}