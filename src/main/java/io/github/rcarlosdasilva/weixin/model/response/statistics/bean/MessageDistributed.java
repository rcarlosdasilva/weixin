package io.github.rcarlosdasilva.weixin.model.response.statistics.bean;

import com.google.gson.annotations.SerializedName;

public class MessageDistributed {

  @SerializedName("ref_date")
  private String date;
  private int countInterval;
  @SerializedName("msg_user")
  private int users;

  /**
   * 数据的日期.
   * 
   * @return time
   */
  public String getDate() {
    return date;
  }

  /**
   * 当日发送消息量分布的区间，0代表 “0”，1代表“1-5”，2代表“6-10”，3代表“10次以上”.
   * 
   * @return interval
   */
  public int getCountInterval() {
    return countInterval;
  }

  /**
   * 上行发送了（向公众号发送了）消息的用户数.
   * 
   * @return count
   */
  public int getUsers() {
    return users;
  }

}
