package io.github.rcarlosdasilva.weixin.model.response.menu;

import com.google.gson.annotations.SerializedName;

public class MenuCreateResponse {

  @SerializedName("menuid")
  private long menuId;

  /**
   * 菜单id（个性化菜单）.
   * 
   * @return menu id
   */
  public long getMenuId() {
    return menuId;
  }

}
