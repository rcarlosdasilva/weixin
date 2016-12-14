package io.github.rcarlosdasilva.weixin.model.response.user.tag.bean;

public class UserTag {

  private int id;
  private String name;
  private int count;

  /**
   * 标签id.
   * 
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * 标签名.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * 标签下用户数.
   * 
   * @return count
   */
  public int getCount() {
    return count;
  }

}
