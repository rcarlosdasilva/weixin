package io.github.rcarlosdasilva.weixin.core

import com.google.common.base.Strings
import io.github.rcarlosdasilva.weixin.handler.CacheHandler
import io.github.rcarlosdasilva.weixin.handler.Encryptor
import io.github.rcarlosdasilva.weixin.handler.GeneralCacheableObject
import io.github.rcarlosdasilva.weixin.handler.NotificationParser
import io.github.rcarlosdasilva.weixin.model.AccessToken
import io.github.rcarlosdasilva.weixin.model.Account
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.builder.NotificationResponseBuilder
import io.github.rcarlosdasilva.weixin.model.notification.*
import io.github.rcarlosdasilva.weixin.model.response.OpGetLicenseInformationResponse
import io.github.rcarlosdasilva.weixin.terms.DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET
import io.github.rcarlosdasilva.weixin.terms.WEIXIN_NOTIFICATION_RESPONSE_NOTHING
import io.github.rcarlosdasilva.weixin.terms.data.EncryptionType
import io.github.rcarlosdasilva.weixin.terms.data.NotificationEvent
import io.github.rcarlosdasilva.weixin.terms.data.NotificationEvent.*
import io.github.rcarlosdasilva.weixin.terms.data.NotificationMessage
import io.github.rcarlosdasilva.weixin.terms.data.NotificationMessage.*
import io.github.rcarlosdasilva.weixin.terms.data.NotificationOpInfo
import io.github.rcarlosdasilva.weixin.terms.data.NotificationOpInfo.*
import mu.KotlinLogging
import java.util.*

