package io.github.rcarlosdasilva.weixin.model.request.user;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.Language;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取网页授权下用户信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserInfoByWebAuthorizeRequest extends BasicRequest {

  private String accessToken;
  private String openId;
  private Language language;

  public UserInfoByWebAuthorizeRequest() {
    this.path = ApiAddress.URL_USER_INFO_BY_WEB_AUTHORIZE;
    this.language = Language.ZH_CN;
  }

  /**
   * 网页授权Token.
   * 
   * @param accessToken
   *          access_token
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   * 用户OpenId.
   * 
   * @param openId
   *          open_id
   */
  public void setOpenId(String openId) {
    this.openId = openId;
  }

  /**
   * 语言.
   * 
   * @param language
   *          语言
   */
  public void setLang(Language language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&openid=").append(this.openId).append("&lang=").append(this.language).toString();
  }

}
