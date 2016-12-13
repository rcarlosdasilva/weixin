package io.github.rcarlosdasilva.weixin.model.response.menu;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.response.menu.bean.Menu;

public class MenuCompleteResponse {

  @SerializedName("is_menu_open")
  private int menuOpened;
  @SerializedName("selfmenu_info")
  private Menu menu;

  /** 菜单是否开启. */
  public boolean isMenuOpened() {
    return menuOpened == Convention.GLOBAL_TRUE_NUMBER;
  }

  /**
   * 菜单信息.
   */
  public Menu getMenu() {
    return menu;
  }

}
