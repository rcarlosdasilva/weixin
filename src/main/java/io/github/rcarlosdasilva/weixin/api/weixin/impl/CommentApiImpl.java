package io.github.rcarlosdasilva.weixin.api.weixin.impl;

import io.github.rcarlosdasilva.weixin.api.BasicApi;
import io.github.rcarlosdasilva.weixin.api.weixin.CommentApi;
import io.github.rcarlosdasilva.weixin.common.dictionary.CommentType;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentCloseRequest;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentDeleteReplyRequest;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentDeleteRequest;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentListRequest;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentOpenRequest;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentReplyRequest;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentStarRequest;
import io.github.rcarlosdasilva.weixin.model.request.comment.CommentUnstarRequest;
import io.github.rcarlosdasilva.weixin.model.response.comment.CommentListResponse;

public class CommentApiImpl extends BasicApi implements CommentApi {

  public CommentApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public boolean open(String messageDataId, int index) {
    CommentOpenRequest requestModel = new CommentOpenRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean close(String messageDataId, int index) {
    CommentCloseRequest requestModel = new CommentCloseRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);

    return post(Boolean.class, requestModel);
  }

  @Override
  public CommentListResponse list(String messageDataId, int index, int begin, int count,
      CommentType type) {
    CommentListRequest requestModel = new CommentListRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);
    requestModel.setBegin(begin);
    requestModel.setCount(count);
    requestModel.setType(type);

    return post(CommentListResponse.class, requestModel);
  }

  @Override
  public boolean star(String messageDataId, int index, String commentId) {
    CommentStarRequest requestModel = new CommentStarRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);
    requestModel.setCommentId(commentId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean unstar(String messageDataId, int index, String commentId) {
    CommentUnstarRequest requestModel = new CommentUnstarRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);
    requestModel.setCommentId(commentId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean delete(String messageDataId, int index, String commentId) {
    CommentDeleteRequest requestModel = new CommentDeleteRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);
    requestModel.setCommentId(commentId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean reply(String messageDataId, int index, String commentId, String content) {
    CommentReplyRequest requestModel = new CommentReplyRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);
    requestModel.setCommentId(commentId);
    requestModel.setContent(content);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean deleteReply(String messageDataId, int index, String commentId) {
    CommentDeleteReplyRequest requestModel = new CommentDeleteReplyRequest();
    requestModel.setMessageDataId(messageDataId);
    requestModel.setIndex(index);
    requestModel.setCommentId(commentId);

    return post(Boolean.class, requestModel);
  }

}
