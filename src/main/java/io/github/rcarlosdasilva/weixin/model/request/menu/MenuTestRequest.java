package io.github.rcarlosdasilva.weixin.model.request.menu;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 测试个性化菜单请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MenuTestRequest extends BasicRequest {

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
