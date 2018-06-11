package io.github.rcarlosdasilva.weixin.model.builder

import io.github.rcarlosdasilva.weixin.model.notification.NotificationMeta
import io.github.rcarlosdasilva.weixin.model.notification.PlaintextNotificationResponse
import io.github.rcarlosdasilva.weixin.model.notification.ResponseAdditionalInfo
import io.github.rcarlosdasilva.weixin.terms.data.MessageType
import java.util.*

/**
 * 微信推送通知的回复构建器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class NotificationResponseBuilder private constructor() {

  private var meta: NotificationMeta? = null
  private val response: PlaintextNotificationResponse = PlaintextNotificationResponse()
  private var noResponse = false

  /**
   * 默认返回sucess.
   *
   * 假如服务器无法保证在五秒内处理并回复，必须做出下述回复：
   * 1. 直接回复success（推荐方式）
   * 2. 直接回复空串（指字节长度为0的空字符串，而不是XML结构体中content字段的内容为空）
   *
   * @return [NotificationResponseBuilder]
   */
  fun responseNothing(): NotificationResponseBuilder = this.apply { noResponse = true }

  /**
   * 回复文本消息.
   *
   * @param content 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
   * @return [NotificationResponseBuilder]
   */
  fun responseText(content: String): NotificationResponseBuilder = this.apply {
    response.type = MessageType.TEXT
    response.info.content = content
  }

  /**
   * 回复图片消息.
   *
   * @param mediaId 通过素材管理中的接口上传多媒体文件，得到的id。
   * @return [NotificationResponseBuilder]
   */
  fun responseImage(mediaId: String): NotificationResponseBuilder = this.apply {
    this.response.type = MessageType.IMAGE
    this.response.info.mediaId = mediaId
  }

  /**
   * 回复语音消息.
   *
   * @param mediaId 通过素材管理中的接口上传多媒体文件，得到的id。
   * @return [NotificationResponseBuilder]
   */
  fun responseVoice(mediaId: String): NotificationResponseBuilder = this.apply {
    this.response.type = MessageType.VOICE
    this.response.info.mediaId = mediaId
  }

  /**
   * 回复视频消息.
   *
   * @param mediaId 通过素材管理中的接口上传多媒体文件，得到的id。
   * @param title 视频消息的标题
   * @param description 视频消息的描述
   * @return [NotificationResponseBuilder]
   */
  fun responseVideo(mediaId: String, title: String, description: String): NotificationResponseBuilder = this.apply {
    this.response.type = MessageType.VIDEO
    this.response.info.mediaId = mediaId
    this.response.info.title = title
    this.response.info.description = description
  }

  /**
   * 回复音乐消息.
   *
   * @param mediaThumbId 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
   * @param title 音乐标题
   * @param description 音乐描述
   * @param musicUrl 音乐链接
   * @param musicHqUrl 高质量音乐链接，WIFI环境优先使用该链接播放音乐
   * @return [NotificationResponseBuilder]
   */
  fun responseMusic(mediaThumbId: String,
                    title: String,
                    description: String,
                    musicUrl: String,
                    musicHqUrl: String): NotificationResponseBuilder = this.apply {
    this.response.type = MessageType.MUSIC
    this.response.info.title = title
    this.response.info.description = description
    this.response.info.mediaThumbId = mediaThumbId
    this.response.info.url = musicUrl
    this.response.info.otherUrl = musicHqUrl
  }

  /**
   * 回复图文消息.
   *
   * 指定图文消息中的一个图文信息，如有多个图文，可多次调用该方法
   *
   * @param title 图文消息标题
   * @param description 图文消息描述
   * @param url 点击图文消息跳转链接
   * @param picUrl 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
   * @return [NotificationResponseBuilder]
   */
  fun responseNewsOneOf(title: String, description: String, url: String, picUrl: String): NotificationResponseBuilder = this.apply {
    this.response.type = MessageType.NEWS_EXTERNAL
    this.response.infos.add(ResponseAdditionalInfo(title, description, url, picUrl))
  }

  /**
   * 构建微信推送响应模型.
   *
   * @return [PlaintextNotificationResponse]
   */
  fun build(): PlaintextNotificationResponse? {
    if (this.noResponse) {
      return null
    }

    return this.response.apply {
      fromUser = meta!!.toUser
      toUser = meta!!.fromUser
      time = Calendar.getInstance().timeInMillis
    }
  }

  companion object {
    /**
     * 创建微信推送通知的回复构建器.
     *
     * @param notificationMeta 推送基础内容.
     * @return 构建器
     */
    @JvmStatic
    fun with(notificationMeta: NotificationMeta): NotificationResponseBuilder = NotificationResponseBuilder().apply { meta = notificationMeta }
  }

}
