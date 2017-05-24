package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取素材文件请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MediaGetTemporaryRequest extends BasicWeixinRequest {

  private String mediaId;

  public MediaGetTemporaryRequest() {
    this.path = ApiAddress.URL_MEDIA_TEMPORARY_GET;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&media_id=").append(this.mediaId).toString();
  }

}
