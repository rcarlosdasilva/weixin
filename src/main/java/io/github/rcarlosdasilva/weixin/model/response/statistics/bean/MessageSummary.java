package io.github.rcarlosdasilva.weixin.model.response.statistics.bean;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.dictionary.StatisticsMessageType;

public class MessageSummary {

  @SerializedName("ref_date")
  private String date;
  @SerializedName("ref_hour")
  private int hour;
  @SerializedName("msg_type")
  private int type;
  @SerializedName("msg_user")
  private int users;
  @SerializedName("msg_count")
  private int count;

  /**
   * 数据的日期.
   */
  public String getDate() {
    return date;
  }

  /**
   * 数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时.
   */
  public int getHour() {
    return hour;
  }

  /**
   * 消息类型，代表含义如下： 1代表文字 2代表图片 3代表语音 4代表视频 6代表第三方应用消息（链接消息）.
   */
  public StatisticsMessageType getType() {
    return StatisticsMessageType.byCode(type);
  }

  /**
   * 上行发送了（向公众号发送了）消息的用户数.
   */
  public int getUsers() {
    return users;
  }

  /**
   * 上行发送了消息的消息总数.
   */
  public int getCount() {
    return count;
  }

}
