package io.github.rcarlosdasilva.weixin.model.request.user.group;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 删除用户组请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserGroupDeleteRequest extends BasicWeixinRequest {

  private Map<String, Integer> group = Maps.newHashMap();

  public UserGroupDeleteRequest() {
    this.path = ApiAddress.URL_USER_GROUP_DELETE;
  }

  public void setGroupId(int id) {
    group.put("id", id);
  }

}
