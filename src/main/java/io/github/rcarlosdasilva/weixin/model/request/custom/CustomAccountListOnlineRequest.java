package io.github.rcarlosdasilva.weixin.model.request.custom;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取在线客服列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CustomAccountListOnlineRequest extends BasicWeixinRequest {

  public CustomAccountListOnlineRequest() {
    this.path = ApiAddress.URL_CUSTOM_ACCOUNT_LIST_ONLINE;
  }

}
