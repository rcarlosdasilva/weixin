package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取素材列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
@SuppressWarnings("unused")
public class MediaListTimelessRequest extends BasicWeixinRequest {

  private String type;
  private int offset;
  private int count;

  public MediaListTimelessRequest() {
    this.path = ApiAddress.URL_MEDIA_TIMELESS_LIST;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public void setCount(int count) {
    this.count = count;
  }

}
