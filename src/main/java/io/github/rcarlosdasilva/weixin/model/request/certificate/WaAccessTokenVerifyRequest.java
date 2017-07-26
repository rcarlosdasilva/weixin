package io.github.rcarlosdasilva.weixin.model.request.certificate;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 网页授权Token验证请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class WaAccessTokenVerifyRequest extends BasicWeixinRequest {

  private String accessToken;
  private String openId;

  public WaAccessTokenVerifyRequest() {
    this.path = ApiAddress.URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_VERIFY;
  }

  /**
   * Token.
   * 
   * @param accessToken
   *          access_token
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   * OpenId.
   * 
   * @param openId
   *          open_id
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
