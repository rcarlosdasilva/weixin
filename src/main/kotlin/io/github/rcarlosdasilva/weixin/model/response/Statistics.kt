package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.data.StatisticsMessageType
import io.github.rcarlosdasilva.weixin.terms.data.StatisticsNewsShareScene
import io.github.rcarlosdasilva.weixin.terms.data.StatisticsNewsUserSource
import io.github.rcarlosdasilva.weixin.terms.data.StatisticsUserSource

class InterfaceSummary {
  /**
   * 数据的日期
   */
  @SerializedName("ref_date")
  val date: String? = null
  /**
   * 数据的小时
   */
  @SerializedName("ref_hour")
  val hour: Int = 0
  /**
   * 通过服务器配置地址获得消息后，被动回复用户消息的次数
   */
  val callbackCount: Int = 0
  /**
   * 上述动作的失败次数
   */
  val failCount: Int = 0
  /**
   * 总耗时，除以callback_count即为平均耗时
   */
  val totalTimeCost: Int = 0
  /**
   * 最大耗时
   */
  val maxTimeCost: Int = 0
}

class MessageDistributed {
  /**
   * 数据的日期
   */
  @SerializedName("ref_date")
  val date: String? = null
  /**
   * 当日发送消息量分布的区间，0代表 “0”，1代表“1-5”，2代表“6-10”，3代表“10次以上”
   */
  val countInterval: Int = 0
  /**
   * 上行发送了（向公众号发送了）消息的用户数
   */
  @SerializedName("msg_user")
  val users: Int = 0
}

class MessageSummary {
  /**
   * 数据的日期
   */
  @SerializedName("ref_date")
  val date: String? = null
  /**
   * 数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
   */
  @SerializedName("ref_hour")
  val hour: Int = 0
  @SerializedName("msg_type")
  private val msgtype: Int = 0
  /**
   * 消息类型，代表含义如下： 1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）
   */
  val type = StatisticsMessageType.with(msgtype)
  /**
   * 上行发送了（向公众号发送了）消息的用户数
   */
  @SerializedName("msg_user")
  val users: Int = 0
  /**
   * 上行发送了消息的消息总数
   */
  @SerializedName("msg_count")
  val count: Int = 0
}

class NewsDetail {
  /**
   * 统计的日期，在getarticletotal接口中，ref_date指的是文章群发出日期， 而stat_date是数据统计日期
   */
  @SerializedName("stat_date")
  val date: String? = null
  /**
   * 送达人数，一般约等于总粉丝数（需排除黑名单或其他异常情况下无法收到消息的粉丝）
   */
  @SerializedName("target_user")
  val targetUsers: Int = 0
  /**
   * 图文页（点击群发图文卡片进入的页面）的阅读人数
   */
  @SerializedName("int_page_read_user")
  val newsReadUsers: Int = 0
  /**
   * 图文页的阅读次数
   */
  @SerializedName("int_page_read_count")
  val newsReadCount: Int = 0
  /**
   * 原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0
   */
  @SerializedName("ori_page_read_user")
  val originReadUsers: Int = 0
  /**
   * 原文页的阅读次数
   */
  @SerializedName("ori_page_read_count")
  val originReadCount: Int = 0
  /**
   * 分享的人数
   */
  @SerializedName("share_user")
  val shareUsers: Int = 0
  /**
   * 分享的次数
   */
  val shareCount: Int = 0
  /**
   * 收藏的人数
   */
  @SerializedName("add_to_fav_user")
  val collectUsers: Int = 0
  /**
   * 收藏的次数
   */
  @SerializedName("add_to_fav_count")
  val collectCount: Int = 0
  /**
   * 公众号会话阅读人数
   */
  @SerializedName("int_page_from_session_read_user")
  val fromSessionReadUsers: Int = 0
  /**
   * 公众号会话阅读次数
   */
  @SerializedName("int_page_from_session_read_count")
  val fromSessionReadCount: Int = 0
  /**
   * 历史消息页阅读人数
   */
  @SerializedName("int_page_from_hist_msg_read_user")
  val fromHistoryReadUsers: Int = 0
  /**
   * 历史消息页阅读次数
   */
  @SerializedName("int_page_from_hist_msg_read_count")
  val fromHistoryReadCount: Int = 0
  /**
   * 朋友圈阅读人数
   */
  @SerializedName("int_page_from_feed_read_user")
  val fromMomentsReadUsers: Int = 0
  /**
   * 朋友圈阅读次数
   */
  @SerializedName("int_page_from_feed_read_count")
  val fromMomentsReadCount: Int = 0
  /**
   * 好友转发阅读人数
   */
  @SerializedName("int_page_from_friends_read_user")
  val fromRepostReadUsers: Int = 0
  /**
   * 好友转发阅读次数
   */
  @SerializedName("int_page_from_friends_read_count")
  val fromRepostReadCount: Int = 0
  /**
   * 其他场景阅读人数
   */
  @SerializedName("int_page_from_other_read_user")
  val fromOtherReadUsers: Int = 0
  /**
   * 其他场景阅读次数
   */
  @SerializedName("int_page_from_other_read_count")
  val fromOtherReadCount: Int = 0
  /**
   * 公众号会话转发朋友圈人数
   */
  @SerializedName("feed_share_from_session_user")
  val sessionToMomentsUsers: Int = 0
  /**
   * 公众号会话转发朋友圈次数
   */
  @SerializedName("feed_share_from_session_cnt")
  val sessionToMomentsCount: Int = 0
  /**
   * 朋友圈转发朋友圈人数
   */
  @SerializedName("feed_share_from_feed_user")
  val momentsToMomentsUsers: Int = 0
  /**
   * 朋友圈转发朋友圈次数
   */
  @SerializedName("feed_share_from_feed_cnt")
  val momentsToMomentsCount: Int = 0
  /**
   * 其他场景转发朋友圈人数
   */
  @SerializedName("feed_share_from_other_user")
  val otherToMomentsUsers: Int = 0
  /**
   * 其他场景转发朋友圈次数
   */
  @SerializedName("feed_share_from_other_cnt")
  val otherToMomentsCount: Int = 0
}

