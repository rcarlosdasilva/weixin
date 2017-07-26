package io.github.rcarlosdasilva.weixin.model.request.message;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 公众号的自动回复规则获取请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MessageQueryAutoReplyRequest extends BasicWeixinRequest {

  public MessageQueryAutoReplyRequest() {
    this.path = ApiAddress.URL_MESSAGE_QUERY_AUTO_REPLY_STATUS;
  }

}
