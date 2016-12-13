package io.github.rcarlosdasilva.weixin.model.request.message;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 公众号的自动回复规则获取请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MessageQueryAutoReplyRequest extends BasicRequest {

  public MessageQueryAutoReplyRequest() {
    this.path = ApiAddress.URL_MESSAGE_QUERY_AUTO_REPLY_STATUS;
  }

}
