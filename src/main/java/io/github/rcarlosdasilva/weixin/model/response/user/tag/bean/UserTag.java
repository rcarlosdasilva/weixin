package io.github.rcarlosdasilva.weixin.model.response.user.tag.bean;

public class UserTag {

  private int id;
  private String name;
  private int count;

  /**
   * 标签id.
   */
  public int getId() {
    return id;
  }

  /**
   * 标签名.
   */
  public String getName() {
    return name;
  }

  /**
   * 标签下用户数.
   */
  public int getCount() {
    return count;
  }

}
