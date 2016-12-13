package io.github.rcarlosdasilva.weixin.model.response.user;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.user.bean.User;

public class UserListResponse {

  @SerializedName("user_info_list")
  private List<User> userList;

  /**
   * 用户列表.
   */
  public List<User> getUserList() {
    return userList;
  }

}
