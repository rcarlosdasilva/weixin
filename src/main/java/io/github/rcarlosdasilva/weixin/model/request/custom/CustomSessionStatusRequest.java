package io.github.rcarlosdasilva.weixin.model.request.custom;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取会话状态请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CustomSessionStatusRequest extends BasicWeixinRequest {

  private String openId;

  public CustomSessionStatusRequest() {
    this.path = ApiAddress.URL_CUSTOM_SESSION_STATUS;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&openid=").append(this.openId).toString();
  }

}
