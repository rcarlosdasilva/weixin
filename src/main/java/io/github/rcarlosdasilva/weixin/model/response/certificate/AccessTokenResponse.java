package io.github.rcarlosdasilva.weixin.model.response.certificate;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.Convention;

public class AccessTokenResponse {

  @SerializedName("access_token")
  private String accessToken;
  @SerializedName("expires_in")
  private int expiresIn;
  private long expireAt;

  /**
   * 获取到的凭证.
   * 
   * @return access_token
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * 凭证有效时间,单位:秒.
   * 
   * @return expires
   */
  public long getExpiresIn() {
    return expiresIn;
  }

  /**
   * 更新准确的过期时间，默认提前180秒过期.
   */
  public void updateExpireAt() {
    this.expireAt = (this.expiresIn - Convention.AHEAD_OF_EXPIRED_SECONDS) * 1000
        + System.currentTimeMillis();
  }

  /**
   * 是否过期或无用.
   * 
   * @return is expired
   */
  public boolean isExpired() {
    return this.expireAt < System.currentTimeMillis() || Strings.isNullOrEmpty(this.accessToken);
  }

}
