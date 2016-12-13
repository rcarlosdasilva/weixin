package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 转换视频media_id请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@SuppressWarnings("unused")
public class MediaTransformMassVideoRequest extends BasicRequest {

  private String mediaId;
  private String title;
  private String description;

  public MediaTransformMassVideoRequest() {
    this.path = ApiAddress.URL_MEDIA_MASS_TRANSFORM_VIDEO;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
