package io.github.rcarlosdasilva.weixin.model.request.message.bean;

@SuppressWarnings("unused")
public class Text implements Message {

  private String content;

  public void setContent(String content) {
    this.content = content;
  }

}
