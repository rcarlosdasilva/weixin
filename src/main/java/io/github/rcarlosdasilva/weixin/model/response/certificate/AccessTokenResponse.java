package io.github.rcarlosdasilva.weixin.model.response.certificate;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse {

  @SerializedName("access_token")
  private String accessToken;
  @SerializedName("expires_in")
  private int expiresIn;
  private long expireAt;

  /** 获取到的凭证. */
  public String getAccessToken() {
    return accessToken;
  }

  /** 凭证有效时间,单位:秒. */
  public long getExpiresIn() {
    return expiresIn;
  }

  /** 更新准确的过期时间. */
  public void updateExpireAt() {
    this.expireAt = (this.expiresIn - 30) * 1000 + System.currentTimeMillis();
  }

  /** 是否过期或无用. */
  public boolean expiredOrUseless() {
    return this.expireAt < System.currentTimeMillis() || Strings.isNullOrEmpty(this.accessToken);
  }

}
