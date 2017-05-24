package io.github.rcarlosdasilva.weixin.model.response.open.auth;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class OpenPlatformAuthPreAuthCodeResponse implements Serializable {

  private static final long serialVersionUID = 4514336702159627804L;

  @SerializedName("pre_auth_code")
  private String preAuthCode;
  @SerializedName("expires_in")
  private String expiresIn;

  /**
   * 预授权码.
   * 
   * @return code
   */
  public String getPreAuthCode() {
    return preAuthCode;
  }

  /**
   * 有效期，为20分钟（返回600）。
   * 
   * @return expires in
   */
  public String getExpiresIn() {
    return expiresIn;
  }

}
