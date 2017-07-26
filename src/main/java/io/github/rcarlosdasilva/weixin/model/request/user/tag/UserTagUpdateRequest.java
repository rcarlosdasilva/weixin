package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.bean.UserTag;

/**
 * 更新标签请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserTagUpdateRequest extends BasicWeixinRequest {

  private UserTag tag = new UserTag();

  public UserTagUpdateRequest() {
    this.path = ApiAddress.URL_USER_TAG_UPDATE;
  }

  public void setTagId(int id) {
    this.tag.setId(id);
  }

  public void setTagName(String name) {
    this.tag.setName(name);
  }

}
