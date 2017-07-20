package io.github.rcarlosdasilva.weixin.model.response.menu.bean.complate;

import com.google.gson.annotations.SerializedName;

public class Button {

  private String name;
  private String type;
  private String key;
  private String value;
  private String url;
  @SerializedName("media_id")
  private String mediaId;
  private String appid;
  @SerializedName("pagepath")
  private String path;
  @SerializedName("sub_button")
  private SubButton subButton;
  @SerializedName("news_info")
  private MediaCollection mediaCollection;

  /**
   * 菜单名称.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * 菜单的类型，公众平台官网上能够设置的菜单类型有view（跳转网页）、text（返回文本，下同）、img、photo、video、
   * voice。使用API设置的则有8种，详见《自定义菜单创建接口》.
   * 
   * @return type
   */
  public String getType() {
    return type;
  }

  /**
   * value、url、key等字段.
   * 
   * <p>
   * 对于不同的菜单类型，value的值意义不同。
   * 
   * <p>
   * 官网上设置的自定义菜单： <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Text:保存文字到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Img、voice：保存mediaID到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Video：保存视频下载链接到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;News：保存图文消息到news_info， 同时保存mediaID到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;View：保存链接到url。
   * 
   * <p>
   * 使用API设置的自定义菜单： <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;click、scancode_push、scancode_waitmsg、 pic_sysphoto、
   * pic_photo_or_album、pic_weixin、location_select：保存值到key； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;view：保存链接到url
   * 
   * @return key
   */
  public String getKey() {
    return key;
  }

  /**
   * value、url、key等字段.
   * 
   * <p>
   * 对于不同的菜单类型，value的值意义不同。
   * 
   * <p>
   * 官网上设置的自定义菜单： <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Text:保存文字到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Img、voice：保存mediaID到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Video：保存视频下载链接到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;News：保存图文消息到news_info， 同时保存mediaID到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;View：保存链接到url。
   * 
   * <p>
   * 使用API设置的自定义菜单： <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;click、scancode_push、scancode_waitmsg、 pic_sysphoto、
   * pic_photo_or_album、pic_weixin、location_select：保存值到key； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;view：保存链接到url
   * 
   * @return value
   */
  public String getValue() {
    return value;
  }

  /**
   * value、url、key等字段.
   * 
   * <p>
   * 对于不同的菜单类型，value的值意义不同。
   * 
   * <p>
   * 官网上设置的自定义菜单： <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Text:保存文字到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Img、voice：保存mediaID到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;Video：保存视频下载链接到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;News：保存图文消息到news_info， 同时保存mediaID到value； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;View：保存链接到url。
   * 
   * <p>
   * 使用API设置的自定义菜单： <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;click、scancode_push、scancode_waitmsg、 pic_sysphoto、
   * pic_photo_or_album、pic_weixin、location_select：保存值到key； <br>
   * &nbsp;&nbsp;&nbsp;&nbsp;view：保存链接到url
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

  public SubButton getSubButton() {
    return subButton;
  }

  /**
   * 图文消息的信息.
   * 
   * @return {@link MediaCollection}
   */
  public MediaCollection getMediaCollection() {
    return mediaCollection;
  }

}