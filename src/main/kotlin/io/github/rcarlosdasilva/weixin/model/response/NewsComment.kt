package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.GLOBAL_TRUE_NUMBER

class NewsCommentListResponse {
  val total: Int = 0
  @SerializedName("comment")
  val comments: List<NewsComment>? = null
}

class NewsComment {
  /**
   * 用户评论id
   */
  @SerializedName("user_comment_id")
  val commentId: String? = null
  /**
   * openid
   */
  @SerializedName("openid")
  val openId: String? = null
  /**
   * 评论时间
   */
  @SerializedName("create_time")
  val time: Long = 0
  /**
   * 评论内容
   */
  val content: String? = null
  @SerializedName("comment_type")
  private val star: Int = 0
  /**
   * 回复
   */
  val replies: NewsCommentReply? = null

  /**
   * 是否精选评论
   */
  val isStar: Boolean
    get() = star == GLOBAL_TRUE_NUMBER
}

class NewsCommentReply {
  /**
   * 作者回复内容
   */
  val content: String? = null
  /**
   * 作者回复时间
   */
  @SerializedName("create_time")
  val time: Long = 0
}
