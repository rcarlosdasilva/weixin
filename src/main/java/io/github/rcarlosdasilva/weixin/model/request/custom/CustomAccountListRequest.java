package io.github.rcarlosdasilva.weixin.model.request.custom;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取客服列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CustomAccountListRequest extends BasicWeixinRequest {

  public CustomAccountListRequest() {
    this.path = ApiAddress.URL_CUSTOM_ACCOUNT_LIST;
  }

}
