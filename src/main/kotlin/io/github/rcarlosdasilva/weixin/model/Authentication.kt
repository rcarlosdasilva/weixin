package io.github.rcarlosdasilva.weixin.model

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.handler.Cacheable
import io.github.rcarlosdasilva.weixin.terms.AHEAD_OF_EXPIRED_SECONDS
import java.io.Serializable

abstract class Certification : Serializable, Cacheable {
  open val code: String? = null
  val expiresIn = 0L
  /**
   * 凭证有效时间,单位:秒，准确的过期时间，默认提前180秒过期
   */
  private val expireAt by lazy { (expiresIn - AHEAD_OF_EXPIRED_SECONDS) * 1000 + System.currentTimeMillis() }
  /**
   * 是否过期或无用
   */
  val isExpired: Boolean
    get() = expireAt < System.currentTimeMillis() || code?.isBlank() != false

  companion object {
    private const val serialVersionUID = 1L
  }
}

/**
 * 通用凭证信息
 *
 * - 对应**公众号接口**的access_token
 * - 对应**开放平台接口**的component_access_token
 * - 对应**开放平台授权方接口**的authorizer_access_token(授权方接口调用凭据（在授权的公众号或小程序具备API权限时，才有此返回值），也简称为令牌)
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class AccessToken : Certification() {
  override val code: String?
    get() = accessToken
  val accessToken: String? = null
  var accountMark: String? = null
  /**
   * （当使用开放平台时）授权方票据的刷新令牌(对应的是authorizer_refresh_token)
   *
   * 接口调用凭据刷新令牌（在授权的公众号具备API权限时，才有此返回值），刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。
   * 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
   */
  var refreshToken: String? = null
}

/**
 * JS SDK，Ticket票据
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
abstract class JsTicket : Certification() {
  override val code: String?
    get() = jsTicket
  @SerializedName("ticket")
  val jsTicket: String? = null
}

/**
 * JS SDK签名模型
 *
 * @param appId AppId
 * @param ticket Ticket
 * @param signature 签名
 * @param url 签名地址
 * @param timestamp 时间戳
 * @param nonce 随机值
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
data class JsapiSignature(
  val appId: String,
  val ticket: String,
  val signature: String,
  val url: String,
  val timestamp: String,
  val nonce: String
)