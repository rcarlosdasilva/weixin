package io.github.rcarlosdasilva.weixin.model.response.comment;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.comment.bean.Comment;

public class CommentListResponse {

  private int total;
  @SerializedName("comment")
  private List<Comment> comments;

  public int getTotal() {
    return total;
  }

  public List<Comment> getComments() {
    return comments;
  }

}
