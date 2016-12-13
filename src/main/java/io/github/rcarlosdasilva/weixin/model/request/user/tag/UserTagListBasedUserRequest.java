package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取用户上的标签列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagListBasedUserRequest extends BasicRequest {

  @SerializedName("openid")
  private String openId;

  public UserTagListBasedUserRequest() {
    this.path = ApiAddress.URL_USER_TAG_LIST_BASE_USER;
  }

  /**
   * 用户OpenId.
   */
  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
