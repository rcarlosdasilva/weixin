package io.github.rcarlosdasilva.weixin.model

import io.github.rcarlosdasilva.weixin.handler.Cacheable
import io.github.rcarlosdasilva.weixin.terms.data.EncryptionType
import io.github.rcarlosdasilva.weixin.terms.data.MpAuthentication
import io.github.rcarlosdasilva.weixin.terms.data.MpType
import java.io.Serializable

data class Op(
  val appId: String,
  val appSecret: String,
  val aesToken: String,
  val aesKey: String
) : Serializable, Cacheable {
  companion object {
    private const val serialVersionUID = 1L
  }
}

data class Mp(val appId: String) : Serializable, Cacheable {

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

  var appSecret: String? = null
    set(value) {}
  /**
   * 注册到缓存器中的key值，方便日志查找，默认为appid
   */
  var key: String = appId
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
   * 公众号在开放平台的RefreshToken
   */
  var refreshToken: String? = null

  val extension by lazy { mutableMapOf<String, Any>() }

  companion object {
    private const val serialVersionUID = 1L
  }

}