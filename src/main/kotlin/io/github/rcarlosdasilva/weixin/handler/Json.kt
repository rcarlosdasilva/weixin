package io.github.rcarlosdasilva.weixin.handler

import com.google.common.base.Preconditions
import com.google.common.base.Strings
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.github.rcarlosdasilva.weixin.terms.*
import org.slf4j.LoggerFactory
import java.io.IOException

/**
 * JSON工具
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object JsonHandler {

  private val gson = GsonBuilder().enableComplexMapKeySerialization()
    .disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .registerTypeAdapterFactory(CustomTypeAdapterFactory())
    .addSerializationExclusionStrategy(object : ExclusionStrategy {

      override fun shouldSkipField(field: FieldAttributes): Boolean {
        val ann = field.getAnnotation(Freeze::class.java)
        return ann != null
      }

      override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
      }
    }).create()

  fun <T> toJson(obj: Any, clazz: Class<T>): String {
    Preconditions.checkNotNull(obj)
    return gson.toJson(obj, clazz)
  }

  fun <T> fromJson(json: String, clazz: Class<T>): T {
    Preconditions.checkNotNull(json)
    return gson.fromJson(json, clazz)
  }

}


/**
 * 标记某个字段不被Gson序列化为JSON字符串
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@Retention(value = AnnotationRetention.RUNTIME)
@Target(allowedTargets = [AnnotationTarget.FIELD])
annotation class Freeze


class CustomTypeAdapterFactory : TypeAdapterFactory {

  override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
    val clazz = type.rawType
    return when (clazz) {
      MessageSendWithMassRequestUser::class.java -> MessageSendWithMassRequestUserTypeAdapter().nullSafe()
      AccessTokenResponse::class.java -> AccessTokenResponseTypeAdapter().nullSafe()
      OpenPlatformAuthAccessTokenResponse::class.java -> OpenPlatformAuthAccessTokenResponseTypeAdapter().nullSafe()
      OpenPlatformAuthGetLicenseInformationResponse::class.java -> OpenPlatformAuthGetLicenseInformationResponseTypeAdapter().nullSafe()
      else -> null
    }
  }

}


class MessageSendWithMassRequestUserTypeAdapter : TypeAdapter<MessageSendWithMassRequestUser>() {

  @Throws(IOException::class)
  override fun write(out: JsonWriter, value: MessageSendWithMassRequestUser) {
    if (!Strings.isNullOrEmpty(value.getUser())) {
      out.value(value.getUser())
    } else if (value.getUsers() != null && value.getUsers().size() > 1) {
      out.beginArray()
      for (user in value.getUsers()) {
        out.value(user)
      }
      out.endArray()
    } else {
      out.nullValue()
    }
  }

  @Throws(IOException::class)
  override fun read(`in`: JsonReader): MessageSendWithMassRequestUser? {
    // 不需要实现
    return null
  }

}


class AccessTokenResponseTypeAdapter : TypeAdapter<AccessTokenResponse>() {

  private val logger = LoggerFactory.getLogger(javaClass)

  @Throws(IOException::class)
  override fun write(out: JsonWriter, value: AccessTokenResponse) {
  }

  @Throws(IOException::class)
  override fun read(`in`: JsonReader): AccessTokenResponse {
    val model = AccessTokenResponse()

    `in`.beginObject()
    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        WEIXIN_ACCESS_TOKEN_KEY -> model.setAccessToken(`in`.nextString())
        WEIXIN_ACCESS_TOKEN_EXPIRES_IN_KEY -> model.setExpiresIn(`in`.nextInt())
        else -> if (`in`.hasNext()) {
          val value = `in`.nextString()
          logger.warn("未知的json键值： [{}: {}]", key, value)
        }
      }
    }
    `in`.endObject()

    return model
  }

}


class OpenPlatformAuthAccessTokenResponseTypeAdapter : TypeAdapter<OpenPlatformAuthAccessTokenResponse>() {

  private val logger = LoggerFactory.getLogger(javaClass)

  @Throws(IOException::class)
  override fun write(out: JsonWriter, value: OpenPlatformAuthAccessTokenResponse) {
    // 不需要实现
  }

  @Throws(IOException::class)
  override fun read(`in`: JsonReader): OpenPlatformAuthAccessTokenResponse {
    val model = OpenPlatformAuthAccessTokenResponse()

    `in`.beginObject()
    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_ACCESS_TOKEN_KEY -> model.setAccessToken(`in`.nextString())

        WEIXIN_ACCESS_TOKEN_EXPIRES_IN_KEY -> model.setExpiresIn(`in`.nextInt())
        else -> if (`in`.hasNext()) {
          val value = `in`.nextString()
          logger.warn("未知的json键值： [{}: {}]", key, value)
        }
      }
    }
    `in`.endObject()

    return model
  }

}


class OpenPlatformAuthGetLicenseInformationResponseTypeAdapter :
  TypeAdapter<OpenPlatformAuthGetLicenseInformationResponse>() {

  private val logger = LoggerFactory.getLogger(javaClass)

  @Throws(IOException::class)
  override fun write(out: JsonWriter, value: OpenPlatformAuthGetLicenseInformationResponse) {
    // 不需要实现
  }

  @Throws(IOException::class)
  override fun read(`in`: JsonReader): OpenPlatformAuthGetLicenseInformationResponse {
    val model = OpenPlatformAuthGetLicenseInformationResponse()

    `in`.beginObject()
    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_LICENSED_INFORMATION_KEY -> readLicensedAccessToken(`in`, model)
        OPEN_PLATFORM_AUTH_LICENSOR_INFORMATION_KEY -> readLicensorInformation(`in`, model)
        OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY -> model.getLicensedAccessToken().setAccessToken(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY -> model.getLicensedAccessToken().setExpiresIn(
          `in`.nextInt()
        )
        OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY -> model.getLicensedAccessToken().setRefreshToken(`in`.nextString())
        else -> if (`in`.hasNext()) {
          val value = `in`.nextString()
          logger.warn(LOG_UNKNOWN_JSON_KEY, key, value)
        }
      }
    }

    `in`.endObject()

    return model
  }

  @Throws(IOException::class)
  private fun readLicensedAccessToken(
    `in`: JsonReader,
    model: OpenPlatformAuthGetLicenseInformationResponse
  ) {
    val licensingInformation = LicensingInformation()
    `in`.beginObject()

    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_LICENSED_APPID_ALIAS_KEY, OPEN_PLATFORM_AUTH_LICENSED_APPID_KEY -> licensingInformation.setAppId(
          `in`.nextString()
        )
        OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY -> model.getLicensedAccessToken().setAccessToken(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY -> model.getLicensedAccessToken().setExpiresIn(
          `in`.nextInt()
        )
        OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY -> model.getLicensedAccessToken().setRefreshToken(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSED_FUNCTIONS_INFO_KEY -> {
          `in`.beginArray()

          while (`in`.hasNext()) {
            `in`.beginObject()
            `in`.nextName()
            `in`.beginObject()
            `in`.nextName()
            licensingInformation.addLicencedFunction(`in`.nextInt())
            `in`.endObject()
            `in`.endObject()
          }

          `in`.endArray()
        }
        else -> if (`in`.hasNext()) {
          val value = `in`.nextString()
          logger.warn(LOG_UNKNOWN_JSON_KEY, key, value)
        }
      }
    }

    `in`.endObject()
    model.setLicensingInformation(licensingInformation)
  }

  @Throws(IOException::class)
  private fun readLicensorInformation(
    `in`: JsonReader,
    model: OpenPlatformAuthGetLicenseInformationResponse
  ) {
    val licensorInfromation = LicensorInfromation()
    `in`.beginObject()

    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_LICENSOR_NICKNAME_KEY -> licensorInfromation.setNickName(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSOR_LOGO_KEY -> licensorInfromation.setLogo(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_TYPE_KEY -> {
          `in`.beginObject()
          `in`.nextName()
          licensorInfromation.setAccountType(`in`.nextInt())
          `in`.endObject()
        }
        OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_VERIFIED_TYPE_KEY -> {
          `in`.beginObject()
          `in`.nextName()
          licensorInfromation.setAccountVerifiedType(`in`.nextInt())
          `in`.endObject()
        }
        OPEN_PLATFORM_AUTH_LICENSOR_MPID_KEY -> licensorInfromation.setMpId(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSOR_PRINCIPAL_NAME_KEY -> licensorInfromation.setPrincipalName(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_NAME_KEY -> licensorInfromation.setAccountName(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSOR_QRCODE_URL_KEY -> licensorInfromation.setQrCodeUrl(`in`.nextString())
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_INFO_KEY -> readBusinessInfo(`in`, licensorInfromation)
        else -> if (`in`.hasNext()) {
          val value = `in`.nextString()
          logger.warn(LOG_UNKNOWN_JSON_KEY, key, value)
        }
      }
    }

    `in`.endObject()
    model.setLicensorInfromation(licensorInfromation)
  }

  @Throws(IOException::class)
  private fun readBusinessInfo(`in`: JsonReader, licensorInfromation: LicensorInfromation) {
    `in`.beginObject()
    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_STORE_KEY -> licensorInfromation.setBusinessStoreOpened(`in`.nextInt() == GLOBAL_TRUE_NUMBER)
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SCAN_KEY -> licensorInfromation.setBusinessScanOpened(`in`.nextInt() == GLOBAL_TRUE_NUMBER)
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_PAY_KEY -> licensorInfromation.setBusinessPayOpened(`in`.nextInt() == GLOBAL_TRUE_NUMBER)
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_CARD_KEY -> licensorInfromation.setBusinessCardOpened(`in`.nextInt() == GLOBAL_TRUE_NUMBER)
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SHAKE_KEY -> licensorInfromation.setBusinessShakeOpened(`in`.nextInt() == GLOBAL_TRUE_NUMBER)
        else -> if (`in`.hasNext()) {
          val value = `in`.nextString()
          logger.warn(LOG_UNKNOWN_JSON_KEY, key, value)
        }
      }
    }
    `in`.endObject()
  }

  companion object {

    private val LOG_UNKNOWN_JSON_KEY = "未知的json键值： [{}: {}]"
  }

}