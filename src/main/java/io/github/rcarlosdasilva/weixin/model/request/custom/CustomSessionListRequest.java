package io.github.rcarlosdasilva.weixin.model.request.custom;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取客服会话列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CustomSessionListRequest extends BasicWeixinRequest {

  private String account;

  public CustomSessionListRequest() {
    this.path = ApiAddress.URL_CUSTOM_SESSION_LIST;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&kf_account=").append(this.account).toString();
  }

}
