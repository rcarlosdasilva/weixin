package io.github.rcarlosdasilva.weixin.model.response.user.tag;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.user.tag.bean.UserTag;

public class UserTagListResponse {

  private List<UserTag> tags;

  /**
   * 标签列表.
   */
  public List<UserTag> getTags() {
    return tags;
  }

}
