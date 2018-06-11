package io.github.rcarlosdasilva.weixin.model.notification

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamConverter
import com.thoughtworks.xstream.annotations.XStreamOmitField
import io.github.rcarlosdasilva.weixin.terms.data.MessageType
import mu.KotlinLogging

interface NotificationResponse

/**
 * 加密的通知模型
 *
 * @param ciphertext
 * 加密后的公众号待回复用户的消息
 * @param signature
 * 签名串
 * @param timestamp
 * 时间戳，可以自己生成，也可以用URL参数的timestamp
 * @param nonce
 * 随机串，可以自己生成，也可以用URL参数的nonce
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@XStreamAlias("xml")
class EncryptedNotificationResponse(
    @field:XStreamAlias("Encrypt") val ciphertext: String,
    @field:XStreamAlias("MsgSignature") val signature: String,
    @field:XStreamAlias("TimeStamp") val timestamp: Long,
    @field:XStreamAlias("Nonce") val nonce: String
) : NotificationResponse


/**
 * 微信推送通知后，被动回复用户消息，明文模式
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@XStreamAlias("xml")
@XStreamConverter(ResponseConverter::class)
class PlaintextNotificationResponse : NotificationResponse {

  private val logger = KotlinLogging.logger {}

  @XStreamAlias("ToUserName")
  var toUser: String? = null
  @XStreamAlias("FromUserName")
  var fromUser: String? = null
  @XStreamAlias("CreateTime")
  var time: Long = 0
  /**
   * 不能为 [MessageType.NEWS_INTERNAL] 和 [MessageType.CARD]
   */
  @XStreamAlias("MsgType")
  @XStreamConverter(EnumStringConverter::class)
  var type: MessageType = MessageType.TEXT
    set(type) {
      if (type == MessageType.NEWS_INTERNAL || type == MessageType.CARD) {
        logger.warn { "响应微信通知，返回类型不支持MessageType.NEWS_INTERNAL和MessageType.CARD" }
      } else {
        field = type
      }
    }
  @XStreamOmitField
  val info = ResponseAdditionalInfo()
  @XStreamOmitField
  val infos = mutableListOf<ResponseAdditionalInfo>()

}
