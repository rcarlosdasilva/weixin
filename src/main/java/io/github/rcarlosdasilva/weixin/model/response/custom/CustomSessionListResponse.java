package io.github.rcarlosdasilva.weixin.model.response.custom;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomSession;

public class CustomSessionListResponse {

  @SerializedName("sessionlist")
  private List<CustomSession> sessions;

  /**
   * 会话列表.
   * 
   * @return list of {@link CustomSession}
   */
  public List<CustomSession> getSessions() {
    return sessions;
  }

}
