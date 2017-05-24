package io.github.rcarlosdasilva.weixin.model.request.user.group;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 移动用户到组请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserGroupMoveMemberRequest extends BasicWeixinRequest {

  @SerializedName("openid")
  private String openId;
  @SerializedName("to_groupid")
  private int toGroupId;

  public UserGroupMoveMemberRequest() {
    this.path = ApiAddress.URL_USER_GROUP_MOVE_MEMBER;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public void setToGroupId(int toGroupId) {
    this.toGroupId = toGroupId;
  }

}
