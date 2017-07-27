package io.github.rcarlosdasilva.weixin.model.response.comment.bean;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.Convention;

public class Comment {

  @SerializedName("user_comment_id")
  private String commentId;
  @SerializedName("openid")
  private String openId;
  @SerializedName("create_time")
  private long time;
  private String content;
  @SerializedName("comment_type")
  private int star;
  private Reply reply;

  /**
   * //用户评论id
   * 
   * @return user_comment_id
   */
  public String getCommentId() {
    return commentId;
  }

  /**
   * openid
   * 
   * @return openid
   */
  public String getOpenId() {
    return openId;
  }

  /**
   * 评论时间
   * 
   * @return create_time
   */
  public long getTime() {
    return time;
  }

  /**
   * 评论内容
   * 
   * @return content
   */
  public String getContent() {
    return content;
  }

  /**
   * 是否精选评论
   * 
   * @return comment_type
   */
  public boolean isStar() {
    return star == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 回复
   * 
   * @return reply
   */
  public Reply getReply() {
    return reply;
  }

}
