package io.github.rcarlosdasilva.weixin.model.response.statistics.bean;

import com.google.gson.annotations.SerializedName;

public class NewsDetail {

  @SerializedName("stat_date")
  private String date;
  @SerializedName("target_user")
  private int targetUsers;
  @SerializedName("int_page_read_user")
  private int newsReadUsers;
  @SerializedName("int_page_read_count")
  private int newsReadCount;
  @SerializedName("ori_page_read_user")
  private int originReadUsers;
  @SerializedName("ori_page_read_count")
  private int originReadCount;
  @SerializedName("share_user")
  private int shareUsers;
  private int shareCount;
  @SerializedName("add_to_fav_user")
  private int collectUsers;
  @SerializedName("add_to_fav_count")
  private int collectCount;
  @SerializedName("int_page_from_session_read_user")
  private int fromSessionReadUsers;
  @SerializedName("int_page_from_session_read_count")
  private int fromSessionReadCount;
  @SerializedName("int_page_from_hist_msg_read_user")
  private int fromHistoryReadUsers;
  @SerializedName("int_page_from_hist_msg_read_count")
  private int fromHistoryReadCount;
  @SerializedName("int_page_from_feed_read_user")
  private int fromMomentsReadUsers;
  @SerializedName("int_page_from_feed_read_count")
  private int fromMomentsReadCount;
  @SerializedName("int_page_from_friends_read_user")
  private int fromRepostReadUsers;
  @SerializedName("int_page_from_friends_read_count")
  private int fromRepostReadCount;
  @SerializedName("int_page_from_other_read_user")
  private int fromOtherReadUsers;
  @SerializedName("int_page_from_other_read_count")
  private int fromOtherReadCount;
  @SerializedName("feed_share_from_session_user")
  private int sessionToMomentsUsers;
  @SerializedName("feed_share_from_session_cnt")
  private int sessionToMomentsCount;
  @SerializedName("feed_share_from_feed_user")
  private int momentsToMomentsUsers;
  @SerializedName("feed_share_from_feed_cnt")
  private int momentsToMomentsCount;
  @SerializedName("feed_share_from_other_user")
  private int otherToMomentsUsers;
  @SerializedName("feed_share_from_other_cnt")
  private int otherToMomentsCount;

  /**
   * 统计的日期，在getarticletotal接口中，ref_date指的是文章群发出日期， 而stat_date是数据统计日期.
   */
  public String getDate() {
    return date;
  }

  /**
   * 送达人数，一般约等于总粉丝数（需排除黑名单或其他异常情况下无法收到消息的粉丝）.
   */
  public int getTargetUsers() {
    return targetUsers;
  }

  /**
   * 图文页（点击群发图文卡片进入的页面）的阅读人数.
   */
  public int getNewsReadUsers() {
    return newsReadUsers;
  }

  /**
   * 图文页的阅读次数.
   */
  public int getNewsReadCount() {
    return newsReadCount;
  }

  /**
   * 原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0.
   */
  public int getOriginReadUsers() {
    return originReadUsers;
  }

  /**
   * 原文页的阅读次数.
   */
  public int getOriginReadCount() {
    return originReadCount;
  }

  /**
   * 分享的人数.
   */
  public int getShareUsers() {
    return shareUsers;
  }

  /**
   * 分享的次数.
   */
  public int getShareCount() {
    return shareCount;
  }

  /**
   * 收藏的人数.
   */
  public int getCollectUsers() {
    return collectUsers;
  }

  /**
   * 收藏的次数.
   */
  public int getCollectCount() {
    return collectCount;
  }

  /**
   * 公众号会话阅读人数.
   */
  public int getFromSessionReadUsers() {
    return fromSessionReadUsers;
  }

  /**
   * 公众号会话阅读次数.
   */
  public int getFromSessionReadCount() {
    return fromSessionReadCount;
  }

  /**
   * 历史消息页阅读人数.
   */
  public int getFromHistoryReadUsers() {
    return fromHistoryReadUsers;
  }

  /**
   * 历史消息页阅读次数.
   */
  public int getFromHistoryReadCount() {
    return fromHistoryReadCount;
  }

  /**
   * 朋友圈阅读人数.
   */
  public int getFromMomentsReadUsers() {
    return fromMomentsReadUsers;
  }

  /**
   * 朋友圈阅读次数.
   */
  public int getFromMomentsReadCount() {
    return fromMomentsReadCount;
  }

  /**
   * 好友转发阅读人数.
   */
  public int getFromRepostReadUsers() {
    return fromRepostReadUsers;
  }

  /**
   * 好友转发阅读次数.
   */
  public int getFromRepostReadCount() {
    return fromRepostReadCount;
  }

  /**
   * 其他场景阅读人数.
   */
  public int getFromOtherReadUsers() {
    return fromOtherReadUsers;
  }

  /**
   * 其他场景阅读次数.
   */
  public int getFromOtherReadCount() {
    return fromOtherReadCount;
  }

  /**
   * 公众号会话转发朋友圈人数.
   */
  public int getSessionToMomentsUsers() {
    return sessionToMomentsUsers;
  }

  /**
   * 公众号会话转发朋友圈次数.
   */
  public int getSessionToMomentsCount() {
    return sessionToMomentsCount;
  }

  /**
   * 朋友圈转发朋友圈人数.
   */
  public int getMomentsToMomentsUsers() {
    return momentsToMomentsUsers;
  }

  /**
   * 朋友圈转发朋友圈次数.
   */
  public int getMomentsToMomentsCount() {
    return momentsToMomentsCount;
  }

  /**
   * 其他场景转发朋友圈人数.
   */
  public int getOtherToMomentsUsers() {
    return otherToMomentsUsers;
  }

  /**
   * 其他场景转发朋友圈次数.
   */
  public int getOtherToMomentsCount() {
    return otherToMomentsCount;
  }

}
