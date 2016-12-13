package io.github.rcarlosdasilva.weixin.model.request.user.group;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 批量移动用户到组请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserGroupMoveMemberBatchRequest extends BasicRequest {

  @SerializedName("openid_list")
  private List<String> openIds;
  @SerializedName("to_groupid")
  private int toGroupId;

  public UserGroupMoveMemberBatchRequest() {
    this.path = ApiAddress.URL_USER_GROUP_MOVE_MEMBER_BATCH;
  }

  /**
   * 用户OpenIds.
   */
  public void setOpenIds(List<String> openIds) {
    this.openIds = openIds;
  }

  /**
   * 组id.
   */
  public void setToGroupId(int toGroupId) {
    this.toGroupId = toGroupId;
  }

}
