package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.bean.UserTag;

/**
 * 删除标签请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserTagDeleteRequest extends BasicWeixinRequest {

  private UserTag tag = new UserTag();

  public UserTagDeleteRequest() {
    this.path = ApiAddress.URL_USER_TAG_DELETE;
  }

  public void setTagId(int id) {
    this.tag.setId(id);
  }

}
