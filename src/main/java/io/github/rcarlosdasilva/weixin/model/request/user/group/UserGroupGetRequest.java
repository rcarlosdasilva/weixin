package io.github.rcarlosdasilva.weixin.model.request.user.group;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取用户所在组请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserGroupGetRequest extends BasicWeixinRequest {

  @SerializedName("openid")
  private String openId;

  public UserGroupGetRequest() {
    this.path = ApiAddress.URL_USER_GROUP_GET;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

}
