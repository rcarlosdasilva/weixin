package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*

/**
 * 微信Token请求模型，使用appid + appsecret方式
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MpAccessTokenRequest(
  private val appId: String,
  private val appSecret: String
) : MpRequest() {
  override fun toString(): String =
    "$URL_CERTIFICATE_TOKEN?grant_type=$ACCESS_TOKEN_GRANT_TYPE&appid=$appId&secret=$appSecret"
}

/**
 * JS Ticket请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class JsapiTicketRequest : MpRequest() {
  override fun toString(): String = "$URL_CERTIFICATE_JSAPI_TICKET?access_token=$accessToken&type=$JSAPI_TICKET_TYPE"
}


/**
 * 网页授权Token请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class WaAccessTokenRequest(
  private val appId: String,
  private val appSecret: String,
  private val code: String
) : MpRequest() {
  override fun toString(): String =
    "$URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN?appid=$appId&secret=$appSecret&code=$code&grant_type=$WEB_AUTHORIZE_ACCESS_TOKEN_GRANT_TYPE"
}

/**
 * 网页授权Token刷新请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class WaAccessTokenRefreshRequest(
  private val appId: String,
  private val refreshToken: String
) : MpRequest() {
  override fun toString(): String =
    "$URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_REFRESH?appid=$appId&grant_type=$WEB_AUTHORIZE_ACCESS_TOKEN_REFRESH_GRANT_TYPE&refresh_token=$refreshToken"
}

/**
 * 网页授权Token验证请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class WaAccessTokenVerifyRequest(
  private val waAccessToken: String,
  private val openId: String
) : MpRequest() {
  override fun toString(): String =
    "$URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_VERIFY?access_token=$waAccessToken&openid=$openId"
}

// ------------------------- 开放平台 ------------------------------

/**
 * 获取第三方平台component_access_token请求模型
 *
 * @param appId 第三方平台appid
 * @param appSecret 第三方平台appsecret
 * @param ticket 微信后台推送的ticket，此ticket会定时推送
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class OpAccessTokenRequest(
  @SerializedName("component_appid") val appId: String,
  @SerializedName("component_appsecret") val appSecret: String,
  @SerializedName("component_verify_ticket") val ticket: String
) : OpRequest() {
  override fun toString(): String = URL_OPEN_PLATFORM_ACCESS_TOKEN
}

/**
 * 获取预授权码pre_auth_code请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class OpAuthPreAuthCodeRequest(
  @SerializedName("component_appid") private val appId: String
) : OpRequest() {
  init {
    this.path = URL_OPEN_PLATFORM_PRE_AUTH_CODE
  }
}


/**
 * 获取接口调用凭据和授权信息请求模型
 *
 * @param appId 第三方平台appid
 * @param authCode 授权code,会在授权成功时返回给第三方平台，详见第三方平台授权流程说明
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class OpGetLicensingInformationRequest(
  @SerializedName("component_appid") private val appId: String,
  @SerializedName("authorization_code") private val authCode: String
) : OpRequest() {
  init {
    this.path = URL_OPEN_PLATFORM_LICENSE_INFORMATION
  }
}

/**
 * 刷新授权方access_token请求模型
 *
 * @param appId 第三方平台appid
 * @param licensorAppId 授权方appid
 * @param licensorRefreshToken 授权方的刷新令牌，刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class OpRefreshLicensorAccessTokenRequest(
  @SerializedName("component_appid") private val appId: String,
  @SerializedName("authorizer_appid") private val licensorAppId: String,
  @SerializedName("authorizer_refresh_token") private val licensorRefreshToken: String
) : OpRequest() {
  init {
    this.path = URL_OPEN_PLATFORM_REFRESH_LICENSOR_ACCESS_TOKEN
  }
}

/**
 * 获取授权方信息请求模型
 */
class OpGetLicensorInformationRequest(
  @SerializedName("component_appid") private val appId: String,
  @SerializedName("authorizer_appid") private val licensorAppId: String
) : OpRequest() {
  init {
    this.path = URL_OPEN_PLATFORM_LICENSOR_INFORMATION
  }
}

/**
 * 获取授权方选项信息请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class OpGetLicensorOptionRequest(
  @SerializedName("component_appid") private val appId: String,
  @SerializedName("authorizer_appid") private val licensorAppId: String,
  @SerializedName("option_name") private val optionName: String
) : OpRequest() {
  init {
    this.path = URL_OPEN_PLATFORM_GET_LICENSOR_OPTION
  }
}

/**
 * 设置授权方选项信息请求模型
 *
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
class OpSetLicensorOptionRequest(
  @SerializedName("component_appid") private val appId: String,
  @SerializedName("authorizer_appid") private val licensorAppId: String,
  @SerializedName("option_name") private val optionName: String,
  @SerializedName("option_value") private val value: String
) : OpRequest() {
  init {
    this.path = URL_OPEN_PLATFORM_SET_LICENSOR_OPTION;
  }
}

/**
 * 代公众号调用接口调用次数清零API请求模型
 */
class OpResetQuotaRequest(@SerializedName("component_appid") private val appId: String) : OpRequest() {
  init {
    this.path = URL_OPEN_PLATFORM_RESET_QUOTA
  }
}
