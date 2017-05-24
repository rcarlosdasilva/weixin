package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 新增素材请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MediaAddTemporaryRequest extends BasicWeixinRequest {

  private String type;

  public MediaAddTemporaryRequest() {
    this.path = ApiAddress.URL_MEDIA_TEMPORARY_ADD;
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
