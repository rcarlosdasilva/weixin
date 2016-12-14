package io.github.rcarlosdasilva.weixin.model.response.common;

import com.google.gson.annotations.SerializedName;

public class ShortUrlResponse {

  @SerializedName("short_url")
  private String shortUrl;

  /**
   * 短连接.
   * 
   * @return url
   */
  public String getShortUrl() {
    return shortUrl;
  }

}
