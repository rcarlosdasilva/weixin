package io.github.rcarlosdasilva.weixin.model.request.media;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

public class MediaGetTemporaryWithHqAudioRequest extends BasicWeixinRequest {

  private String mediaId;

  public MediaGetTemporaryWithHqAudioRequest() {
    this.path = ApiAddress.URL_MEDIA_TEMPORARY_GET_HQ_AUDIO;
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
