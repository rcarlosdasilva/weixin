package io.github.rcarlosdasilva.weixin.model.response.user.group.bean;

public class UserGroup {

  private int id;
  private String name;
  private int count;

  /**
   * id.
   */
  public int getId() {
    return id;
  }

  /**
   * 组名.
   */
  public String getName() {
    return name;
  }

  /**
   * 组中用户数.
   */
  public int getCount() {
    return count;
  }

}
