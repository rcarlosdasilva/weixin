package io.github.rcarlosdasilva.weixin.model.response.media;

import java.io.InputStream;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.media.bean.Article;

public class MediaGetTimelessResponse {

  private byte[] stream;
  private String title;
  private String description;
  @SerializedName("down_url")
  private String downloadUrl;
  @SerializedName("news_item")
  private List<Article> articles;

  public void setStream(byte[] stream) {
    this.stream = stream;
  }

  /**
   * 如果是除视频或图文之外的其他类型的素材消息，则响应的直接为素材的内容，开发者可以自行保存为文件.
   * 
   * @return {@link InputStream}
   */
  public byte[] getStream() {
    return stream;
  }

  /**
   * 如果返回的是视频消息素材，视频标题.
   * 
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * 如果返回的是视频消息素材，视频描述.
   * 
   * @return description
   */
  public String getDescription() {
    return description;
  }

  /**
   * 如果返回的是视频消息素材，视频下载地址.
   * 
   * @return url
   */
  public String getDownloadUrl() {
    return downloadUrl;
  }

  /**
   * 如果请求的素材为图文消息，获取图文列表.
   * 
   * @return list of {@link Article}
   */
  public List<Article> getArticles() {
    return articles;
  }

}
