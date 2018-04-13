package io.github.rcarlosdasilva.weixin.handler

import com.google.common.base.Preconditions
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.github.rcarlosdasilva.weixin.model.response.OpAccessTokenResponse
import io.github.rcarlosdasilva.weixin.model.response.OpGetLicenseInformationResponse
import io.github.rcarlosdasilva.weixin.terms.*
import io.github.rcarlosdasilva.weixin.terms.data.MpType
import io.github.rcarlosdasilva.weixin.terms.data.OpMpAuthentication
import mu.KotlinLogging

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


@Suppress("UNCHECKED_CAST")
class CustomTypeAdapterFactory : TypeAdapterFactory {

  override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? =
    when (type.rawType) {
//      MessageSendWithMassRequestUser::class.java -> MessageSendWithMassRequestUserTypeAdapter().nullSafe()
      OpAccessTokenResponse::class.java -> OpAccessTokenResponseTypeAdapter().nullSafe()
      OpGetLicenseInformationResponse::class.java -> OpGetLicenseInformationResponseTypeAdapter().nullSafe()
      else -> null
    } as TypeAdapter<T>?

}


//class MessageSendWithMassRequestUserTypeAdapter : TypeAdapter<MessageSendWithMassRequestUser>() {
//
//  override fun write(out: JsonWriter, value: MessageSendWithMassRequestUser) {
//    if (!Strings.isNullOrEmpty(value.getUser())) {
//      out.value(value.getUser())
//    } else if (value.getUsers() != null && value.getUsers().size() > 1) {
//      out.beginArray()
//      for (user in value.getUsers()) {
//        out.value(user)
//      }
//      out.endArray()
//    } else {
//      out.nullValue()
//    }
//  }
//
//  override fun read(`in`: JsonReader): MessageSendWithMassRequestUser? {
//    // 不需要实现
//    return null
//  }
//
//}


class OpAccessTokenResponseTypeAdapter : TypeAdapter<OpAccessTokenResponse>() {

  private val logger = KotlinLogging.logger { }

  override fun write(out: JsonWriter, value: OpAccessTokenResponse) {
    // 不需要实现
  }

  override fun read(`in`: JsonReader): OpAccessTokenResponse = OpAccessTokenResponse().apply {
    `in`.beginObject()
    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_ACCESS_TOKEN_KEY -> accessToken = `in`.nextString()
        WEIXIN_ACCESS_TOKEN_EXPIRES_IN_KEY -> expiresIn = `in`.nextLong()
        else -> if (`in`.hasNext()) {
          logger.warn { "未知的json键值： [{$key}: {${`in`.nextString()}}]" }
        }
      }
    }
    `in`.endObject()
  }

}


class OpGetLicenseInformationResponseTypeAdapter : TypeAdapter<OpGetLicenseInformationResponse>() {

  private val logger = KotlinLogging.logger { }

  override fun write(out: JsonWriter, value: OpGetLicenseInformationResponse) {
    // 不需要实现
  }

  override fun read(`in`: JsonReader): OpGetLicenseInformationResponse = OpGetLicenseInformationResponse().apply {
    `in`.beginObject()
    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_LICENSING_INFORMATION_KEY -> readLicensingInformation(`in`, this)
        OPEN_PLATFORM_AUTH_LICENSOR_INFORMATION_KEY -> readLicensorInformation(`in`, this)
        OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY -> licensedAccessToken.accessToken = `in`.nextString()
        OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY -> licensedAccessToken.expiresIn = `in`.nextLong()
        OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY -> licensedAccessToken.refreshToken = `in`.nextString()
        else -> if (`in`.hasNext()) {
          logger.warn { "未知的json键值： [{$key}: {${`in`.nextString()}}]" }
        }
      }
    }
    `in`.endObject()
  }

  private fun readLicensingInformation(`in`: JsonReader, model: OpGetLicenseInformationResponse) {
    model.licensingInformation = OpGetLicenseInformationResponse.LicensingInformation().apply {
      `in`.beginObject()

      while (`in`.hasNext()) {
        val key = `in`.nextName()
        when (key) {
          OPEN_PLATFORM_AUTH_LICENSED_APPID_ALIAS_KEY, OPEN_PLATFORM_AUTH_LICENSED_APPID_KEY ->
            appId = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY ->
            model.licensedAccessToken.accessToken = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY ->
            model.licensedAccessToken.expiresIn = `in`.nextLong()
          OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY ->
            model.licensedAccessToken.refreshToken = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSED_FUNCTIONS_INFO_KEY -> {
            `in`.beginArray()

            while (`in`.hasNext()) {
              `in`.beginObject()
              `in`.nextName()
              `in`.beginObject()
              `in`.nextName()
              functionIds.add(`in`.nextInt())
              `in`.endObject()
              `in`.endObject()
            }

            `in`.endArray()
          }
          else -> if (`in`.hasNext()) {
            logger.warn { "未知的json键值： [{$key}: {${`in`.nextString()}}]" }
          }
        }
      }

      `in`.endObject()
    }
  }

  private fun readLicensorInformation(`in`: JsonReader, model: OpGetLicenseInformationResponse) {
    model.licensorInformation = OpGetLicenseInformationResponse.LicensorInformation().apply {
      `in`.beginObject()

      while (`in`.hasNext()) {
        val key = `in`.nextName()
        when (key) {
          OPEN_PLATFORM_AUTH_LICENSOR_NICKNAME_KEY -> nickName = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSOR_LOGO_KEY -> logo = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_TYPE_KEY -> {
            `in`.beginObject()
            `in`.nextName()
            accountType = MpType.with(`in`.nextInt())
            `in`.endObject()
          }
          OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_VERIFIED_TYPE_KEY -> {
            `in`.beginObject()
            `in`.nextName()
            accountVerifiedType = OpMpAuthentication.with(`in`.nextInt())
            `in`.endObject()
          }
          OPEN_PLATFORM_AUTH_LICENSOR_MPID_KEY -> mpId = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSOR_PRINCIPAL_NAME_KEY -> principalName = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_NAME_KEY -> accountName = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSOR_QRCODE_URL_KEY -> qrCodeUrl = `in`.nextString()
          OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_INFO_KEY -> readBusinessInfo(`in`, this)
          else -> if (`in`.hasNext()) {
            logger.warn { "未知的json键值： [{$key}: {${`in`.nextString()}}]" }
          }
        }
      }

      `in`.endObject()
    }
  }

  private fun readBusinessInfo(`in`: JsonReader, information: OpGetLicenseInformationResponse.LicensorInformation) {
    `in`.beginObject()
    while (`in`.hasNext()) {
      val key = `in`.nextName()
      when (key) {
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_STORE_KEY ->
          information.isBusinessCardOpened = `in`.nextInt() == GLOBAL_TRUE_NUMBER
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SCAN_KEY ->
          information.isBusinessCardOpened = `in`.nextInt() == GLOBAL_TRUE_NUMBER
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_PAY_KEY ->
          information.isBusinessCardOpened = `in`.nextInt() == GLOBAL_TRUE_NUMBER
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_CARD_KEY ->
          information.isBusinessCardOpened = `in`.nextInt() == GLOBAL_TRUE_NUMBER
        OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SHAKE_KEY ->
          information.isBusinessCardOpened = `in`.nextInt() == GLOBAL_TRUE_NUMBER
        else -> if (`in`.hasNext()) {
          logger.warn { "未知的json键值： [{$key}: {${`in`.nextString()}}]" }
        }
      }
    }
    `in`.endObject()
  }

}