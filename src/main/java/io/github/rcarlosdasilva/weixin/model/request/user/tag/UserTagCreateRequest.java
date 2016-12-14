package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.bean.UserTag;

/**
 * 创建标签请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagCreateRequest extends BasicRequest {

  private UserTag tag = new UserTag();

  public UserTagCreateRequest() {
    this.path = ApiAddress.URL_USER_TAG_CREATE;
  }

  public void setTagName(String name) {
    this.tag.setName(name);
  }

}
