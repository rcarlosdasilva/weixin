package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.core.ApiRequestException
import io.github.rcarlosdasilva.weixin.core.ExecuteException
import io.github.rcarlosdasilva.weixin.core.Weixin
import io.github.rcarlosdasilva.weixin.handler.CacheHandler
import io.github.rcarlosdasilva.weixin.handler.GeneralCacheableObject
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.QrCodeCreateResponse
import io.github.rcarlosdasilva.weixin.model.response.ServerIpsResponse
import io.github.rcarlosdasilva.weixin.model.response.ShortUrlResponse
import io.github.rcarlosdasilva.weixin.terms.WEIXIN_IP_CACHE_KEY
import io.github.rcarlosdasilva.weixin.terms.data.QrCodeAction
import mu.KotlinLogging

/**
 * 公众号公共API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpCommonality(private val account: Mp) : Api(account) {

  private val logger = KotlinLogging.logger { }

  /**
   * 获取微信服务器IP地址.
   *
   * 如果公众号基于消息接收安全上的考虑，需要获知微信服务器的IP地址列表，以便识别出哪些消息是微信官方推送给你的，哪些消息可能是他人伪造的，可以通过该接口获得微信服务器IP地址列表。
   *
   * @return IP地址列表
   */
  fun getWeixinIps(): List<String> =
    get(ServerIpsResponse::class.java, ServerIpsRequest())?.ipList?.also {
      logger.info { "获取到新的微信服务器IP地址列表（size=${it.size}）：" }
      it.forEach { logger.info { "  weixin ip: $it" } }
    } ?: throw ApiRequestException("getWeixinIps - 无法获取微信服务器ip")

  /**
   * 判断ip是否是可信任的微信ip.
   *
   * @param ip ip地址
   * @return 是否合法
   */
  @Suppress("UNCHECKED_CAST")
  fun isLegalRequestIp(ip: String): Boolean {
    val cacheableObject = CacheHandler.of(GeneralCacheableObject::class.java).get(WEIXIN_IP_CACHE_KEY)
    val ips: List<String> = if (cacheableObject != null) {
      cacheableObject.obj as List<String>
    } else {
      getWeixinIps().also {
        CacheHandler.of(GeneralCacheableObject::class.java).put(WEIXIN_IP_CACHE_KEY, GeneralCacheableObject(it))
      }
    }
    return ips.contains(ip)
  }

  /**
   * 将一条长链接转成短链接.
   *
   * 主要使用场景：
   * 开发者用于生成二维码的原链接（商品、支付二维码等）太长导致扫码速度和成功率下降，
   * 将原长链接通过此接口转成短链接再生成二维码将大大提升扫码速度和成功率。
   *
   * @param url 需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
   * @return 短链接
   */
  fun getShortUrl(url: String): String =
    post(ShortUrlResponse::class.java, ShortUrlRequest(url))?.shortUrl
        ?: throw ApiRequestException("getWeixinIps - 无法获取微信服务器ip")

  private fun requestCreateQr(requestModel: QrCodeCreateRequest): QrCodeCreateResponse {
    return post(QrCodeCreateResponse::class.java, requestModel)
        ?: throw ApiRequestException("getWeixinIps - 无法获取微信服务器ip")
  }

  /**
   * 生成带参数的临时二维码.
   *
   * 临时二维码，是有过期时间的，最长可以设置为在二维码生成后的30天（即2592000秒）后过期，但能够生成较多数量。
   * 临时二维码主要用于帐号绑定等不要求二维码永久保存的业务场景
   *
   * @param expireSeconds 二维码过期时间，单位秒。最大不超过2592000（即30天），默认30秒
   * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return [QrCodeCreateResponse] 包含ticket和url两个有效值
   */
  fun createTemporaryQr(expireSeconds: Long, sceneId: Int): QrCodeCreateResponse =
    requestCreateQr(QrCodeCreateRequest(expireSeconds, QrCodeAction.TEMPORARY_WITH_ID.toString()).apply {
      info["scene"] = mapOf("scene_id" to sceneId)
    })

  /**
   * 同[createTemporaryQr]
   */
  fun createTemporaryQr(expireSeconds: Long, sceneString: String): QrCodeCreateResponse =
    requestCreateQr(QrCodeCreateRequest(expireSeconds, QrCodeAction.TEMPORARY_WITH_STRING.toString()).apply {
      info["scene"] = mapOf("scene_str" to sceneString)
    })

  /**
   * 生成带参数的永久二维码.
   *
   * 永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景。
   *
   * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return [QrCodeCreateResponse] 包含ticket和url两个有效值
   */
  fun createUnlimitedQr(sceneId: Int): QrCodeCreateResponse =
    requestCreateQr(QrCodeCreateRequest(null, QrCodeAction.UNLIMITED_WITH_ID.toString()).apply {
      info["scene"] = mapOf("scene_id" to sceneId)
    })

  /**
   * 同[createUnlimitedQr]
   */
  fun createUnlimitedQr(sceneString: String): QrCodeCreateResponse =
    requestCreateQr(QrCodeCreateRequest(null, QrCodeAction.UNLIMITED_WITH_STRING.toString()).apply {
      info["scene"] = mapOf("scene_str" to sceneString)
    })

  /**
   * 通过创建二维码结果获取图片.
   *
   * @param ticket 带参数的二维码创建结果ticket
   * @return 图片文件流
   */
  fun downloadQrImage(ticket: String): ByteArray = getStream(QrCodeDownloadRequest(ticket))?.let {
    readStream(it)
  } ?: throw ApiRequestException("getWeixinIps - 无法获取微信服务器ip")

  /**
   * 直接获取带参数的临时二维码.
   *
   * 临时二维码，是有过期时间的，最长可以设置为在二维码生成后的30天（即2592000秒）后过期，但能够生成较多数量。
   * 临时二维码主要用于帐号绑定等不要求二维码永久保存的业务场景
   *
   * @param expireSeconds 二维码过期时间，单位秒。最大不超过2592000（即30天），默认30秒
   * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return 图片文件流
   */
  fun downloadTemporaryQrImage(expireSeconds: Long, sceneId: Int): ByteArray =
    createTemporaryQr(expireSeconds, sceneId).let {
      downloadQrImage(it.ticket!!)
    }

  /**
   * 同[downloadTemporaryQrImage]
   */
  fun downloadTemporaryQrImage(expireSeconds: Long, sceneString: String): ByteArray =
    createTemporaryQr(expireSeconds, sceneString).let {
      downloadQrImage(it.ticket!!)
    }

  /**
   * 直接获取带参数的永久二维码.
   *
   * 永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景。
   *
   * @param senceId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return 图片文件流
   */
  fun downloadUnlimitedQrImage(senceId: Int): ByteArray =
    createUnlimitedQr(senceId).let {
      downloadQrImage(it.ticket!!)
    }

  /**
   * 同[downloadUnlimitedQrImage]
   */
  fun downloadUnlimitedQrImage(sceneString: String): ByteArray =
    createUnlimitedQr(sceneString).let {
      downloadQrImage(it.ticket!!)
    }

  /**
   * 公众号调用接口调用次数清零API.
   *
   * 请注意：
   * - 每个公众号每个月有10次清零机会，包括在微信公众平台上的清零以及调用API进行清零
   * - 第三方代公众号调用，实际上消耗的是公众号的清零quota
   *
   * @return 如果是超出清零的请求次数限制返回false
   */
  fun resetQuota(): Boolean = try {
    logger.warn { "正在尝试将微信接口调用次数清零" }
    post(Boolean::class.java, ResetQuotaRequest(account.appId))!!
  } catch (ex: ExecuteException) {
    logger.error { "微信接口调用次数清零失败" }
    false
  }

  /**
   * 判断当前公众号配置是否可用，主要通过请求一下access_token来验证公众号信息是否正确.
   *
   * @return 是否可用
   */
  fun isUsable(): Boolean = try {
    Weixin.mp(account.key).authentication.askAccessToken().isNotBlank()
  } catch (ex: Exception) {
    false
  }

}