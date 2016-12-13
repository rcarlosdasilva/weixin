package io.github.rcarlosdasilva.weixin.model.response.statistics.bean;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.dictionary.StatisticsUserSource;

public class UserSummary {

  @SerializedName("ref_date")
  private String date;
  @SerializedName("user_source")
  private int source;
  @SerializedName("new_user")
  private int newUsers;
  @SerializedName("cancel_user")
  private int cancelUsers;
  @SerializedName("cumulate_user")
  private int totalUsers;

  /**
   * 数据的日期.
   */
  public String getDate() {
    return date;
  }

  /**
   * 用户的渠道，数值代表的含义如下： 0代表其他合计 1代表公众号搜索 17代表名片分享 30代表扫描二维码 43代表图文页右上角菜单
   * 51代表支付后关注（在支付完成页） 57代表图文页内公众号名称 75代表公众号文章广告 78代表朋友圈广告.
   */
  public StatisticsUserSource getSource() {
    return StatisticsUserSource.byCode(source);
  }

  /**
   * 新增的用户数量.
   */
  public int getNewUsers() {
    return newUsers;
  }

  /**
   * 取消关注的用户数量，new_user减去cancel_user即为净增用户数量.
   */
  public int getCancelUsers() {
    return cancelUsers;
  }

  /**
   * 总用户量.
   */
  public int getTotalUsers() {
    return totalUsers;
  }

}
