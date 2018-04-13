package io.github.rcarlosdasilva.weixin.model.notification

import com.thoughtworks.xstream.annotations.XStreamAlias
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.terms.data.NotificationEvent
import io.github.rcarlosdasilva.weixin.terms.data.NotificationMessage
import io.github.rcarlosdasilva.weixin.terms.data.NotificationOpInfo
import java.util.*

/**
 * 微信推送通知模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@XStreamAlias("notification")
class Notification : NotificationMeta() {

  /**
   * xml明文原文，在安全模式下，微信验证URL时解密后有值，对应URL中的echostr
   */
  var plaintext: String? = null
  /**
   * 获取事件相关内容
   */
  var event: Event? = null
  /**
   * 获取消息相关内容
   */
  var message: Message? = null
  /**
   * 获取开放平台相关内容
   */
  var opInfo: OpInfo? = null
  /**
   * 获取推送对应的公众号配置信息
   */
  var account: Mp? = null

}


/**
 * 微信推送通知模型基础
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
open class NotificationMeta {

  /**
   * appid
   *
   * 公众号平台的通知：appid = 公众号appid<br></br>
   * 开放平台的通知：appid = 第三方appid
   */
  @XStreamAlias("AppId")
  var appId: String? = null
  /**
   * 开发者微信号 (ToUserName)
   */
  @XStreamAlias("ToUserName")
  val toUser: String? = null
  /**
   * 发送方帐号（一个OpenID）(FromUserName)
   */
  @XStreamAlias("FromUserName")
  val fromUser: String? = null
  @XStreamAlias("CreateTime")
  private val _time = 0L
  val time
    get() = Date(_time)
  @XStreamAlias("MsgType")
  private val _messageType: String? = null
  val messageType
    get() = NotificationMessage.with(_messageType)
  @XStreamAlias("InfoType")
  private val _infoType: String? = null
  val infoType
    get() = NotificationOpInfo.with(_infoType)
  /**
   * 获取密文
   */
  @XStreamAlias("Encrypt")
  val ciphertext: String? = null

}


/**
 * 开放平台通知内容
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@XStreamAlias("info")
class OpInfo {

  /**
   * Ticket内容
   */
  @XStreamAlias("ComponentVerifyTicket")
  val ticket: String? = null
  /**
   * 公众号或小程序的appid
   */
  @XStreamAlias("AuthorizerAppid")
  val licensorAppId: String? = null
  /**
   * 授权码，可用于换取公众号的接口调用凭据，详细见上面的说明
   */
  @XStreamAlias("AuthorizationCode")
  val license: String? = null
  @XStreamAlias("AuthorizationCodeExpiredTime")
  private val _licenseExpireAt = 0L
  val licenseExpireAt
    get() = Date(_licenseExpireAt)

}


