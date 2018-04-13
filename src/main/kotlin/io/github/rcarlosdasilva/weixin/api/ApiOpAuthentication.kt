package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.core.ApiRequestException
import io.github.rcarlosdasilva.weixin.core.ExecuteException
import io.github.rcarlosdasilva.weixin.core.Weixin
import io.github.rcarlosdasilva.weixin.handler.CacheHandler
import io.github.rcarlosdasilva.weixin.handler.GeneralCacheableObject
import io.github.rcarlosdasilva.weixin.handler.urlEncode
import io.github.rcarlosdasilva.weixin.model.AccessToken
import io.github.rcarlosdasilva.weixin.model.Op
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.OpAccessTokenResponse
import io.github.rcarlosdasilva.weixin.model.response.OpGetLicenseInformationResponse
import io.github.rcarlosdasilva.weixin.model.response.OpGetLicensorOptionResponse
import io.github.rcarlosdasilva.weixin.model.response.OpPreAuthCodeResponse
import io.github.rcarlosdasilva.weixin.terms.*
import mu.KotlinLogging

/**
 * 开放平台三方平台认证API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiOpAuthentication(private val account: Op) : Api(account) {

  private val logger = KotlinLogging.logger { }

  /**
   * 获取微信开放平台服务凭证(component_access_token)
   *
   * 第三方平台component_access_token是第三方平台的下文中接口的调用凭据，也叫做令牌（component_access_token）。
   * 每个令牌是存在有效期（2小时）的，且令牌的调用不是无限制的，请第三方平台做好令牌的管理，在令牌快过期时（比如1小时50分）再进行刷新。
   *
   * @return component_access_token
   */
  fun askAccessToken(): String {
    var token = CacheHandler.of(AccessToken::class.java).get(DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN)
    if (token != null && !token.isExpired) {
      return token.accessToken!!
    }

    if (null == token) {
      logger.debug("For: >> 无缓存过的component_access_token，请求access_token")
    } else {
      logger.debug { "For: >> 因component_access_token过期，重新请求。失效的access_token：[{${token!!.accessToken}}]" }
    }

    for (i in 1..3) {
      token = CacheHandler.of(AccessToken::class.java).get(DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN)
      if (token != null && !token.isExpired) {
        break
      }

      val identifier =
        CacheHandler.of(AccessToken::class.java).lock(DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN, 2000, true)
      if (identifier == null) {
        try {
          Thread.sleep(100)
          continue
        } catch (e: InterruptedException) {
          Thread.currentThread().interrupt()
        }
      }

      token = requestAccessToken()
      CacheHandler.of(AccessToken::class.java).unlock(DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN, identifier!!)
    }

    return token?.accessToken ?: throw ApiRequestException("askAccessToken - 无法获取开放平台component_access_token")
  }

  @Synchronized
  private fun requestAccessToken(): AccessToken? {
    logger.debug("For: >> 正在获取component_access_token")
    val cacheableObject =
      CacheHandler.of(GeneralCacheableObject::class.java).get(DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET)
    cacheableObject ?: run {
      logger.warn("获取不到微信开放平台的ticket，可能微信还未将ticket通知到当前服务器")
      return null
    }

    val ticket = cacheableObject.obj.toString()
    val accessToken =
      post(OpAccessTokenResponse::class.java, OpAccessTokenRequest(account.appId, account.appSecret, ticket))

    return accessToken?.apply {
      accountMark = UNIQUE_OP_ACCOUNT_CACHE_KEY
      CacheHandler.of(AccessToken::class.java).put(DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN, accessToken)
      logger.debug("For: >> 获取到access_token：[{}]", accessToken.accessToken)

      val listener = Weixin.registry.listener(OpAccessTokenUpdatedListener::class.java)
      listener?.run {
        logger.debug("For: >> 调用监听器OpenPlatformAccessTokenUpdatedListener")
        updated(accessToken.accessToken!!, expiresIn)
      }
    } ?: throw ApiRequestException("无法正常请求component_access_token")
  }

  /**
   * 获取预授权码pre_auth_code
   *
   * 该API用于获取预授权码。预授权码用于公众号或小程序授权时的第三方平台方安全验证。
   *
   * **直接调用[authorize]接口，不需要单独调用获取预授权码**
   *
   * @return pre_auth_code
   */
  fun askPreAuthCode(): String {
    val responseModel = post(OpPreAuthCodeResponse::class.java, OpAuthPreAuthCodeRequest(account.appId))
    return responseModel?.preAuthCode ?: throw ApiRequestException("无法获取微信开放平台预授权码")
  }

  /**
   * 使用授权码换取公众号或小程序的接口调用凭据和授权信息
   *
   * 该API用于使用授权码换取授权公众号或小程序的授权信息，并换取authorizer_access_token和authorizer_refresh_token。
   * 授权码的获取，需要在用户在第三方平台授权页中完成授权流程后，在回调URI中通过URL参数提供给第三方平台方。
   * **请注意，由于现在公众号或小程序可以自定义选择部分权限授权给第三方平台，
   * 因此第三方平台开发者需要通过该接口来获取公众号或小程序具体授权了哪些权限，而不是简单地认为自己声明的权限就是公众号或小程序授权的权限。**
   *
   * **已使用[NotificationHandlerProxy]自动获取接口调用凭据和授权信息，如无特殊需要，无需调用**
   *
   * @param license 授权码
   * @return [OpGetLicenseInformationResponse]
   */
  fun getLicensingInformation(license: String): OpGetLicenseInformationResponse {
    val responseModel =
      post(OpGetLicenseInformationResponse::class.java, OpGetLicensingInformationRequest(account.appId, license))

    return responseModel?.apply {
      val listener = Weixin.registry.listener(OpLisensorMpAccessTokenUpdatedListener::class.java)
      listener?.run {
        logger.debug("For: >> 调用监听器OpenPlatformLisensorAccessTokenUpdatedListener")
        updated(
          responseModel.licensingInformation!!.appId!!,
          responseModel.licensedAccessToken.accessToken!!,
          responseModel.licensedAccessToken.refreshToken!!,
          responseModel.licensedAccessToken.expiresIn
        )
      }
    } ?: throw ApiRequestException("无法获取授权方的授权信息")
  }

  /**
   * 获取（刷新）授权公众号或小程序的接口调用凭据（令牌）
   *
   * 该API用于在授权方令牌（authorizer_access_token）失效时，可用刷新令牌（authorizer_refresh_token）获取新的令牌。
   * **请注意，此处token是2小时刷新一次，开发者需要自行进行token的缓存，避免token的获取次数达到每日的限定额度。
   * 缓存方法可以参考：http://mp.weixin.qq.com/wiki/2/88b2bf1265a707c031e51f26ca5e6512.html**
   *
   * **这里的授权access_token会自动更新，开发者无需关心**
   *
   * @param licensorAppId 授权方appid
   * @param refreshToken 授权方的刷新令牌，刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
   * @return [OpGetLicenseInformationResponse]
   */
  fun refreshLicensorAccessToken(licensorAppId: String, refreshToken: String): OpGetLicenseInformationResponse {
    val responseModel = post(
      OpGetLicenseInformationResponse::class.java,
      OpRefreshLicensorAccessTokenRequest(account.appId, licensorAppId, refreshToken)
    )

    return responseModel?.apply {
      val listener = Weixin.registry.listener(OpLisensorMpAccessTokenUpdatedListener::class.java)
      listener?.run {
        logger.debug("For: >> 调用监听器OpenPlatformLisensorAccessTokenUpdatedListener")
        updated(
          responseModel.licensingInformation!!.appId!!,
          responseModel.licensedAccessToken.accessToken!!,
          responseModel.licensedAccessToken.refreshToken!!,
          responseModel.licensedAccessToken.expiresIn
        )
      }
    } ?: throw ApiRequestException("无法获取授权方的授权信息")
  }

  /**
   * 获取授权方的帐号基本信息
   *
   * 该API用于获取授权方的基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL。
   * 需要特别记录授权方的帐号类型，在消息及事件推送时，对于不具备客服接口的公众号，需要在5秒内立即响应；
   * 而若有客服接口，则可以选择暂时不响应，而选择后续通过客服接口来发送消息触达粉丝。
   *
   * **已使用[NotificationHandlerProxy]自动获取接口调用凭据和授权信息，如无特殊需要，无需调用**
   *
   * @param licensorAppId 授权方appid
   * @return [OpGetLicenseInformationResponse]
   */
  fun getLicensorInformation(licensorAppId: String): OpGetLicenseInformationResponse = post(
    OpGetLicenseInformationResponse::class.java,
    OpGetLicensorInformationRequest(account.appId, licensorAppId)
  ) ?: throw ApiRequestException("无法获取授权方的授权信息")

  /**
   * 获取授权方的选项设置信息
   *
   * 该API用于获取授权方的公众号或小程序的选项设置信息，如：地理位置上报，语音识别开关，
   * 多客服开关。注意，获取各项选项设置信息，需要有授权方的授权，详见权限集说明。
   *
   * @param licensorAppId 授权方appid
   * @param optionName 选项名称
   * @return [OpGetLicensorOptionResponse]
   */
  fun getLicensorOption(licensorAppId: String, optionName: String): OpGetLicensorOptionResponse =
    post(OpGetLicensorOptionResponse::class.java, OpGetLicensorOptionRequest(account.appId, licensorAppId, optionName))
        ?: throw ApiRequestException("无法获取授权方的选项设置信息")

  /**
   * 设置授权方的选项信息
   *
   * 该API用于设置授权方的公众号或小程序的选项信息，如：地理位置上报，语音识别开关，
   * 多客服开关。注意，设置各项选项设置信息，需要有授权方的授权，详见权限集说明。
   *
   * @param licensorAppId 授权方appid
   * @param optionName 选项名称
   * @param value 选项值
   * @return boolean
   */
  fun setLicensorOption(licensorAppId: String, optionName: String, value: String): Boolean =
    post(Boolean::class.java, OpSetLicensorOptionRequest(account.appId, licensorAppId, optionName, value))
        ?: throw ApiRequestException("无法设置授权方的选项设置信息")

  /**
   * 微信开放平台第三方平台授权页面地址
   *
   * 第三方平台方可以在自己的网站:中放置“微信公众号授权”或者“小程序授权”的入口，引导公众号和小程序管理员进入授权页。 授权页网址为 [https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=xxxx&amp;pre_auth_code=xxxxx&amp;redirect_uri=xxxx](#)
   * ， 该网址中第三方平台方需要提供第三方平台方appid、预授权码和回调URI<br></br>
   * 授权流程完成后，授权页会自动跳转进入回调URI，并在URL参数中返回授权码和过期时间**(redirect_url?auth_code=xxx&amp;expires_in=600)**
   *
   * ** 这个方法会自动获取新的预授权码，不需要再去调用 [.askPreAuthCode]接口** <br></br>
   * 授权码(auth_code)会在回调地址中当参数传递回来，同时，微信服务器会通知当前服务器“授权成功通知(InfoType =
   * authorized)”。注意：这里
   * [NotificationHandlerProxy]会在这个微信的通知中，自动使用授权码获取以下信息：,<br></br>
   * 1. “授权信息”和“授权方接口调用凭据以及凭据刷新码<br></br>
   * 2. 授权方的账号基本信息（暂时不提供小程序的方式，可手动获取） ，无需再利用回调地址中的auth_code获取<br></br>
   *
   * @param redirectTo 回调URI
   * @return 授权页面地址
   */
  fun authorize(redirectTo: String): String =
    "$URL_OPEN_PLATFORM_AUTHORIZE?component_appid=${account.appId}&pre_auth_code=${askPreAuthCode()}" +
        "&redirect_uri=${urlEncode(redirectTo)}"

  /**
   * 第三方平台对其所有API调用次数清零（只与第三方平台相关，与公众号无关，接口如api_component_token）
   *
   * @return 如果是超出清零的请求次数限制返回false
   */
  fun resetQuota(): Boolean =
    try {
      post(Boolean::class.java, OpResetQuotaRequest(account.appId))!!
    } catch (ex: ExecuteException) {
      if (ex.code === ResultCode.RESULT_48006) {
        @Suppress("UNUSED_EXPRESSION") false
      }
      throw ex
    }

}
