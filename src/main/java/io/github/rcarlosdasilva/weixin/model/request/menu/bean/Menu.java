package io.github.rcarlosdasilva.weixin.model.request.menu.bean;

import java.util.List;

import com.google.common.collect.Lists;

public class Menu {

  private List<Button> buttons = Lists.newArrayList();
  private MatchRule matchRule;

  public List<Button> getButtons() {
    return buttons;
  }

  public MatchRule getMatchRule() {
    return matchRule;
  }

  public void setMatchRule(MatchRule matchRule) {
    this.matchRule = matchRule;
  }

}
