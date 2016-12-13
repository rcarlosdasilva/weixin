package io.github.rcarlosdasilva.weixin.model.request.message.bean;

@SuppressWarnings("unused")
public class Card implements Message {

  private String cardId;

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

}