/**
 * 微信推送通知代理类.
 *
 * 微信的加密解密使用AES，默认会引起JRE的一个java.security.InvalidKeyException: Illegal key
 * size异常，请参考[Encryptor]解决
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class NotificationHandlerProxy private constructor(private val handler: NotificationHandler) {

  private val logger = KotlinLogging.logger {}

  /**
   * 处理微信推送来的内容，（可处理开放平台、及公众号平台下的安全模式、加密模式）
   *
   * 公众号推送的xml中使用的是toUserName，开放平台推送的xml中使用的是appid
   *
   * @param appId （开放平台下）授权方公众号appid
   * @param content 推送内容，对应POST请求的数据
   * @param signature 签名串，公众号平台对应URL参数的signature，开放平台的消息签名，对应msg_signature参数
   * @param timestamp 时间戳，对应URL参数的timestamp
   * @param nonce 随机串，对应URL参数的nonce
   * @return 回复微信的内容
   */
  @JvmOverloads
  fun process(appId: String? = null, content: String, signature: String, timestamp: Long, nonce: String): String? {
    logger.debug("正在处理微信通知: at {} --- {} || 签名[{}] || 随机码[{}]", timestamp, content, signature, nonce)

    val originalNotification = NotificationParser.parse(content)

    // 开放平台下授权过程中的公众号appid
    val authorizingAppId = originalNotification.appId
    // 开放平台下，普通消息或事件消息的公众号appid，或公众号平台下公众号的mpid
    val normalAppId = appId ?: originalNotification.toUser
    val recipient = if (Strings.isNullOrEmpty(authorizingAppId))
      normalAppId
    else
      authorizingAppId

    if (recipient == null || recipient.isBlank()) {
      logger.warn("获取不到appid或tousername：{}", content)
      return null
    }

    val decryptedNotification = decryptNotification(originalNotification, recipient, signature, timestamp, nonce)
        ?: return null

    // TODO 校验 decryptedNotification

    val messageType = decryptedNotification.messageType
    val infoType = decryptedNotification.infoType

    val builder = NotificationResponseBuilder.with(decryptedNotification)
    if (messageType != null) {
      if (messageType === NotificationMessage.EVENT) {
        logger.debug("开始处理事件推送..")
        processEvent(builder, decryptedNotification)
      } else {
        logger.debug("开始处理消息推送..")
        processMessage(builder, decryptedNotification)
      }
    }
    if (infoType != null) {
      processInfo(infoType, builder, decryptedNotification)
    }

    return encryptNotification(builder, decryptedNotification.account)
  }

  private fun decryptNotification(originalNotification: Notification, recipient: String, signature: String,
                                  timestamp: Long, nonce: String): Notification? {
    val openPlatform = Weixin.lookup()
    val account = Weixin.lookup(recipient)
    if (openPlatform == null && account == null) {
      logger.warn("没有配置开放平台，并且找不到对应的公众号配置(Account): {}", recipient)
      return null
    }

    var decryptedNotification: Notification?

    // openPlatform 和 account 中至少有一个不为null
    if (openPlatform != null && account == null) {
      logger.debug("未找到对应的公众号配置(Account): {}，这是一个新的公众号，正在使用开放平台配置解密..", recipient)
      decryptedNotification = Encryptor.decrypt(openPlatform.aesToken, openPlatform.aesKey,
          originalNotification.ciphertext!!, signature, timestamp, nonce)
    } else {
      if (account!!.proxyWithOp) {
        if (openPlatform == null) {
          logger.warn("没有配置开放平台，但公众号配置(Account): {} 为开放平台授权", recipient)
          return null
        }

        logger.debug("正在使用开放平台配置对公众号(Account): {} 解密..", recipient)
        decryptedNotification = Encryptor.decrypt(openPlatform.aesToken, openPlatform.aesKey,
            originalNotification.ciphertext!!, signature, timestamp, nonce)
      } else {
        if (account.encryptionType != EncryptionType.PLAIN_TEXT) {
          logger.debug("正在使用公众号配置(Account): {} 解密..", recipient)
          decryptedNotification = Encryptor.decrypt(account.aesToken!!, account.aesKey!!,
              originalNotification.ciphertext!!, signature, timestamp, nonce)
        } else {
          logger.debug("检测到公众号配置(Account): {} 为明文模式，不需要解密", recipient)
          return originalNotification
        }
      }
    }

    if (decryptedNotification == null) {
      logger.warn("无法解密，或解密失败: {}", recipient)
      return null
    }
    logger.debug("解密成功：{}", recipient)

    return decryptedNotification.apply { this.account = account }
  }

  private fun encryptNotification(builder: NotificationResponseBuilder, account: Mp?): String? {
    // 生成明文响应内容
    val plaintextResponse = builder.build()
    logger.debug("处理完毕，已生成返回数据")

    if (plaintextResponse == null) {
      return WEIXIN_NOTIFICATION_RESPONSE_NOTHING
    } else {
      val openPlatform = Weixin.lookup()

      // Bean转XML
      var reply = NotificationParser.toXml(plaintextResponse)
      var encryptedResponse: EncryptedNotificationResponse?

      // 如果account==null，说明消息是开放平台的消息，或者是公众号使用了开放平台。只要是开放平台相关，就一定要加密
      if (account == null || account.proxyWithOp) {
        openPlatform ?: throw ExecuteException("不应该出现的错误，OP账号信息为null")

        logger.debug("正在使用开放平台配置加密..")
        encryptedResponse = Encryptor.encrypt(openPlatform.appId, openPlatform.aesToken, openPlatform.aesKey, reply)

        encryptedResponse ?: let {
          logger.error("无法使用开放平台加密：{}", account)
          return null
        }

        reply = NotificationParser.toXml(encryptedResponse)
        logger.debug("加密成功")
      } else {
        if (account.encryptionType != EncryptionType.PLAIN_TEXT) {
          logger.debug("正在使用公众号配置(Account): {} 加密..", account.appId)
          encryptedResponse = Encryptor.encrypt(account.appId, account.aesToken!!, account.aesKey!!, reply)

          encryptedResponse ?: let {
            logger.error("无法使用公众号aes加密：{}", account)
            return null
          }

          reply = NotificationParser.toXml(encryptedResponse)
          logger.debug("加密成功")
        }
      }
      return reply
    }
  }

  /**
   * 处理事件类通知.
   */
  private fun processEvent(builder: NotificationResponseBuilder, notification: Notification) {
    val event = notification.event
    val type = event!!.type

    when (type) {
      CLICK -> handler.doEventOfMenuForClick(builder, notification, event.key!!)
      VIEW -> handler.doEventOfMenuForView(builder, notification, event.key!!, event.menuId!!)
      SCAN_QR_PUSH -> handler.doEventOfMenuForScanQrPush(builder, notification, event.key!!, event.scanCodeInfo!!)
      SCAN_QR_WAIT_MSG -> handler.doEventOfMenuForScanQrWait(builder, notification, event.key!!, event.scanCodeInfo!!)
      PIC_PHOTO -> handler.doEventOfMenuForPicPhoto(builder, notification, event.key!!, event.picsInfo!!)
      PIC_PHOTO_OR_ALBUM -> handler.doEventOfMenuForPicPhotoOrAlbum(builder, notification, event.key!!, event.picsInfo!!)
      PIC_WX_ALBUM -> handler.doEventOfMenuForPicWxAlbum(builder, notification, event.key!!, event.picsInfo!!)
      NotificationEvent.LOCATION -> handler.doEventOfMenuForLocation(builder, notification, event.key!!, event.locationInfo!!)
      MASS_SEND_FINISH -> handler.doEventOfMessageForMass(builder, notification, event.messageId!!, event.status!!,
          event.totalCount, event.filterCount, event.sentCount, event.errorCount)
      TEMPLATE_SEND_FINISH -> handler.doEventOfMessageForTemplate(builder, notification, event.messageId!!, event.status!!)
      SUBSCRIBE -> handler.doEventOfCommonForSubscribe(builder, notification, event.key!!, event.ticket!!)
      UNSUBSCRIBE -> handler.doEventOfCommonForUnsubscribe(builder, notification)
      SCAN -> handler.doEventOfCommonForScanQr(builder, notification, event.key!!, event.ticket!!)
      REPORT_LOCATION -> handler.doEventOfCommonForLocation(builder, notification, event.latitude!!, event.longitude!!,
          event.precision!!)
      VERIFY_QUALIFICATION_SUCCESS -> handler.doEventOfVerifyForQualificationSuccess(builder, notification, event.expiredTime)
      VERIFY_QUALIFICATION_FAIL -> handler.doEventOfVerifyForQualificationFail(builder, notification, event.failTime,
          event.failReason!!)
      VERIFY_NAMING_SUCCESS -> handler.doEventOfVerifyForNamingSuccess(builder, notification, event.expiredTime)
      VERIFY_NAMING_FAIL -> handler.doEventOfVerifyForNamingFail(builder, notification, event.failTime, event.failReason!!)
      VERIFY_ANNUAL -> handler.doEventOfVerifyForAnnual(builder, notification, event.expiredTime)
      VERIFY_EXPIRED -> handler.doEventOfVerifyForExpired(builder, notification, event.expiredTime)
      CARD_PAID -> TODO()
      UNKNOWN -> TODO()
      null -> TODO()
    }
  }

  /**
   * 处理消息类通知.
   */
  private fun processMessage(builder: NotificationResponseBuilder, notification: Notification) {
    val type = notification.messageType
    val message = notification.message!!

    when (type) {
      TEXT -> handler.doMessageForText(builder, notification, message.messageId!!, message.content!!)
      IMAGE -> handler.doMessageForImage(builder, notification, message.messageId!!, message.mediaId!!, message.picUrl!!)
      VOICE -> handler.doMessageForVoice(builder, notification, message.messageId!!, message.mediaId!!, message.format!!,
          message.recognition!!)
      VIDEO -> handler.doMessageForVideo(builder, notification, message.messageId!!, message.mediaId!!, message.mediaThumbId!!)
      SHORT_VIDEO -> handler.doMessageForShortVideo(builder, notification, message.messageId!!, message.mediaId!!,
          message.mediaThumbId!!)
      NotificationMessage.LOCATION -> handler.doMessageForLocation(builder, notification, message.messageId!!,
          message.locationX!!, message.locationY!!, message.scale!!, message.address!!)
      LINK -> handler.doMessageForLink(builder, notification, message.messageId!!, message.title!!, message.description!!,
          message.url!!)
      NotificationMessage.EVENT -> let {}// do nothing
      null -> TODO()
    }
  }

  private fun processInfo(infoType: NotificationOpInfo, builder: NotificationResponseBuilder,
                          notification: Notification) {
    notification.opInfo!!.apply {
      when (infoType) {
        VERIFY_TICKET -> processInfoWithVerifyTicket(this, builder, notification)
        AUTHORIZE_SUCCEEDED -> processInfoWhenSuccessed(this, builder, notification)
        AUTHORIZE_CANCELED -> processInfoWhenCanceled(this, builder, notification)
        AUTHORIZE_UPDATED -> processInfoWhenUpdated(this, builder, notification)
      }
    }
  }

  private fun processInfoWithVerifyTicket(info: OpInfo, builder: NotificationResponseBuilder,
                                          notification: Notification) {
    info.ticket ?: let {
      logger.warn("无法从微信通知消息中获取到开放平台发放的Ticket")
      return
    }

    CacheHandler.of(GeneralCacheableObject::class.java)
        .put(DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET, GeneralCacheableObject(info.ticket))
    handler.doInfoOfComponentVerifyTicket(builder, notification, info.ticket)
  }

  private fun processInfoWhenSuccessed(info: OpInfo, builder: NotificationResponseBuilder,
                                       notification: Notification) {
    val autoLoad = Weixin.registry.setting.autoLoadAuthorizedWeixinData

    var licensingInformation: OpGetLicenseInformationResponse.LicensingInformation? = null
    var licensorInformation: OpGetLicenseInformationResponse.LicensorInformation? = null
    var accessToken: AccessToken? = null

    if (autoLoad) {
      val licensingInformationResponse = fetchLicensingInformation(info.license!!)
      val licensorInformationResponse = fetchLicensorInformation(info.licensorAppId!!)

      accessToken = licensingInformationResponse.licensedAccessToken
      licensingInformation = licensingInformationResponse.licensingInformation
      licensorInformation = licensorInformationResponse.licensorInformation
    }

    val account = handler.doInfoOfAuthorizeSucceeded(builder, notification, info.licensorAppId!!, info.license!!,
        info.licenseExpireAt, accessToken, licensingInformation, licensorInformation)
    if (account != null) {
      Weixin.registry.checkin(account as Mp)
      if (accessToken != null) {
        accessToken.accountKey = account.key
        CacheHandler.of(AccessToken::class.java).put(account.key, accessToken)
      }
    }
  }

  private fun processInfoWhenCanceled(info: OpInfo, builder: NotificationResponseBuilder,
                                      notification: Notification) {
    val licensorAppId = info.licensorAppId ?: let {
      logger.error("无法从开放平台返回的信息中获取到开放平台授权者appid")
      return
    }

    Weixin.registry.remove(licensorAppId)
    handler.doInfoOfAuthorizeCanceled(builder, notification, info.licensorAppId)
  }

  private fun processInfoWhenUpdated(info: OpInfo, builder: NotificationResponseBuilder,
                                     notification: Notification) {
    val autoLoad = Weixin.registry.setting.autoLoadAuthorizedWeixinData

    var licensingInformation: OpGetLicenseInformationResponse.LicensingInformation? = null
    var licensorInfromation: OpGetLicenseInformationResponse.LicensorInformation? = null
    var accessToken: AccessToken? = null

    if (autoLoad) {
      val licensingInformationResponse = fetchLicensingInformation(info.license!!)
      val licensorInformationResponse = fetchLicensorInformation(info.licensorAppId!!)

      accessToken = licensingInformationResponse.licensedAccessToken
      licensingInformation = licensingInformationResponse.licensingInformation
      licensorInfromation = licensorInformationResponse.licensorInformation
    }

    val account = handler.doInfoOfAuthorizeUpdated(builder, notification, info.licensorAppId!!, info.license!!,
        info.licenseExpireAt, accessToken, licensingInformation, licensorInfromation)

    if (account != null) {
      Weixin.registry.checkin(account as Mp)
      if (accessToken != null) {
        accessToken.accountKey = account.key
        CacheHandler.of(AccessToken::class.java).put(account.key, accessToken)
      }
    }
  }

  /**
   * 自动换取公众号或小程序的接口调用凭据和授权信息
   *
   * @param license 授权码
   */
  private fun fetchLicensingInformation(license: String): OpGetLicenseInformationResponse {
    val response = Weixin.op().authentication.getLicensingInformation(license)

    response.licensingInformation ?: throw  ExecuteException("无法获取到开放平台授权方信息")

    return response
  }

  /**
   * 自动获取授权方的帐号基本信息
   */
  private fun fetchLicensorInformation(licensorAppId: String): OpGetLicenseInformationResponse {
    val response = Weixin.op().authentication.getLicensorInformation(licensorAppId)

    response.licensorInformation ?: throw ExecuteException("无法获取到开放平台授权方的基本信息")

    return response
  }

  companion object {
    lateinit var instance: NotificationHandlerProxy

    /**
     * 设置微信推送处理器，并生成代理.
     *
     * @param handler [NotificationHandler]
     */
    @JvmStatic
    fun proxy(handler: NotificationHandler) {
      instance = NotificationHandlerProxy(handler)
    }
  }

}


