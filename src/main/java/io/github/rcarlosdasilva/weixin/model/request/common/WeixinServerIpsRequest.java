package io.github.rcarlosdasilva.weixin.model.request.common;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 微信服务器IP列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WeixinServerIpsRequest extends BasicRequest {

  public WeixinServerIpsRequest() {
    this.path = ApiAddress.URL_CERTIFICATE_SERVER_IP;
  }

}
