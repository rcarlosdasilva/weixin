package io.github.rcarlosdasilva.weixin.model.response.user.group.bean;

public class UserGroup {

  private int id;
  private String name;
  private int count;

  /**
   * id.
   * 
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * 组名.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * 组中用户数.
   * 
   * @return count
   */
  public int getCount() {
    return count;
  }

}