// ====================================================================================


/**
 * 统一处理器接口，自动根据微信推送通知内容，执行响应代码.
 *
 * 实现该接口，使用 [NotificationHandlerProxy.proxy] 生成一个代理proxy，
 * 当微信推送内容到服务器时，使用 `proxy#process()` 处理接收到的数据，[NotificationHandler] 接口的实现是真正处理通知消息的地方
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface NotificationHandler {

  /**
   * 接收普通消息，文本消息.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id，64位整型
   * @param content 文本消息内容
   */
  fun doMessageForText(builder: NotificationResponseBuilder, notification: Notification, messageId: Long, content: String)

  /**
   * 接收普通消息，图片消息.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id，64位整型
   * @param mediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param picUrl 图片链接（由系统生成）
   */
  fun doMessageForImage(builder: NotificationResponseBuilder, notification: Notification, messageId: Long,
                        mediaId: String, picUrl: String)

  /**
   * 接收普通消息，语音消息.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id，64位整型
   * @param mediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param format 语音格式，如amr，speex等
   * @param recognition 语音识别结果，UTF8编码。（开通语音识别后有值，具体查看微信文档）
   */
  fun doMessageForVoice(builder: NotificationResponseBuilder, notification: Notification,
                        messageId: Long, mediaId: String, format: String, recognition: String)

  /**
   * 接收普通消息，视频消息.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id，64位整型
   * @param mediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param mediaThumbId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
   */
  fun doMessageForVideo(builder: NotificationResponseBuilder, notification: Notification, messageId: Long,
                        mediaId: String, mediaThumbId: String)

  /**
   * 接收普通消息，小视频消息.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id，64位整型
   * @param mediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param mediaThumbId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
   */
  fun doMessageForShortVideo(builder: NotificationResponseBuilder, notification: Notification, messageId: Long,
                             mediaId: String, mediaThumbId: String)

  /**
   * 接收普通消息，地理位置消息.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id，64位整型
   * @param locationX 地理位置维度
   * @param locationY 地理位置经度
   * @param scale 地图缩放大小
   * @param address 地理位置信息
   */
  fun doMessageForLocation(builder: NotificationResponseBuilder, notification: Notification, messageId: Long,
                           locationX: Double, locationY: Double, scale: Int, address: String)

  /**
   * 接收普通消息，链接消息.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id，64位整型
   * @param title 消息标题
   * @param description 消息描述
   * @param url 消息链接
   */
  fun doMessageForLink(builder: NotificationResponseBuilder, notification: Notification, messageId: Long,
                       title: String, description: String, url: String)

  /**
   * 自定义菜单事件推送，点击菜单拉取消息时的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，与自定义菜单接口中KEY值对应
   */
  fun doEventOfMenuForClick(builder: NotificationResponseBuilder, notification: Notification, key: String)

  /**
   * 自定义菜单事件推送，点击菜单跳转链接时的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，设置的跳转URL
   * @param menuId 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。
   */
  fun doEventOfMenuForView(builder: NotificationResponseBuilder, notification: Notification, key: String, menuId: String)

  /**
   * 自定义菜单事件推送，扫码推事件的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，由开发者在创建菜单时设定
   * @param scanCodeInfo 扫描信息, see [ScanCodeInfo]
   */
  fun doEventOfMenuForScanQrPush(builder: NotificationResponseBuilder, notification: Notification, key: String,
                                 scanCodeInfo: ScanCodeInfo)

  /**
   * 自定义菜单事件推送，扫码推事件且弹出“消息接收中”提示框的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，由开发者在创建菜单时设定
   * @param scanCodeInfo 扫描信息, see [ScanCodeInfo]
   */
  fun doEventOfMenuForScanQrWait(builder: NotificationResponseBuilder, notification: Notification, key: String,
                                 scanCodeInfo: ScanCodeInfo)

  /**
   * 自定义菜单事件推送，弹出系统拍照发图的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，由开发者在创建菜单时设定
   * @param picsInfo 发送的图片信息, see [PicsInfo]
   */
  fun doEventOfMenuForPicPhoto(builder: NotificationResponseBuilder, notification: Notification, key: String,
                               picsInfo: PicsInfo)

  /**
   * 自定义菜单事件推送，弹出拍照或者相册发图的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，由开发者在创建菜单时设定
   * @param picsInfo 发送的图片信息, see [PicsInfo]
   */
  fun doEventOfMenuForPicPhotoOrAlbum(builder: NotificationResponseBuilder, notification: Notification, key: String,
                                      picsInfo: PicsInfo)

  /**
   * 自定义菜单事件推送，弹出微信相册发图器的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，由开发者在创建菜单时设定
   * @param picsInfo 发送的图片信息, see [PicsInfo]
   */
  fun doEventOfMenuForPicWxAlbum(builder: NotificationResponseBuilder, notification: Notification, key: String,
                                 picsInfo: PicsInfo)

  /**
   * 自定义菜单事件推送，弹出地理位置选择器的事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，由开发者在创建菜单时设定
   * @param locationInfo 发送的位置信息, see [LocationInfo]
   */
  fun doEventOfMenuForLocation(builder: NotificationResponseBuilder, notification: Notification, key: String,
                               locationInfo: LocationInfo)

  /**
   * 高级群发接口，事件推送群发结果.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 群发的消息ID
   * @param status 群发的结构，为“send success”或“send fail”或“err(num)”。但send = success时，
   * 也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。
   * err(num)是审核失败的具体原因，可能的情况如下：
   * - err(10001) 涉嫌广告
   * - err(20001) 涉嫌政治
   * - err(20004) 涉嫌社会
   * - err(20002) 涉嫌色情
   * - err(20006) 涉嫌违法犯罪
   * - err(20008) 涉嫌欺诈
   * - err(20013) 涉嫌版权
   * - err(22000) 涉嫌互推(互相宣传)
   * - err(21000) 涉嫌其他
   * @param totalCount tag_id下粉丝数；或者openid_list中的粉丝数
   * @param filterCount 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount
   * = SentCount + ErrorCount
   * @param sentCount 发送成功的粉丝数
   * @param errorCount 发送失败的粉丝数
   */
  fun doEventOfMessageForMass(builder: NotificationResponseBuilder, notification: Notification, messageId: Long,
                              status: String, totalCount: Int, filterCount: Int, sentCount: Int, errorCount: Int)

  /**
   * 模板消息接口，事件推送.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param messageId 消息id
   * @param status 成功为success，由于用户拒收（用户设置拒绝接收公众号消息）而失败时为failed:user block，由于其他原因失败时为failed: system failed
   */
  fun doEventOfMessageForTemplate(builder: NotificationResponseBuilder, notification: Notification, messageId: Long,
                                  status: String)

  /**
   * 关注事件.
   *
   * 有两种情况：
   * 1. 用户关注
   * 2. 用户扫码关注（查看 扫描带参数二维码事件，用户未关注时，进行关注后的事件推送）
   *
   * **key和ticket**的值在第一种情况下为空
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，qrscene_为前缀，后面为二维码的参数值
   * @param ticket 二维码的ticket，可用来换取二维码图片
   */
  fun doEventOfCommonForSubscribe(builder: NotificationResponseBuilder, notification: Notification, key: String,
                                  ticket: String)

  /**
   * 取消关注事件.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   */
  fun doEventOfCommonForUnsubscribe(builder: NotificationResponseBuilder, notification: Notification)

  /**
   * 扫描带参数二维码事件，用户已关注时的事件推送.
   *
   *
   *
   * 有两种情况，1. 用户关注 2. 用户扫码关注（查看 扫描带参数二维码事件）
   * **key和ticket**的值在第一种情况下为空
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param key 事件KEY值，qrscene_为前缀，后面为二维码的参数值
   * @param ticket 二维码的ticket，可用来换取二维码图片
   */
  fun doEventOfCommonForScanQr(builder: NotificationResponseBuilder, notification: Notification, key: String,
                               ticket: String)

  /**
   * 上报地理位置事件.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param latitude 地理位置纬度
   * @param longitude 地理位置经度
   * @param precision 地理位置精度
   */
  fun doEventOfCommonForLocation(builder: NotificationResponseBuilder, notification: Notification, latitude: Double,
                                 longitude: Double, precision: Double)

  /**
   * 微信认证事件推送，资质认证成功（此时立即获得接口权限）.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param expiredTime 有效期 (整形)，指的是时间戳，将于该时间戳认证过期
   */
  fun doEventOfVerifyForQualificationSuccess(builder: NotificationResponseBuilder, notification: Notification,
                                             expiredTime: Date)

  /**
   * 微信认证事件推送，资质认证失败.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param failTime 失败发生时间 (整形)，时间戳
   * @param failReason 认证失败的原因
   */
  fun doEventOfVerifyForQualificationFail(builder: NotificationResponseBuilder, notification: Notification,
                                          failTime: Date, failReason: String)

  /**
   * 微信认证事件推送，名称认证成功（即命名成功）.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param expiredTime 有效期 (整形)，指的是时间戳，将于该时间戳认证过期
   */
  fun doEventOfVerifyForNamingSuccess(builder: NotificationResponseBuilder, notification: Notification,
                                      expiredTime: Date)

  /**
   * 微信认证事件推送，名称认证失败（这时虽然客户端不打勾，但仍有接口权限）.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param failTime 失败发生时间 (整形)，时间戳
   * @param failReason 认证失败的原因
   */
  fun doEventOfVerifyForNamingFail(builder: NotificationResponseBuilder, notification: Notification,
                                   failTime: Date, failReason: String)

  /**
   * 微信认证事件推送，年审通知.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param expiredTime 有效期 (整形)，指的是时间戳，将于该时间戳认证过期，需尽快年审
   */
  fun doEventOfVerifyForAnnual(builder: NotificationResponseBuilder, notification: Notification, expiredTime: Date)

  /**
   * 微信认证事件推送，认证过期失效通知.
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param expiredTime 有效期 (整形)，指的是时间戳，表示已于该时间戳认证过期，需要重新发起微信认证
   */
  fun doEventOfVerifyForExpired(builder: NotificationResponseBuilder, notification: Notification, expiredTime: Date)

  /**
   * 开放平台时间推送，推送component_verify_ticket协议.
   *
   *
   * 在第三方平台创建审核通过后，微信服务器会向其“授权事件接收URL”每隔10分钟定时推送component_verify_ticket
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param ticket ticket
   */
  fun doInfoOfComponentVerifyTicket(builder: NotificationResponseBuilder, notification: Notification, ticket: String)

  /**
   * 推送授权相关通知：授权成功通知.
   *
   * 当公众号对第三方平台进行授权、取消授权、更新授权后，微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
   *
   * 当Setting配置中autoLoadAuthorizedWeixinData为true时，这里会自动获取授权方的信息（accessToken、licensingInformation、licensorInfromation），并传入方法。否则这三个参数为null
   *
   * 1. 开发者应该在这里将授权的公众号信息（licensingInformation与licensorInfromation）保存到数据库**
   * 2. 需要开发者在处理完业务相关之后，返回公众号信息的Bean - [Account]**
   * 3. 公众号会自动注册到缓存，推荐使用授权方的appid做key（实际key由开发者确定），
   * 之后的操作就可以使用Weixin.with(key)**
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param appId 公众号或小程序
   * @param license 授权码，可用于换取公众号的接口调用凭据，详细见上面的说明
   * @param expireAt 授权码过期时间
   * @param accessToken 授权方的access_token及refresh_token
   * @param licensingInformation 授权信息
   * @param licensorInformation 授权方基本信息
   * @return 公众号信息，一定要包含key
   */
  fun doInfoOfAuthorizeSucceeded(builder: NotificationResponseBuilder, notification: Notification, appId: String,
                                 license: String, expireAt: Date, accessToken: AccessToken?,
                                 licensingInformation: OpGetLicenseInformationResponse.LicensingInformation?,
                                 licensorInformation: OpGetLicenseInformationResponse.LicensorInformation?): Account?

  /**
   * 推送授权相关通知：取消授权通知.
   *
   *
   * 当公众号对第三方平台进行授权、取消授权、更新授权后，微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param appId 公众号或小程序
   */
  fun doInfoOfAuthorizeCanceled(builder: NotificationResponseBuilder, notification: Notification, appId: String)

  /**
   * 推送授权相关通知：授权更新通知，参考同授权成功.
   *
   * 当公众号对第三方平台进行授权、取消授权、更新授权后，微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
   *
   * @param builder 微信推送响应构建器， see [NotificationResponseBuilder]，需使用 builder 指定回复什么信息
   * @param notification 微信推送基本信息, see [Notification]
   * @param appId 公众号或小程序
   * @param license 授权码，可用于换取公众号的接口调用凭据，详细见上面的说明
   * @param expireAt 授权码过期时间
   * @param accessToken 授权方的access_token及refresh_token
   * @param licensingInformation 授权信息
   * @param licensorInformation 授权方基本信息
   * @return 公众号信息，一定要包含key
   */
  fun doInfoOfAuthorizeUpdated(builder: NotificationResponseBuilder, notification: Notification,
                               appId: String, license: String, expireAt: Date, accessToken: AccessToken?,
                               licensingInformation: OpGetLicenseInformationResponse.LicensingInformation?,
                               licensorInformation: OpGetLicenseInformationResponse.LicensorInformation?): Account?

}

