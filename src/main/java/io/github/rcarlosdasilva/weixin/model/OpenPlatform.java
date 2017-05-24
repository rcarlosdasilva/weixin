package io.github.rcarlosdasilva.weixin.model;

import java.io.Serializable;

public class OpenPlatform implements Serializable {

  private static final long serialVersionUID = -4067025457096279300L;

  private String appId;
  private String appSecret;
  private String token;
  private String aesKey;

  public OpenPlatform(String appId, String appSecret, String token, String aesKey) {
    super();
    this.appId = appId;
    this.appSecret = appSecret;
    this.token = token;
    this.aesKey = aesKey;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAesKey() {
    return aesKey;
  }

  public void setAesKey(String aesKey) {
    this.aesKey = aesKey;
  }

}
