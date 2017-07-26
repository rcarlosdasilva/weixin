package io.github.rcarlosdasilva.weixin.model.request.menu;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.Button;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.MatchRule;

/**
 * 创建菜单请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MenuCreateRequest extends BasicWeixinRequest {

  @SerializedName("button")
  private List<Button> buttons;
  @SerializedName("matchrule")
  private MatchRule matchRule;

  public MenuCreateRequest() {
    this.path = ApiAddress.URL_MENU_CREATE;
  }

  public void withConditional() {
    this.path = ApiAddress.URL_MENU_CONDITIONAL_CREATE;
  }

  public void setButtons(List<Button> buttons) {
    this.buttons = buttons;
  }

  public void setMatchRule(MatchRule matchRule) {
    this.matchRule = matchRule;
  }

}
