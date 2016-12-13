package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.bean.UserTag;

/**
 * 更新标签请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagUpdateRequest extends BasicRequest {

  private UserTag tag = new UserTag();

  public UserTagUpdateRequest() {
    this.path = ApiAddress.URL_USER_TAG_UPDATE;
  }

  /**
   * 标签id.
   */
  public void setTagId(int id) {
    this.tag.setId(id);
  }

  /**
   * 标签名.
   */
  public void setTagName(String name) {
    this.tag.setName(name);
  }

}
