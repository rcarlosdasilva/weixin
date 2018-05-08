package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.core.ExecuteException
import io.github.rcarlosdasilva.weixin.terms.*
import io.github.rcarlosdasilva.weixin.terms.data.MessageType
import io.github.rcarlosdasilva.weixin.terms.data.MessageType.*

/**
 * 模板内容参数
 *
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
data class Template(
  private val value: String,
  private val color: String
)

interface Message

class Card(@SerializedName("card_id") private val cardId: String) : Message

class Image(@SerializedName("media_id") private val mediaId: String) : Message

class Music(
  @SerializedName("musicurl") private val url: String,
  @SerializedName("hqmusicurl") private val url4hq: String,
  @SerializedName("thumb_media_id") private val mediaThumbId: String,
  private val title: String,
  private val description: String
) : Message

class News(
  private val title: String,
  private val url: String,
  private val description: String,
  @SerializedName("picurl") private val picUrl: String
)

class NewsExternal : Message {
  @SerializedName("articles")
  val articles = mutableListOf<News>()
}

class NewsInternal(@SerializedName("media_id") private val mediaId: String) : Message

class Text(private val content: String) : Message

class Video(
  @SerializedName("media_id") private val mediaId: String,
  @SerializedName("thumb_media_id") private val mediaThumbId: String,
  private val title: String,
  private val description: String
) : Message

class Voice(@SerializedName("media_id") private val mediaId: String) : Message

class MessageContainer(val type: MessageType) {
  var bean: Message? = null
  /**
   * 图文消息被判定为转载时，是否继续群发
   *
   * 继续群发（转载）为true，否则停止群发。 默认为true
   */
  var isCanReprint = true
  /**
   * 群发接口新增 clientmsgid 参数
   *
   * 开发者调用群发接口时可以主动设置 clientmsgid 参数，避免重复推送。 群发时，微信后台将对 24 小时内的群发记录进行检查，如果该
   * clientmsgid已经存在一条群发记录，则会拒绝本次群发请求，返回已存在的群发msgid，开发者可以调用“查询群发消息发送状态”接口查看该条群发的状态。
   */
  var businessMark: String? = null
  /**
   * 指定以某个客服帐号来发消息
   */
  var customServiceAccount: String? = null
}

class CustomService(@SerializedName("kf_account") private var account: String)


class MassFilter {
  @SerializedName("is_to_all")
  internal var isToAll = false
  @SerializedName("tag_id")
  internal var tagId: Int = 0
}


