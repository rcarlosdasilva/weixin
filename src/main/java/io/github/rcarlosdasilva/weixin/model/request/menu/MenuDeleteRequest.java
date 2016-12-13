package io.github.rcarlosdasilva.weixin.model.request.menu;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 删除菜单请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MenuDeleteRequest extends BasicRequest {

  @SerializedName("menuid")
  private long menuId;

  public MenuDeleteRequest() {
    this.path = ApiAddress.URL_MENU_DELETE;
  }

  /**
   * 个性化菜单.
   */
  public void withConditional() {
    this.path = ApiAddress.URL_MENU_CONDITIONAL_DELETE;
  }

  /**
   * 菜单id.
   */
  public void setMenuId(long menuId) {
    this.menuId = menuId;
  }

}
