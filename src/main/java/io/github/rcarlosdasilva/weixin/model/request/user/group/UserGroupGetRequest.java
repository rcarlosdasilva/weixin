package io.github.rcarlosdasilva.weixin.model.request.user.group;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取用户所在组请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserGroupGetRequest extends BasicRequest {

  @SerializedName("openid")
  private String openId;

  public UserGroupGetRequest() {
    this.path = ApiAddress.URL_USER_GROUP_GET;
  }

  /**
   * 用户OpenId.
   */
  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
