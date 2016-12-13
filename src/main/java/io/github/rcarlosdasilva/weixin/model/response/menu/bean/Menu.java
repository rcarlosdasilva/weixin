package io.github.rcarlosdasilva.weixin.model.response.menu.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Menu {

  @SerializedName("button")
  private List<Button> buttons;
  @SerializedName("menuid")
  private long menuId;
  @SerializedName("matchrule")
  private MatchRule matchRule;

  /**
   * 菜单按钮列表.
   */
  public List<Button> getButtons() {
    return buttons;
  }

  /**
   * 菜单id.
   */
  public long getMenuId() {
    return menuId;
  }

  /**
   * 匹配规则.
   */
  public MatchRule getMatchRule() {
    return matchRule;
  }

}
