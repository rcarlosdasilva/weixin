package io.github.rcarlosdasilva.weixin.model.request.menu;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 查询菜单请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MenuQueryRequest extends BasicRequest {

  public MenuQueryRequest() {
    this.path = ApiAddress.URL_MENU_QUERY;
  }

}
