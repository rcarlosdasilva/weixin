package io.github.rcarlosdasilva.weixin.model.response.custom;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomSession;

public class CustomSessionWaitingsResponse {

  private int count;
  @SerializedName("waitcaselist")
  private List<CustomSession> waitings;

  /**
   * 未接入会话数量.
   * 
   * @return count
   */
  public int getCount() {
    return count;
  }

  /**
   * 未接入会话列表，最多返回100条数据，按照来访顺序.
   * 
   * @return list of {@link CustomSession}
   */
  public List<CustomSession> getWaitings() {
    return waitings;
  }

}
