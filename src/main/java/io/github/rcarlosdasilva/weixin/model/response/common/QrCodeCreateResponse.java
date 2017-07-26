package io.github.rcarlosdasilva.weixin.model.response.common;

import com.google.gson.annotations.SerializedName;

/**
 * 带参数的二维码创建结果.
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class QrCodeCreateResponse {

  private String ticket;
  @SerializedName("expire_seconds")
  private long expireSeconds;
  private String url;

  /**
   * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码.
   * 
   * @return ticket
   */
  public String getTicket() {
    return ticket;
  }

  /**
   * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）.
   * 
   * @return expires
   */
  public long getExpireSeconds() {
    return expireSeconds;
  }

  /**
   * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片.
   * 
   * @return url
   */
  public String getUrl() {
    return url;
  }

}
