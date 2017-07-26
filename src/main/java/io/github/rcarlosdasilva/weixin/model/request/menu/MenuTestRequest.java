package io.github.rcarlosdasilva.weixin.model.request.menu;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 测试个性化菜单请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MenuTestRequest extends BasicWeixinRequest {

  @SerializedName("user_id")
  private String userLabel;

  public MenuTestRequest() {
    this.path = ApiAddress.URL_MENU_CONDITIONAL_TEST;
  }

  /**
   * 微信号或openid.
   * 
   * @param userLabel
   *          微信号
   */
  public void setUserLabel(String userLabel) {
    this.userLabel = userLabel;
  }

}
