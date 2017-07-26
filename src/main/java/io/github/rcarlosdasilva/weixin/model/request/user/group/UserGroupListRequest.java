package io.github.rcarlosdasilva.weixin.model.request.user.group;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取用户组列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserGroupListRequest extends BasicWeixinRequest {

  public UserGroupListRequest() {
    this.path = ApiAddress.URL_USER_GROUP_LIST;
  }

}
