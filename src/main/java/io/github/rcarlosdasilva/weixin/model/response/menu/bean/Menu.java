package io.github.rcarlosdasilva.weixin.model.response.menu.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Menu {

  @SerializedName("button")
  private List<Button> buttons;
  @SerializedName("menuid")
  private long menuId;
  @SerializedName("matchrule")
  private List<MatchRule> matchRule;

  /**
   * 菜单按钮列表.
   * 
   * @return list of {@link Button}
   */
  public List<Button> getButtons() {
    return buttons;
  }

  /**
   * 菜单id.
   * 
   * @return menu id
   */
  public long getMenuId() {
    return menuId;
  }

  /**
   * 匹配规则.
   * 
   * @return {@link MatchRule}
   */
  public List<MatchRule> getMatchRule() {
    return matchRule;
  }

}
