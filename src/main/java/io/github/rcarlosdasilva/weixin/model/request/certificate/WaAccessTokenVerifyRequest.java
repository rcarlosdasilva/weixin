package io.github.rcarlosdasilva.weixin.model.request.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 网页授权Token验证请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class WaAccessTokenVerifyRequest extends BasicRequest {

  private String accessToken;
  private String openId;

  public WaAccessTokenVerifyRequest() {
    this.path = ApiAddress.URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_VERIFY;
  }

  /**
   * Token.
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   * OpenId.
   */
  public void setOpenId(String openId) {
    this.openId = openId;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&openid=").append(this.openId).toString();
  }

}
