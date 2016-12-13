package io.github.rcarlosdasilva.weixin.model.notification.bean;

/**
 * 微信被动回复消息附加内容
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class ResponseAdditionalInfo {

  private String content;
  private String mediaId;
  private String mediaThumbId;
  private String title;
  private String description;
  private String url;
  private String otherUrl;

  public ResponseAdditionalInfo() {
  }

  /**
   * 图文消息用.
   */
  public ResponseAdditionalInfo(String title, String description, String url, String otherUrl) {
    this.title = title;
    this.description = description;
    this.url = url;
    this.otherUrl = otherUrl;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getMediaThumbId() {
    return mediaThumbId;
  }

  public void setMediaThumbId(String mediaThumbId) {
    this.mediaThumbId = mediaThumbId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getOtherUrl() {
    return otherUrl;
  }

  public void setOtherUrl(String otherUrl) {
    this.otherUrl = otherUrl;
  }

}
