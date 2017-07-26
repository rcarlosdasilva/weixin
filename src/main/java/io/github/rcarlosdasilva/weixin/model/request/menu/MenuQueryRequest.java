package io.github.rcarlosdasilva.weixin.model.request.menu;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 查询菜单请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MenuQueryRequest extends BasicWeixinRequest {

  public MenuQueryRequest() {
    this.path = ApiAddress.URL_MENU_QUERY;
  }

}
