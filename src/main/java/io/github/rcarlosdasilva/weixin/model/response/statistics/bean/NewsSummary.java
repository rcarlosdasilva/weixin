package io.github.rcarlosdasilva.weixin.model.response.statistics.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.dictionary.StatisticsNewsShareScene;
import io.github.rcarlosdasilva.weixin.common.dictionary.StatisticsNewsUserSource;

public class NewsSummary {

  @SerializedName("ref_date")
  private String date;
  @SerializedName("ref_hour")
  private int hour;
  @SerializedName("msgid")
  private String messageId;
  private String title;
  private List<NewsDetail> details;
  @SerializedName("user_source")
  private int source;
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
  @SerializedName("share_scene")
  private int scene;
  @SerializedName("add_to_fav_user")
  private int collectUsers;
  @SerializedName("add_to_fav_count")
  private int collectCount;

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
   * 请注意：这里的msgid实际上是由msgid（图文消息id，这也就是群发接口调用后返回的msg_data_id）和index（消息次序索引）组成，
   * 例如12003_3， 其中12003是msgid，即一次群发的消息的id；
   * 3为index，假设该次群发的图文消息共5个文章（因为可能为多图文），3表示5个中的第3个.
   */
  public String getMessageId() {
    return messageId;
  }

  /**
   * 图文消息的标题.
   */
  public String getTitle() {
    return title;
  }

  /**
   * 获取详细信息.
   */
  public List<NewsDetail> getDetails() {
    return details;
  }

  /**
   * 在获取图文阅读分时数据时才有该字段，代表用户从哪里进入来阅读该图文。0:会话;1.好友;2.朋友圈;3.腾讯微博;4.历史消息页;5.其他.
   */
  public StatisticsNewsUserSource getSource() {
    return StatisticsNewsUserSource.byCode(source);
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
   * 分享的场景 1代表好友转发 2代表朋友圈 3代表腾讯微博 255代表其他.
   */
  public StatisticsNewsShareScene getScene() {
    return StatisticsNewsShareScene.byCode(scene);
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

}