class NewsSummary {
  /**
   * 数据的日期
   */
  @SerializedName("ref_date")
  val date: String? = null
  /**
   * 数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
   */
  @SerializedName("ref_hour")
  val hour: Int = 0
  /**
   * 请注意：这里的msgid实际上是由msgid（图文消息id，这也就是群发接口调用后返回的msg_data_id）和index（消息次序索引）组成，
   * 例如12003_3， 其中12003是msgid，即一次群发的消息的id；
   * 3为index，假设该次群发的图文消息共5个文章（因为可能为多图文），3表示5个中的第3个
   */
  @SerializedName("msgid")
  val messageId: String? = null
  /**
   * 图文消息的标题
   */
  val title: String? = null
  /**
   * 获取详细信息
   */
  val details: List<NewsDetail>? = null
  @SerializedName("user_source")
  private val usersource: Int = 0
  val source = StatisticsNewsUserSource.with(usersource)
  /**
   * 图文页（点击群发图文卡片进入的页面）的阅读人数
   */
  @SerializedName("int_page_read_user")
  val newsReadUsers: Int = 0
  /**
   * 图文页的阅读次数
   */
  @SerializedName("int_page_read_count")
  val newsReadCount: Int = 0
  /**
   * 原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0
   */
  @SerializedName("ori_page_read_user")
  val originReadUsers: Int = 0
  /**
   * 原文页的阅读次数
   */
  @SerializedName("ori_page_read_count")
  val originReadCount: Int = 0
  /**
   * 分享的人数
   */
  @SerializedName("share_user")
  val shareUsers: Int = 0
  /**
   * 分享的次数
   */
  val shareCount: Int = 0
  @SerializedName("share_scene")
  private val sharescene: Int = 0
  /**
   * 分享的场景 1代表好友转发 2代表朋友圈 3代表腾讯微博 255代表其他
   */
  val scene = StatisticsNewsShareScene.with(sharescene)
  /**
   * 收藏的人数
   */
  @SerializedName("add_to_fav_user")
  val collectUsers: Int = 0
  /**
   * 收藏的次数
   */
  @SerializedName("add_to_fav_count")
  val collectCount: Int = 0
}

class UserSummary {
  /**
   * 数据的日期
   */
  @SerializedName("ref_date")
  val date: String? = null
  @SerializedName("user_source")
  private val usersource: Int = 0
  /**
   * 用户的渠道，数值代表的含义如下： 0代表其他合计 1代表公众号搜索 17代表名片分享 30代表扫描二维码 43代表图文页右上角菜单
   * 51代表支付后关注（在支付完成页） 57代表图文页内公众号名称 75代表公众号文章广告 78代表朋友圈广告
   */
  val source = StatisticsUserSource.with(usersource)
  /**
   * 新增的用户数量
   */
  @SerializedName("new_user")
  val newUsers: Int = 0
  /**
   * 取消关注的用户数量，new_user减去cancel_user即为净增用户数量
   */
  @SerializedName("cancel_user")
  val cancelUsers: Int = 0
  /**
   * 总用户量
   */
  @SerializedName("cumulate_user")
  val totalUsers: Int = 0
}


class StatisticsInterfaceSummaryResponse {
  /**
   * 获取数据集合
   *
   * @return list of [InterfaceSummary]
   */
  val list: List<InterfaceSummary>? = null
}

class StatisticsMessageDistributedResponse {
  /**
   * 获取数据集合
   *
   * @return list of [MessageDistributed]
   */
  val list: List<MessageDistributed>? = null
}

class StatisticsMessageSummaryResponse {
  /**
   * 获取数据集合
   *
   * @return list of [MessageSummary]
   */
  val list: List<MessageSummary>? = null
}

class StatisticsNewsSummaryResponse {
  /**
   * 获取数据集合
   *
   * @return list of [NewsSummary]
   */
  val list: List<NewsSummary>? = null
}

class StatisticsUserSummaryResponse {
  /**
   * 获取数据集合
   *
   * @return list of [UserSummary]
   */
  val list: List<UserSummary>? = null
}