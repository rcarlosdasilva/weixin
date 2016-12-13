package io.github.rcarlosdasilva.weixin.model.response.statistics.bean;

import com.google.gson.annotations.SerializedName;

public class InterfaceSummary {

  @SerializedName("ref_date")
  private String date;
  @SerializedName("ref_hour")
  private int hour;
  private int callbackCount;
  private int failCount;
  private int totalTimeCost;
  private int maxTimeCost;

  /**
   * 数据的日期.
   */
  public String getDate() {
    return date;
  }

  /**
   * 数据的小时.
   */
  public int getHour() {
    return hour;
  }

  /**
   * 通过服务器配置地址获得消息后，被动回复用户消息的次数.
   */
  public int getCallbackCount() {
    return callbackCount;
  }

  /**
   * 上述动作的失败次数.
   */
  public int getFailCount() {
    return failCount;
  }

  /**
   * 总耗时，除以callback_count即为平均耗时.
   */
  public int getTotalTimeCost() {
    return totalTimeCost;
  }

  /**
   * 最大耗时.
   */
  public int getMaxTimeCost() {
    return maxTimeCost;
  }

}
