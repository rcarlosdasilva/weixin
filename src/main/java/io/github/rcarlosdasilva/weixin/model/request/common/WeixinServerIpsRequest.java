package io.github.rcarlosdasilva.weixin.model.request.common;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 微信服务器IP列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class WeixinServerIpsRequest extends BasicWeixinRequest {

  public WeixinServerIpsRequest() {
    this.path = ApiAddress.URL_CERTIFICATE_SERVER_IP;
  }

}
