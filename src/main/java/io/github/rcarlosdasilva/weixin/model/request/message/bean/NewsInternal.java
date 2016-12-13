package io.github.rcarlosdasilva.weixin.model.request.message.bean;

@SuppressWarnings("unused")
public class NewsInternal implements Message {

  private String mediaId;

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
