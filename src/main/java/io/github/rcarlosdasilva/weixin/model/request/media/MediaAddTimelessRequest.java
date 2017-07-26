package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 新增永久素材请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MediaAddTimelessRequest extends BasicWeixinRequest {

  private String type;

  public MediaAddTimelessRequest() {
    this.path = ApiAddress.URL_MEDIA_TIMELESS_ADD;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&type=").append(this.type).toString();
  }

}