/**
 * 如果大部分的通知都不需要处理，建议继承该类
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class DefaultNotificationHandler : NotificationHandler {

  override fun doMessageForText(builder: NotificationResponseBuilder, notification: Notification,
                                messageId: Long, content: String) {
    builder.responseNothing()
  }

  override fun doMessageForImage(builder: NotificationResponseBuilder, notification: Notification,
                                 messageId: Long, mediaId: String, picUrl: String) {
    builder.responseNothing()
  }

  override fun doMessageForVoice(builder: NotificationResponseBuilder, notification: Notification,
                                 messageId: Long, mediaId: String, format: String, recognition: String) {
    builder.responseNothing()
  }

  override fun doMessageForVideo(builder: NotificationResponseBuilder, notification: Notification,
                                 messageId: Long, mediaId: String, mediaThumbId: String) {
    builder.responseNothing()
  }

  override fun doMessageForShortVideo(builder: NotificationResponseBuilder, notification: Notification,
                                      messageId: Long, mediaId: String, mediaThumbId: String) {
    builder.responseNothing()
  }

  override fun doMessageForLocation(builder: NotificationResponseBuilder, notification: Notification,
                                    messageId: Long, locationX: Double, locationY: Double, scale: Int, address: String) {
    builder.responseNothing()
  }

  override fun doMessageForLink(builder: NotificationResponseBuilder, notification: Notification,
                                messageId: Long, title: String, description: String, url: String) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForClick(builder: NotificationResponseBuilder, notification: Notification,
                                     key: String) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForView(builder: NotificationResponseBuilder, notification: Notification,
                                    key: String, menuId: String) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForScanQrPush(builder: NotificationResponseBuilder,
                                          notification: Notification, key: String, scanCodeInfo: ScanCodeInfo) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForScanQrWait(builder: NotificationResponseBuilder,
                                          notification: Notification, key: String, scanCodeInfo: ScanCodeInfo) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForPicPhoto(builder: NotificationResponseBuilder,
                                        notification: Notification, key: String, picsInfo: PicsInfo) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForPicPhotoOrAlbum(builder: NotificationResponseBuilder,
                                               notification: Notification, key: String, picsInfo: PicsInfo) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForPicWxAlbum(builder: NotificationResponseBuilder,
                                          notification: Notification, key: String, picsInfo: PicsInfo) {
    builder.responseNothing()
  }

  override fun doEventOfMenuForLocation(builder: NotificationResponseBuilder,
                                        notification: Notification, key: String, locationInfo: LocationInfo) {
    builder.responseNothing()
  }

  override fun doEventOfMessageForMass(builder: NotificationResponseBuilder,
                                       notification: Notification, messageId: Long, status: String, totalCount: Int, filterCount: Int,
                                       sentCount: Int, errorCount: Int) {
    builder.responseNothing()
  }

  override fun doEventOfMessageForTemplate(builder: NotificationResponseBuilder,
                                           notification: Notification, messageId: Long, status: String) {
    builder.responseNothing()
  }

  override fun doEventOfCommonForSubscribe(builder: NotificationResponseBuilder,
                                           notification: Notification, key: String, ticket: String) {
    builder.responseNothing()
  }

  override fun doEventOfCommonForUnsubscribe(builder: NotificationResponseBuilder,
                                             notification: Notification) {
    builder.responseNothing()
  }

  override fun doEventOfCommonForScanQr(builder: NotificationResponseBuilder,
                                        notification: Notification, key: String, ticket: String) {
    builder.responseNothing()
  }

  override fun doEventOfCommonForLocation(builder: NotificationResponseBuilder,
                                          notification: Notification, latitude: Double, longitude: Double, precision: Double) {
    builder.responseNothing()
  }

  override fun doEventOfVerifyForQualificationSuccess(builder: NotificationResponseBuilder,
                                                      notification: Notification, expiredTime: Date) {
    builder.responseNothing()
  }

  override fun doEventOfVerifyForQualificationFail(builder: NotificationResponseBuilder,
                                                   notification: Notification, failTime: Date, failReason: String) {
    builder.responseNothing()
  }

  override fun doEventOfVerifyForNamingSuccess(builder: NotificationResponseBuilder,
                                               notification: Notification, expiredTime: Date) {
    builder.responseNothing()
  }

  override fun doEventOfVerifyForNamingFail(builder: NotificationResponseBuilder,
                                            notification: Notification, failTime: Date, failReason: String) {
    builder.responseNothing()
  }

  override fun doEventOfVerifyForAnnual(builder: NotificationResponseBuilder,
                                        notification: Notification, expiredTime: Date) {
    builder.responseNothing()
  }

  override fun doEventOfVerifyForExpired(builder: NotificationResponseBuilder,
                                         notification: Notification, expiredTime: Date) {
    builder.responseNothing()
  }

  override fun doInfoOfComponentVerifyTicket(builder: NotificationResponseBuilder,
                                             notification: Notification, ticket: String) {
    builder.responseNothing()
  }

  override fun doInfoOfAuthorizeSucceeded(builder: NotificationResponseBuilder, notification: Notification,
                                          appId: String, license: String, expireAt: Date, accessToken: AccessToken?,
                                          licensingInformation: OpGetLicenseInformationResponse.LicensingInformation?,
                                          licensorInformation: OpGetLicenseInformationResponse.LicensorInformation?): Account? {
    builder.responseNothing()
    return null
  }

  override fun doInfoOfAuthorizeCanceled(builder: NotificationResponseBuilder, notification: Notification,
                                         appId: String) {
    builder.responseNothing()
  }

  override fun doInfoOfAuthorizeUpdated(builder: NotificationResponseBuilder, notification: Notification,
                                        appId: String, license: String, expireAt: Date, accessToken: AccessToken?,
                                        licensingInformation: OpGetLicenseInformationResponse.LicensingInformation?,
                                        licensorInformation: OpGetLicenseInformationResponse.LicensorInformation?): Account? {
    builder.responseNothing()
    return null
  }

}