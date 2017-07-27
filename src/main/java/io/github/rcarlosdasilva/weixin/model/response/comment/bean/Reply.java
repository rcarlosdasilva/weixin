package io.github.rcarlosdasilva.weixin.model.response.comment.bean;

import com.google.gson.annotations.SerializedName;

public class Reply {

  private String content;
  @SerializedName("create_time")
  private long time;

  /**
   * 作者回复内容
   * 
   * @return content
   */
  public String getContent() {
    return content;
  }

  /**
   * 作者回复时间
   * 
   * @return create_time
   */
  public long getTime() {
    return time;
  }

}
