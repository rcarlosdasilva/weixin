package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.GLOBAL_TRUE_NUMBER

class MessageSendWithTemplateResponse {
  /**
   * 消息id
   *
   * @return message id
   */
  @SerializedName("msgid")
  val messageId: Long = 0
}

class Reply {
  /**
   * 自动回复的类型。关注后自动回复和消息自动回复的类型仅支持文本（text）、图片（img）、
   * 语音（voice）、视频（video），关键词自动回复则还多了图文消息（news）
   */
  val type: String? = null
  /**
   * 对于文本类型，content是文本内容，对于图文、图片、语音、视频类型，content是mediaID
   */
  val content: String? = null
  /**
   * 图文消息的信息
   */
  @SerializedName("news_info")
  val newsList: NewsInfoList? = null
}

class NewsInfoList {
  val list: List<News>? = null
}

class News {
  /**
   * 标题
   */
  val title: String? = null
  /**
   * 摘要
   */
  val digest: String? = null
  /**
   * 作者
   */
  val author: String? = null
  private val showCover: Int = 0
  /**
   * 是否显示封面，0为不显示，1为显示
   */
  val isShowCover: Boolean
    get() = showCover == GLOBAL_TRUE_NUMBER
  /**
   * 封面图片的URL
   */
  val coverUrl: String? = null
  /**
   * 正文的URL
   */
  val contentUrl: String? = null
  /**
   * 原文的URL，若置空则无查看原文入口
   */
  val sourceUrl: String? = null
}

class KeywordReplyRule {
  /**
   * 规则名称
   */
  val ruleName: String? = null
  /**
   * 创建时间
   */
  val createTime: Long = 0
  /**
   * 回复模式，reply_all代表全部回复，random_one代表随机回复其中一条
   */
  val replyMode: String? = null
  /**
   * 匹配的关键词列表
   */
  @SerializedName("keyword_list_info")
  val keywords: List<Keyword>? = null
  /**
   * 回复列表
   */
  @SerializedName("reply_list_info")
  val replies: List<Reply>? = null
}

class Keyword {
  /**
   * 自动回复的类型。关注后自动回复和消息自动回复的类型仅支持文本（text）、图片（img）、语音（voice）、视频（video），关键词自动回复则还多了图文消息（news）
   */
  val type: String? = null
  /**
   * 匹配模式，contain代表消息中含有该关键词即可，equal表示消息内容必须和关键词严格相同
   */
  val matchMode: String? = null
  /**
   * 对于文本类型，content是文本内容，对于图文、图片、语音、视频类型，content是mediaID
   */
  val content: String? = null
}

class AutoReply4KeywordsInfo {
  @SerializedName("list")
  val rules: List<KeywordReplyRule>? = null
}

class MessageQueryAutoReplyResponse {
  @SerializedName("is_add_friend_reply_open")
  private val enableWhenAddedFriend: Int = 0
  /**
   * 关注后自动回复是否开启，0代表未开启，1代表开启
   */
  val isEnableWhenAddedFriend: Boolean
    get() = enableWhenAddedFriend == GLOBAL_TRUE_NUMBER
  @SerializedName("is_autoreply_open")
  private val enableWhenReceivedMessage: Int = 0
  /**
   * 消息自动回复是否开启，0代表未开启，1代表开启
   */
  val isEnableWhenReceivedMessage: Boolean
    get() = enableWhenReceivedMessage == GLOBAL_TRUE_NUMBER
  /**
   * 关注后自动回复的信息
   */
  @SerializedName("add_friend_autoreply_info")
  val addedFriendInfo: Reply? = null
  /**
   * 消息自动回复的信息
   */
  @SerializedName("message_default_autoreply_info")
  val receivedMessageInfo: Reply? = null
  /**
   * 关键词自动回复的信息
   */
  @SerializedName("keyword_autoreply_info")
  val keywordsInfo: AutoReply4KeywordsInfo? = null
}

class MessageSendWithMassResponse {
  /**
   * 消息发送任务的ID
   */
  @SerializedName("msg_id")
  val messageId: String? = null
  /**
   * 消息的数据ID，，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，
   * 获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍
   */
  @SerializedName("msg_data_id")
  val messageDataId: String? = null
}