package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import com.google.gson.annotations.SerializedName;

public class MassFilter {

  @SerializedName("is_to_all")
  private boolean isToAll = false;
  @SerializedName("tag_id")
  private int tagId;

  public void setTagId(int tagId) {
    this.tagId = tagId;
  }

  public void setToAll(boolean isToAll) {
    this.isToAll = isToAll;
  }

}
