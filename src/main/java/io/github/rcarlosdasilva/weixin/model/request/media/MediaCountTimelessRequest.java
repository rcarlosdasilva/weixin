package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取素材总数请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MediaCountTimelessRequest extends BasicWeixinRequest {

  public MediaCountTimelessRequest() {
    this.path = ApiAddress.URL_MEDIA_TIMELESS_COUNT;
  }

}
