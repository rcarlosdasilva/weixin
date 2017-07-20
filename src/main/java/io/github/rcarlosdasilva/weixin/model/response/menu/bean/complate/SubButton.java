package io.github.rcarlosdasilva.weixin.model.response.menu.bean.complate;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SubButton {

  @SerializedName("list")
  private List<Button> buttons;

  public List<Button> getButtons() {
    return buttons;
  }

}