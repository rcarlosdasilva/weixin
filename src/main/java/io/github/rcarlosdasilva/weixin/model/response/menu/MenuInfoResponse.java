package io.github.rcarlosdasilva.weixin.model.response.menu;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.menu.bean.Menu;

public class MenuInfoResponse {

  private Menu menu;
  @SerializedName("conditionalmenu")
  private List<Menu> conditionalMenu;

  /**
   * 自定义菜单.
   * 
   * @return {@link Menu}
   */
  public Menu getMenu() {
    return menu;
  }

  /**
   * 个性化菜单.
   * 
   * @return {@link Menu}
   */
  public List<Menu> getConditionalMenu() {
    return conditionalMenu;
  }

}
