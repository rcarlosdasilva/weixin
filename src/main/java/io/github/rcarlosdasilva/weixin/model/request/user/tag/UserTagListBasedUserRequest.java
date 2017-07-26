package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取用户上的标签列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserTagListBasedUserRequest extends BasicWeixinRequest {

  @SerializedName("openid")
  private String openId;

  public UserTagListBasedUserRequest() {
    this.path = ApiAddress.URL_USER_TAG_LIST_BASE_USER;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
