package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import com.google.gson.annotations.SerializedName;

public class Card implements Message {

  @SerializedName("card_id") 
  private String cardId;

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

}
