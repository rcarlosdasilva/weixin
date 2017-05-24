package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取永久素材请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@SuppressWarnings("unused")
public class MediaGetTimelessRequest extends BasicWeixinRequest {

  private String mediaId;

  public MediaGetTimelessRequest() {
    this.path = ApiAddress.URL_MEDIA_TIMELESS_GET;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
