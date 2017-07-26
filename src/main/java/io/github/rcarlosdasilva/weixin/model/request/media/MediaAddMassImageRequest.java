package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 上传图文消息内的图片获取URL请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MediaAddMassImageRequest extends BasicWeixinRequest {

  public MediaAddMassImageRequest() {
    this.path = ApiAddress.URL_MEDIA_MASS_ADD_IMAGE;
  }

}
