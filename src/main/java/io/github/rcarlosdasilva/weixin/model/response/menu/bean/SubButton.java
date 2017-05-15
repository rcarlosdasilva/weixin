package io.github.rcarlosdasilva.weixin.model.response.menu.bean;

import com.google.gson.annotations.SerializedName;

public class SubButton {

  private String name;
  private String type;
  private String key;
  private String url;
  @SerializedName("media_id")
  private String mediaId;
  private String appid;
  @SerializedName("pagepath")
  private String path;

  /**
   * 菜单标题.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * 菜单的响应动作类型.
   * 
   * @return type
   */
  public String getType() {
    return type;
  }

  /**
   * 菜单KEY值，用于消息接口推送.
   * 
   * @return key
   */
  public String getKey() {
    return key;
  }

  /**
   * 网页链接，用户点击菜单可打开链接.
   * 
   * @return url
   */
  public String getUrl() {
    return url;
  }

  /**
   * 调用新增永久素材接口返回的合法media_id.
   * 
   * @return media id
   */
  public String getMediaId() {
    return mediaId;
  }

  public String getAppid() {
    return appid;
  }

  public String getPath() {
    return path;
  }

}