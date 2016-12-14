package io.github.rcarlosdasilva.weixin.model.response.certificate;

import com.google.gson.annotations.SerializedName;

public class WaAccessTokenResponse {

  @SerializedName("access_token")
  private String accessToken;
  @SerializedName("expires_in")
  private int expiresIn;
  private long expireAt;
  @SerializedName("refresh_token")
  private String refreshToken;
  @SerializedName("openid")
  private String openId;
  private String scope;

  /**
   * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同.
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
  public int getExpiresIn() {
    return expiresIn;
  }

  /**
   * 用户刷新access_token.
   * 
   * @return refresh_token
   */
  public String getRefreshToken() {
    return refreshToken;
  }

  /**
   * 用户唯一标识.
   * 
   * @return open_id
   */
  public String getOpenId() {
    return openId;
  }

  /**
   * 用户授权的作用域，使用逗号（,）分隔.
   * 
   * @return scope
   */
  public String getScope() {
    return scope;
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
  public boolean expired() {
    return this.expireAt < System.currentTimeMillis();
  }

}
