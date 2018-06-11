package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.NewsCommentListResponse
import io.github.rcarlosdasilva.weixin.terms.data.NewsCommentType

/**
 * 公众号图文消息留言管理API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpNewsComment(account: Mp) : Api(account) {

  /**
   * 打开已群发文章评论功能
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文，默认0
   * @return 是否成功
   */
  fun open(messageDataId: String, index: Int): Boolean =
      post(Boolean::class.java, NewsCommentOpenRequest(messageDataId, index))

  /**
   * 关闭已群发文章评论功能
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文，默认0
   * @return 是否成功
   */
  fun close(messageDataId: String, index: Int): Boolean =
      post(Boolean::class.java, NewsCommentCloseRequest(messageDataId, index))

  /**
   * 查看指定文章的评论数据
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认返回该msg_data_id的第一篇图文
   * @param begin 起始位置
   * @param count 获取数目（&gt;=50会被拒绝）
   * @param typeNews
   * typeNews=0普通评论和精选评论;typeNews=1普通评论;typeNews=2精选评论
   * @return [NewsCommentListResponse]
   */
  fun list(messageDataId: String, index: Int, begin: Int, count: Int, typeNews: NewsCommentType): NewsCommentListResponse =
      post(NewsCommentListResponse::class.java, NewsCommentListRequest(messageDataId, index, begin, count, typeNews))

  /**
   * 将评论标记精选
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId 用户评论id
   * @return 是否成功
   */
  fun star(messageDataId: String, index: Int, commentId: String): Boolean =
      post(Boolean::class.java, NewsCommentStarRequest(messageDataId, commentId, index))

  /**
   * 将评论取消精选
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId 用户评论id
   * @return 是否成功
   */
  fun unstar(messageDataId: String, index: Int, commentId: String): Boolean =
      post(Boolean::class.java, NewsCommentUnStarRequest(messageDataId, commentId, index))

  /**
   * 删除评论
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId 用户评论id
   * @return 是否成功
   */
  fun delete(messageDataId: String, index: Int, commentId: String): Boolean =
      post(Boolean::class.java, NewsCommentDeleteRequest(messageDataId, commentId, index))

  /**
   * 回复评论
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId 用户评论id
   * @param content 回复内容
   * @return 是否成功
   */
  fun reply(messageDataId: String, index: Int, commentId: String, content: String): Boolean =
      post(Boolean::class.java, NewsCommentReplyRequest(messageDataId, commentId, index, content))

  /**
   * 删除回复
   *
   * @param messageDataId 群发返回的msg_data_id
   * @param index 多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId 用户评论id
   * @return 是否成功
   */
  fun deleteReply(messageDataId: String, index: Int, commentId: String): Boolean =
      post(Boolean::class.java, NewsCommentDeleteReplyRequest(messageDataId, commentId, index))

}