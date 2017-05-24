package io.github.rcarlosdasilva.weixin.model.request.user;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.Language;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取用户信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserInfoRequest extends BasicWeixinRequest {

  @SerializedName("openid")
  private String openId;
  @SerializedName("lang")
  private Language language;

  /**
   * 批量请求用户信息时使用，无需Token.
   */
  public UserInfoRequest() {
    this.path = ApiAddress.URL_USER_INFO;
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
  public void setLanguage(Language language) {
    this.language = language;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&openid=").append(this.openId).append("&lang=").append(this.language).toString();
  }

}
