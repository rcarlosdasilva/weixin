package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.bean.UserTag;

/**
 * 删除标签请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagDeleteRequest extends BasicRequest {

  private UserTag tag = new UserTag();

  public UserTagDeleteRequest() {
    this.path = ApiAddress.URL_USER_TAG_DELETE;
  }

  public void setTagId(int id) {
    this.tag.setId(id);
  }

}
