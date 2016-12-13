package io.github.rcarlosdasilva.weixin.model.response.custom;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomMessageRecord;

public class CustomMessageRecordsResponse {

  @SerializedName("number")
  private int size;
  @SerializedName("msgid")
  private long messageId;
  @SerializedName("recordlist")
  private List<CustomMessageRecord> records;

  /** 本次返回聊天记录条数. */
  public int getSize() {
    return size;
  }

  /** 本次聊天记录结束的信息id，将此id放入下次请求的messageId中，以继续获取后续的记录. */
  public long getMessageId() {
    return messageId;
  }

  /** 聊天记录列表. */
  public List<CustomMessageRecord> getRecords() {
    return records;
  }

}