/**
 * 微信通知消息中，事件相关内容
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@XStreamAlias("event")
class Event {

  @XStreamAlias("Event")
  private val _type: String? = null
  val type
    get() = NotificationEvent.with(_type)
  /**
   * 事件KEY值 (EventKey)
   *
   * 1. 自定义菜单：与自定义菜单接口中KEY值对应<br></br>
   * 2. 扫描带参数二维码，用户未关注时，进行关注后的事件推送：事件KEY值，qrscene_为前缀，后面为二维码的参数值<br></br>
   * 3. 扫描带参数二维码，用户已关注时的事件推送：是一个32位无符号整数，即创建二维码时的二维码scene_id
   */
  @XStreamAlias("EventKey")
  val key: String? = null
  /**
   * 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了 (MenuID)
   */
  @XStreamAlias("MenuId")
  val menuId: String? = null
  /**
   * 二维码的ticket，可用来换取二维码图片
   */
  @XStreamAlias("Ticket")
  val ticket: String? = null
  /**
   * 地理位置纬度
   */
  @XStreamAlias("Latitude")
  val latitude: Double? = null
  /**
   * 地理位置经度
   */
  @XStreamAlias("Longitude")
  val longitude: Double? = null
  /**
   * 地理位置精度
   */
  @XStreamAlias("Precision")
  val precision: Double? = null
  /**
   * 消息id（可能是模板消息，也可能是群发消息）
   *
   * 微信在两个地方分别给了msgId 和 msgID
   */
  @XStreamAlias("MsgId")
  val messageId: Long? = null
  /**
   * 消息id（可能是模板消息，也可能是群发消息）
   *
   * 微信在两个地方分别给了msgId 和 msgID
   */
  @XStreamAlias("MsgID")
  val messageID: Long? = null
  /**
   * 消息发送状态
   *
   * 1.模板消息时：成功为success，由于用户拒收（用户设置拒绝接收公众号消息）而失败时为failed:user
   * block，由于其他原因失败时为failed: system failed<br></br>
   * 2.群发的结构，为“send success”或“send fail”或“err(num)”。但send
   * success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，可能的情况如下：
   * err(10001), //涉嫌广告 err(20001), //涉嫌政治 err(20004), //涉嫌社会 err(20002), //涉嫌色情
   * err(20006), //涉嫌违法犯罪 err(20008), //涉嫌欺诈 err(20013), //涉嫌版权 err(22000),
   * //涉嫌互推(互相宣传) err(21000), //涉嫌其他
   */
  @XStreamAlias("Status")
  val status: String? = null
  /**
   * tag_id下粉丝数；或者openid_list中的粉丝数
   */
  @XStreamAlias("TotalCount")
  val totalCount = -1
  /**
   * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，
   * FilterCount = SentCount + ErrorCount
   */
  @XStreamAlias("FilterCount")
  val filterCount = -1
  /**
   * 发送成功的粉丝数
   */
  @XStreamAlias("SentCount")
  val sentCount = -1
  /**
   * 发送失败的粉丝数
   */
  @XStreamAlias("ErrorCount")
  val errorCount = -1
  /**
   * 有效期 (整形)，指的是时间戳，将于该时间戳认证过期
   */
  @XStreamAlias("ExpiredTime")
  private val _expiredTime = 0L
  val expiredTime
    get() = Date(_expiredTime)
  @XStreamAlias("FailTime")
  private val _failTime = 0L
  val failTime
    get() = Date(_failTime)
  /**
   * 认证失败的原因
   */
  @XStreamAlias("FailReason")
  val failReason: String? = null
  /**
   * 扫描信息
   */
  @XStreamAlias("ScanCodeInfo")
  val scanCodeInfo: ScanCodeInfo? = null
  /**
   * 发送的图片信息
   */
  @XStreamAlias("SendPicsInfo")
  val picsInfo: PicsInfo? = null
  /**
   * 发送的位置信息
   */
  @XStreamAlias("SendLocationInfo")
  val locationInfo: LocationInfo? = null
  /**
   * 卡券ID
   */
  @XStreamAlias("CardId")
  val cardId: String? = null
  /**
   * 卡券Code码
   */
  @XStreamAlias("UserCardCode")
  val cardCode: String? = null
  /**
   * 微信支付交易订单号（只有使用买单功能核销的卡券才会出现）
   */
  @XStreamAlias("TransId")
  val transactionId: String? = null
  /**
   * 门店ID，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现）
   */
  @XStreamAlias("LocationId")
  val storeId: Int? = null
  /**
   * 门店名称，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现）
   */
  @XStreamAlias("LocationName")
  val storeName: String? = null
  /**
   * 实付金额，单位为分
   */
  @XStreamAlias("Fee")
  val paidFee: String? = null
  /**
   * 应付金额，单位为分
   */
  @XStreamAlias("OriginalFee")
  val originalFee: String? = null

}


/**
 * 微信通知消息中，消息相关内容
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@XStreamAlias("message")
class Message {

  /**
   * 消息id，64位整型 (MsgId)
   */
  @XStreamAlias("MsgId")
  val messageId: Long? = null
  /**
   * 文本消息内容 (Content)
   */
  @XStreamAlias("Content")
  val content: String? = null
  /**
   * 消息媒体id，可以调用多媒体文件下载接口拉取该媒体 (MediaID)
   */
  @XStreamAlias("MediaId")
  val mediaId: String? = null
  /**
   * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据 (ThumbMediaId)
   */
  @XStreamAlias("ThumbMediaId")
  val mediaThumbId: String? = null
  /**
   * 图片链接（由系统生成） (PicUrl)
   */
  @XStreamAlias("PicUrl")
  val picUrl: String? = null
  /**
   * 语音格式，如amr，speex等 (Format)
   */
  @XStreamAlias("Format")
  val format: String? = null
  /**
   * 语音识别结果，UTF8编码 (recognition)
   */
  @XStreamAlias("Recognition")
  val recognition: String? = null
  /**
   * 消息标题 (Title)
   */
  @XStreamAlias("Title")
  val title: String? = null
  /**
   * 消息描述 (Description)
   */
  @XStreamAlias("Description")
  val description: String? = null
  /**
   * 消息链接 (Url)
   */
  @XStreamAlias("Url")
  val url: String? = null
  /**
   * 地理位置维度
   */
  @XStreamAlias("Location_X")
  val locationX: Double? = null
  /**
   * 地理位置经度
   */
  @XStreamAlias("Location_Y")
  val locationY: Double? = null
  /**
   * 地图缩放大小
   */
  @XStreamAlias("Scale")
  val scale: Int? = null
  /**
   * 地理位置信息
   */
  @XStreamAlias("Label")
  val address: String? = null

}
