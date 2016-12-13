package io.github.rcarlosdasilva.weixin.model.request.menu;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取自定义菜单配置请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MenuQueryCompleteRequest extends BasicRequest {

  public MenuQueryCompleteRequest() {
    this.path = ApiAddress.URL_MENU_QUERY_COMPLETE;
  }

}
