package io.github.rcarlosdasilva.weixin.model.request.menu.bean;

import java.util.List;

public class Menu {

  private List<Button> buttons;
  private MatchRule matchRule;

  public List<Button> getButtons() {
    return buttons;
  }

  public void setButtons(List<Button> buttons) {
    this.buttons = buttons;
  }

  public MatchRule getMatchRule() {
    return matchRule;
  }

  public void setMatchRule(MatchRule matchRule) {
    this.matchRule = matchRule;
  }

}
