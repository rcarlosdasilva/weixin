package io.github.rcarlosdasilva.weixin.model.request.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * JS Ticket请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class JsTicketRequest extends BasicWeixinRequest {

  public JsTicketRequest() {
    this.path = ApiAddress.URL_CERTIFICATE_JS_TICKET;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&type=").append(Convention.JS_TICKET_TYPE).toString();
  }

}
