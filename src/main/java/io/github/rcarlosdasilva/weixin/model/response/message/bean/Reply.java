package io.github.rcarlosdasilva.weixin.model.response.message.bean;

import com.google.gson.annotations.SerializedName;

public class Reply {

  private String type;
  private String content;
  @SerializedName("news_info")
  private NewsInfoList newsList;

  /**
   * 自动回复的类型。关注后自动回复和消息自动回复的类型仅支持文本（text）、图片（img）、
   * 语音（voice）、视频（video），关键词自动回复则还多了图文消息（news）.
   * 
   * @return type
   */
  public String getType() {
    return type;
  }

  /**
   * 对于文本类型，content是文本内容，对于图文、图片、语音、视频类型，content是mediaID.
   * 
   * @return content
   */
  public String getContent() {
    return content;
  }

  /**
   * 图文消息的信息.
   * 
   * @return {@link NewsInfoList}
   */
  public NewsInfoList getNewsList() {
    return newsList;
  }

}
