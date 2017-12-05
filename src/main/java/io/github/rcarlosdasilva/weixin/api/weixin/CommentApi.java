package io.github.rcarlosdasilva.weixin.api.weixin;

import io.github.rcarlosdasilva.weixin.common.dictionary.CommentType;
import io.github.rcarlosdasilva.weixin.model.response.comment.CommentListResponse;

/**
 * 公众号图文消息留言管理API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface CommentApi {

  /**
   * 打开已群发文章评论功能.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文，默认0
   * @return 是否成功
   */
  boolean open(String messageDataId, int index);

  /**
   * 关闭已群发文章评论功能.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文，默认0
   * @return 是否成功
   */
  boolean close(String messageDataId, int index);

  /**
   * 查看指定文章的评论数据.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认返回该msg_data_id的第一篇图文
   * @param begin
   *          起始位置
   * @param count
   *          获取数目（&gt;=50会被拒绝）
   * @param type
   *          type=0普通评论和精选评论;type=1普通评论;type=2精选评论
   * @return {@link CommentListResponse}
   */
  CommentListResponse list(String messageDataId, int index, int begin, int count, CommentType type);

  /**
   * 将评论标记精选.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId
   *          用户评论id
   * @return 是否成功
   */
  boolean star(String messageDataId, int index, String commentId);

  /**
   * 将评论取消精选.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId
   *          用户评论id
   * @return 是否成功
   */
  boolean unstar(String messageDataId, int index, String commentId);

  /**
   * 删除评论.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId
   *          用户评论id
   * @return 是否成功
   */
  boolean delete(String messageDataId, int index, String commentId);

  /**
   * 回复评论.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId
   *          用户评论id
   * @param content
   *          回复内容
   * @return 是否成功
   */
  boolean reply(String messageDataId, int index, String commentId, String content);

  /**
   * 删除回复.
   * 
   * @param messageDataId
   *          群发返回的msg_data_id
   * @param index
   *          多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
   * @param commentId
   *          用户评论id
   * @return 是否成功
   */
  boolean deleteReply(String messageDataId, int index, String commentId);

}
