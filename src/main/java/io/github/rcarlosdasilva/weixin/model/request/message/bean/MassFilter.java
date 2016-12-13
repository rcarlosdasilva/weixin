package io.github.rcarlosdasilva.weixin.model.request.message.bean;

@SuppressWarnings("unused")
public class MassFilter {

  private boolean isToAll = false;
  private int tagId;

  public void setTagId(int tagId) {
    this.tagId = tagId;
  }

  public void setToAll(boolean isToAll) {
    this.isToAll = isToAll;
  }

}
