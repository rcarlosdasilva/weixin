package io.github.rcarlosdasilva.weixin.model.response.user.group;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.user.group.bean.UserGroup;

public class UserGroupListResponse {

  List<UserGroup> groups;

  /**
   * 组列表.
   */
  public List<UserGroup> getGroups() {
    return groups;
  }

}