/**
 * 推送模板消息请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MessageSendWithTemplateRequest(
  @SerializedName("touser") private val to: String,
  @SerializedName("template_id") private val templateId: String,
  @Expose private val redirect: String,
  private val data: Map<String, Template>
) : MpRequest() {
  private var url: String? = redirect
  @SerializedName("miniprogram")
  private var miniProgram: MiniProgram? = null

  init {
    this.path = URL_MESSAGE_SEND_WITH_TEMPLATE
  }

  constructor(
    to: String,
    templateId: String,
    data: Map<String, Template>,
    appId: String,
    route: String
  ) : this(to, templateId, "", data) {
    miniProgram = MiniProgram(appId, route)
    url = null
  }

  inner class MiniProgram(
    private val appId: String,
    @SerializedName("pagepath") private val route: String
  )
}

/**
 * 公众号的自动回复规则获取请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MessageQueryAutoReplyRequest : MpRequest() {
  init {
    this.path = URL_MESSAGE_QUERY_AUTO_REPLY_STATUS
  }
}

/**
 * 消息群发请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MessageSendWithMassRequest : MpRequest() {
  @SerializedName("msgtype")
  private var type: String? = null
  private var filter: MassFilter? = null
  @SerializedName("touser")
  private var user: MassRequestUser? = null
  @SerializedName("towxname")
  private var wxname: String? = null
  @SerializedName("mpnews")
  private var newsInternal: NewsInternal? = null
  private var text: Text? = null
  private var voice: Voice? = null
  private var image: Image? = null
  @SerializedName("mpvideo")
  private var video: Video? = null
  @SerializedName("wxcard")
  private var card: Card? = null
  /**
   * 群发接口新增 send_ignore_reprint 参数，开发者可以对群发接口的 send_ignore_reprint
   * 参数进行设置，指定待群发的文章被判定为转载时，是否继续群发。
   * 当 send_ignore_reprint 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。
   * 当 send_ignore_reprint 参数设置为0时，文章被判定为转载时，将停止群发操作。
   * send_ignore_reprint 默认为0。
   */
  @SerializedName("send_ignore_reprint")
  var isCanReprint = GLOBAL_TRUE_NUMBER
  /**
   * 群发接口新增 clientmsgid 参数，开发者调用群发接口时可以主动设置 clientmsgid 参数，避免重复推送。 群发时，微信后台将对 24
   * 小时内的群发记录进行检查，如果该 clientmsgid
   * 已经存在一条群发记录，则会拒绝本次群发请求，返回已存在的群发msgid，开发者可以调用“查询群发消息发送状态”接口查看该条群发的状态。
   *
   * 开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
   */
  @SerializedName("clientmsgid")
  var mark: String? = null

  /**
   * 按标签群发
   */
  fun forTag(tagId: Int) {
    this.path = URL_MESSAGE_SEND_WITH_MASS_FOR_TAG
    this.filter = MassFilter().apply { this.tagId = tagId }
  }

  fun forAll() {
    this.path = URL_MESSAGE_SEND_WITH_MASS_FOR_TAG
    this.filter = MassFilter().apply { this.isToAll = true }
  }

  /**
   * 按OpenId群发
   */
  fun forUsers(users: List<String>) {
    this.path = URL_MESSAGE_SEND_WITH_MASS_FOR_USERS
    this.user = MassRequestUser(users)
  }

  /**
   * 预览接口
   */
  fun forPreview(isOpenId: Boolean, target: String) {
    this.path = URL_MESSAGE_SEND_WITH_MASS_PREVIEW
    if (isOpenId) {
      this.user = MassRequestUser(target)
    } else {
      this.wxname = target
    }
  }

  fun setMessage(type: MessageType, message: Message) {
    this.type = type.text
    when (type) {
      TEXT -> this.text = message as Text
      IMAGE -> this.image = message as Image
      VOICE -> this.voice = message as Voice
      VIDEO -> this.video = message as Video
      MPVIDEO -> this.video = message as Video
      NEWS_INTERNAL -> this.newsInternal = message as NewsInternal
      CARD -> this.card = message as Card
      else -> throw ExecuteException("图文消息推送不支持音乐或外链接图文消息")
    }
  }

  class MassRequestUser {
    var users: List<String>? = null
    var user: String? = null

    constructor(users: List<String>) : super() {
      this.users = users
    }

    constructor(user: String) : super() {
      this.user = user
    }
  }

}

/**
 * 客服消息推送请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MessageSendWithCustomRequest(@SerializedName("touser") private val to: String) : MpRequest() {
  @SerializedName("msgtype")
  private var type: String? = null
  private var text: Text? = null
  private var image: Image? = null
  private var voice: Voice? = null
  private var video: Video? = null
  private var music: Music? = null
  @SerializedName("news")
  private var newsExternal: NewsExternal? = null
  @SerializedName("mpnews")
  private var newsInternal: NewsInternal? = null
  @SerializedName("wxcard")
  private var card: Card? = null
  @SerializedName("customservice")
  var customService: CustomService? = null

  init {
    this.path = URL_MESSAGE_SEND_WITH_CUSTOM
  }

  fun setMessage(type: MessageType, message: Message) {
    this.type = type.text
    when (type) {
      TEXT -> this.text = message as Text
      IMAGE -> this.image = message as Image
      VOICE -> this.voice = message as Voice
      VIDEO -> this.video = message as Video
      MPVIDEO -> this.video = message as Video
      MUSIC -> this.music = message as Music
      NEWS_EXTERNAL -> this.newsExternal as NewsExternal
      NEWS_INTERNAL -> this.newsInternal = message as NewsInternal
      CARD -> this.card = message as Card
    }
  }
}

/**
 * 删除群发请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MessageDeleteMassRequest(
  @SerializedName("msg_id") private val messageId: String,
  @SerializedName("article_idx") private val index: Int
) : MpRequest() {
  init {
    this.path = URL_MESSAGE_DELETE_MASS
  }
}

/**
 * 删除群发请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MessageQueryMassStatusRequest(@SerializedName("msg_id") private var messageId: String) : MpRequest() {
  init {
    this.path = URL_MESSAGE_QUERY_MASS_STATUS
  }
}
