package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.core.ApiRequestException
import io.github.rcarlosdasilva.weixin.core.Weixin
import io.github.rcarlosdasilva.weixin.handler.CacheHandler
import io.github.rcarlosdasilva.weixin.handler.JsonHandler
import io.github.rcarlosdasilva.weixin.handler.urlEncode
import io.github.rcarlosdasilva.weixin.model.AccessToken
import io.github.rcarlosdasilva.weixin.model.JsapiSignature
import io.github.rcarlosdasilva.weixin.model.JsapiTicket
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.MpAccessTokenResponse
import io.github.rcarlosdasilva.weixin.model.response.MpJsapiTicketResponse
import io.github.rcarlosdasilva.weixin.model.response.WaAccessTokenResponse
import io.github.rcarlosdasilva.weixin.terms.URL_WEB_AUTHORIZE
import io.github.rcarlosdasilva.weixin.terms.data.WebAuthorizeScope
import mu.KotlinLogging
import org.apache.commons.codec.digest.DigestUtils
import java.util.*

/**
 * 公众号认证API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpAuthentication(private val account: Mp) : Api(account) {

  private val logger = KotlinLogging.logger { }

  /**
   * 获取微信服务凭证(access_token)
   *
   * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。
   *
   * @return 凭证
   */
  @Synchronized
  fun askAccessToken(): String {
    var token = CacheHandler.of(AccessToken::class.java).get(account.key)
    if (token != null && !token.isExpired) {
      return token.accessToken!!
    }

    if (null == token) {
      logger.debug { "For:{${account.key}} >> 无缓存过的access_token，请求access_token" }
    } else {
      logger.debug { "For:{${account.key}} >> 因access_token过期，重新请求。失效的access_token：[{${token!!.accessToken}}]" }
    }

    for (i in 1..3) {
      token = CacheHandler.of(AccessToken::class.java).get(account.key)
      if (token != null && !token.isExpired) {
        break
      }

      val identifier = CacheHandler.of(AccessToken::class.java).lock(account.key, 2000, true)
      if (identifier == null) {
        try {
          Thread.sleep(100)
          continue
        } catch (e: InterruptedException) {
          Thread.currentThread().interrupt()
        }
      }

      token = if (account.proxyWithOp) {
        // 使用微信开放平台获取access_token。在公众号授权后，会自动获取第一次授权方的access_token
        val refreshToken = if (null == token) account.refreshToken else token.refreshToken
        refreshLicensedAccessToken(account.appId, refreshToken!!)
      } else {
        // 使用公众号appid和appsecret获取access_token
        requestAccessToken()
      }
      CacheHandler.of(AccessToken::class.java).unlock(account.key, identifier!!)
    }

    return token?.accessToken ?: throw ApiRequestException("askAccessToken - 无法获取access_token")
  }

  /**
   * 刷新微信服务凭证(access_token)
   *
   * @return 凭证
   */
  fun refreshAccessToken(): String =
      if (account.proxyWithOp) {
        refreshLicensedAccessToken(account.appId, account.refreshToken!!)
      } else {
        requestAccessToken()
      }.accessToken!!

  /**
   * 更新微信服务凭证(access_token)为指定的token
   *
   * @param token 指定token
   * @param expiredAt token过期时间点的毫秒数（并非多长时间过期），值为0则按照7200秒内有效（同微信规则）
   */
  fun updateAccessToken(token: String, expiredAt: Long) {
    if (token.isBlank() || expiredAt <= 0) {
      logger.warn { "For:{$account.key} >> 使用错误的数据更新token： {$token}, {$expiredAt}" }
      return
    }

    var expiresIn = 7200L * 1000L
    if (expiredAt > 0) {
      expiresIn = expiredAt - System.currentTimeMillis()
    }
    val responseMock = "{'access_token':'$token','expires_in':$expiresIn}"
    val accessToken = JsonHandler.fromJson(responseMock, MpAccessTokenResponse::class.java)
    accessToken.accountKey = account.key
    CacheHandler.of(AccessToken::class.java).put(account.key, accessToken)
  }

  /**
   * 更新使用开放平台的授权方的access_token
   *
   * @return 请求结果
   */
  @Synchronized
  private fun refreshLicensedAccessToken(licensorAppId: String, refreshToken: String): AccessToken {
    val response = Weixin.op().authentication.refreshLicensorAccessToken(licensorAppId, refreshToken)

    return response.licensedAccessToken.apply {
      accountKey = account.key
      CacheHandler.of(AccessToken::class.java).put(account.key, this)
      logger.debug { "For:{${account.key}} >> 开放平台更新授权方access_token：[{$accessToken}]" }
    }
  }

  /**
   * 真正请求access_token代码
   *
   * @return 请求结果
   */
  @Synchronized
  private fun requestAccessToken(): AccessToken {
    logger.debug("For:{} >> 正在获取access_token", account.key)
    val requestModel = MpAccessTokenRequest(account.appId, account.appSecret!!)
    val accessToken = get(MpAccessTokenResponse::class.java, requestModel)

    return accessToken.apply {
      accountKey = account.key
      CacheHandler.of(AccessToken::class.java).put(account.key, this)
      logger.debug { "For:{$account.key} >> 获取到access_token：[{$accessToken}]" }

      val listener = Weixin.registry.listener(MpAccessTokenUpdatedListener::class.java)
      listener?.run {
        logger.debug("For:{} >> 调用监听器AccessTokenUpdatedListener", account.key)
        updated(
            account.key, account.appId, accessToken.accessToken!!, expiresIn
        )
      }
    }
  }

  /**
   * 获取JS SDK凭证(jsapi_ticket)
   *
   * jsapi_ticket是公众号用于调用微信JS接口的临时票据。
   *
   * @return 凭证
   */
  fun askJsapiTicket(): String {
    var ticket = CacheHandler.of(JsapiTicket::class.java).get(account.key)
    if (ticket != null && !ticket.isExpired) {
      return ticket.ticket!!
    }

    if (null == ticket) {
      logger.debug { "For:{${account.key}} >> 无缓存过的jsapi_ticket，请求jsapi_ticket" }
    } else {
      logger.debug { "For:{${account.key}} >> 因jsapi_ticket过期，重新请求。失效的jsapi_ticket：[{$ticket}]" }
    }

    for (i in 1..3) {
      ticket = CacheHandler.of(JsapiTicket::class.java).get(account.key)
      if (ticket != null && !ticket.isExpired) {
        break
      }

      val identifier = CacheHandler.of(JsapiTicket::class.java).lock(account.key, 2000, true)
      if (identifier == null) {
        try {
          Thread.sleep(100)
          continue
        } catch (e: InterruptedException) {
          Thread.currentThread().interrupt()
        }
      }

      ticket = requestJsapiTicket()
      CacheHandler.of(AccessToken::class.java).unlock(account.key, identifier!!)
    }

    return ticket?.ticket ?: throw ApiRequestException("askJsapiTicket - 无法获取jsapi_ticket")
  }

  /**
   * 刷新JS SDK凭证(jsapi_ticket)
   */
  fun refreshJsapiTicket(): String = requestJsapiTicket().ticket!!

  /**
   * 更新JS SDK凭证(jsapi_ticket)为指定的ticket
   *
   * @param ticket 指定ticket
   * @param expiredAt token过期时间点的毫秒数（并非多长时间过期），值如果小于0则按照7200秒内有效（同微信规则）
   */
  fun updateJsapiTicket(ticket: String, expiredAt: Long) {
    if (ticket.isBlank() || expiredAt <= 0) {
      logger.warn("For:{} >> 使用错误的数据更新ticket： {}, {}", account.key, ticket, expiredAt)
      return
    }

    var expiresIn = 7200L * 1000L
    if (expiredAt > 0) {
      expiresIn = expiredAt - System.currentTimeMillis()
    }
    val responseMock = "{'ticket':'$ticket','expires_in':$expiresIn}"
    val jsapiTicket = JsonHandler.fromJson(responseMock, MpJsapiTicketResponse::class.java)
    jsapiTicket.accountKey = account.key
    CacheHandler.of(JsapiTicket::class.java).put(account.key, jsapiTicket)
  }

  /**
   * 真正请求jsapi_ticket代码
   *
   * @return 请求结果
   */
  @Synchronized
  private fun requestJsapiTicket(): MpJsapiTicketResponse {
    logger.debug("For:{} >> 正在获取jsapi_ticket", account.key)
    val requestModel = JsapiTicketRequest()
    val jsapiTicket = get(MpJsapiTicketResponse::class.java, requestModel)

    return jsapiTicket.apply {
      accountKey = account.key
      CacheHandler.of(JsapiTicket::class.java).put(account.key, this)
      logger.debug { "For:{${account.key}} >> 获取jsapi_ticket：[{$jsapiTicket}]" }

      val listener = Weixin.registry.listener(MpJsapiTicketUpdatedListener::class.java)
      listener?.run {
        logger.debug { "For:{${account.key}} >> 调用监听器JsapiTicketUpdatedListener" }
        updated(account.key, account.appId, jsapiTicket.ticket!!, expiresIn)
      }
    }
  }

  /**
   * 通过code换取网页授权access_token
   *
   * 这里通过code换取的是一个特殊的网页授权access_token,与基础支持中的access_token（
   * 该access_token用于调用其他接口）不同。如果网页授权的作用域为snsapi_base，
   * 则本步骤中获取到网页授权access_token的同时，也获取到了openid，snsapi_base式的网页授权流程即到此为止。
   *
   * 首先保证通过网页授权链接（参考 `Weixin.with(key).url().webAuthorize()`
   * ），获取微信返回的code。code作为换取access_token的票据，每次用户授权带上的code将不一样，
   * code只能使用一次，5分钟未被使用自动过期。
   *
   * @param code code票据
   * @return [WaAccessTokenResponse]
   */
  fun askWebAuthorizeAccessToken(code: String): WaAccessTokenResponse? =
      get(WaAccessTokenResponse::class.java, WaAccessTokenRequest(account.appId, account.appSecret!!, code))

  /**
   * 刷新access_token
   *
   * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
   * refresh_token拥有较长的有效期（7天、30天、60天、90天），当refresh_token失效的后， 需要用户重新授权。
   *
   * @param refreshToken 刷新token
   * @return [WaAccessTokenResponse]
   */
  fun refreshWebAuthorizeAccessToken(refreshToken: String): WaAccessTokenResponse? =
      get(WaAccessTokenResponse::class.java, WaAccessTokenRefreshRequest(account.appId, refreshToken))

  /**
   * 检验授权凭证（access_token）是否有效
   *
   * @param waAccessToken 网页授权access_token
   * @param openId OpenId
   * @return 是否有效
   */
  fun verifyWebAuthorizeAccessToken(waAccessToken: String, openId: String): Boolean? =
      get(Boolean::class.java, WaAccessTokenVerifyRequest(waAccessToken, openId))

  /**
   * 网页授权获取code地址跳转
   *
   * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，
   * 默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开一个微信页面，
   * 该页面最终会跳转到开发者指定的页面。这里会将开发者指定的页面处理成可被微信授权的地址链接。
   *
   * @param scope [WebAuthorizeScope] 应用授权作用域，snsapi_base（不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo（弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权， 也能获取其信息）
   * @param redirectTo 授权后跳转到url
   * @param param 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
   * @return 授权链接
   */
  @JvmOverloads
  fun webAuthorize(scope: WebAuthorizeScope, redirectTo: String, param: String? = null): String =
      "$URL_WEB_AUTHORIZE?appid=${account.appId}&redirect_uri=${urlEncode(redirectTo)}&response_type=code" +
          "&scope=$scope${param?.let { "&state=$param" } ?: ""}#wechat_redirect"

  /**
   * JS-SDK使用权限签名算法
   *
   * @param url 当前网页的URL，不包含#及其后面部分
   * @return 签名
   */
  fun generateJsapiSignature(url: String): JsapiSignature {
    val ticket = askJsapiTicket()
    val timestamp = System.currentTimeMillis() / 1000
    val nonce = UUID.randomUUID().toString()

    val signatureRaw = "jsapi_ticket=$ticket&noncestr=$nonce&timestamp=$timestamp&url=$url"

    return JsapiSignature(account.appId, ticket, DigestUtils.sha1Hex(signatureRaw), url, timestamp.toString(), nonce)
  }

}