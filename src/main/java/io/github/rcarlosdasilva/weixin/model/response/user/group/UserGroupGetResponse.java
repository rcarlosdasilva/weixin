package io.github.rcarlosdasilva.weixin.model.response.user.group;

import com.google.gson.annotations.SerializedName;

public class UserGroupGetResponse {

  @SerializedName("groupid")
  private int groupId;

  /**
   * 组id.
   */
  public int getGroupId() {
    return groupId;
  }

}
