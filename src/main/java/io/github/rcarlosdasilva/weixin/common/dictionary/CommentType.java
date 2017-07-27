package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 图文消息评论类型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum CommentType {

  /**
   * 普通评论和精选评论.
   */
  ALL(0),
  /**
   * 普通评论.
   */
  NORMAL(1),
  /**
   * 精选评论.
   */
  STAR(2);

  private int code;

  CommentType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
