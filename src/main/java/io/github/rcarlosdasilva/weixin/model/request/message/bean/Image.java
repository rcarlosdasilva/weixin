package io.github.rcarlosdasilva.weixin.model.request.message.bean;

@SuppressWarnings("unused")
public class Image implements Message {

  private String mediaId;

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

}
