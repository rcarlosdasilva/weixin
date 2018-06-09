package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.handler.Freeze
import io.github.rcarlosdasilva.weixin.terms.*
import io.github.rcarlosdasilva.weixin.terms.data.NewsCommentType

class NewsCommentCloseRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    private val index: Int = 0
) : MpRequest() {
  init {
    this.path = URL_NEWS_COMMENT_ABILITY_CLOSE
  }
}

class NewsCommentDeleteReplyRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    @SerializedName("user_comment_id") private val commentId: String,
    private val index: Int = 0
) : MpRequest() {
  init {
    this.path = URL_NEWS_COMMENT_REPLY_DELETE
  }
}

class NewsCommentDeleteRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    @SerializedName("user_comment_id") private val commentId: String,
    private val index: Int = 0
) : MpRequest() {
  init {
    this.path = URL_NEWS_COMMENT_DELETE
  }
}

class NewsCommentListRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    private val index: Int = 0,
    private val begin: Int = 0,
    private val count: Int = 0,
    @Freeze private val newsCommentType: NewsCommentType
) : MpRequest() {
  private val type: Int = newsCommentType.code

  init {
    this.path = URL_NEWS_COMMENT_LIST
  }
}

class NewsCommentOpenRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    private val index: Int = 0
) : MpRequest() {
  init {
    this.path = URL_NEWS_COMMENT_ABILITY_OPEN
  }
}

class NewsCommentReplyRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    @SerializedName("user_comment_id") private val commentId: String,
    private val index: Int = 0,
    private val content: String
) : MpRequest() {
  init {
    this.path = URL_NEWS_COMMENT_REPLY
  }
}

class NewsCommentStarRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    @SerializedName("user_comment_id") private val commentId: String,
    private val index: Int = 0
) : MpRequest() {
  init {
    this.path = URL_NEWS_COMMENT_STAR
  }
}

class NewsCommentUnStarRequest(
    @SerializedName("msg_data_id") private val messageDataId: String,
    @SerializedName("user_comment_id") private val commentId: String,
    private val index: Int = 0
) : MpRequest() {
  init {
    this.path = URL_NEWS_COMMENT_UNSTAR
  }
}
