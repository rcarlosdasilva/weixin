package io.github.rcarlosdasilva.weixin.model.response.certificate;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

public class JsTicketResponse {

  @SerializedName("ticket")
  private String jsTicket;
  @SerializedName("expires_in")
  private int expiresIn;
  private long expireAt;

  /**
   * 获取到JS票据.
   * 
   * @return js_ticket
   */
  public String getJsTicket() {
    return jsTicket;
  }

  /**
   * 票据有效时间,单位:秒.
   * 
   * @return expires
   */
  public long getExpiresIn() {
    return expiresIn;
  }

  /**
   * 更新准确的过期时间.
   */
  public void updateExpireAt() {
    this.expireAt = (this.expiresIn - 30) * 1000 + System.currentTimeMillis();
  }

  /**
   * 是否过期.
   * 
   * @return is expired
   */
  public boolean expiredOrUseless() {
    return this.expireAt < System.currentTimeMillis() || Strings.isNullOrEmpty(this.jsTicket);
  }

}
