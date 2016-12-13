package io.github.rcarlosdasilva.weixin.model.response.menu;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.menu.bean.Menu;

public class MenuInfoResponse {

  private Menu menu;
  @SerializedName("conditionalmenu")
  private Menu conditionalMenu;

  /** 自定义菜单. */
  public Menu getMenu() {
    return menu;
  }

  /** 个性化菜单. */
  public Menu getConditionalMenu() {
    return conditionalMenu;
  }

}
