package io.github.rcarlosdasilva.weixin.model.request.user.group;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 删除用户组请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserGroupDeleteRequest extends BasicRequest {

  private Map<String, Integer> group = Maps.newHashMap();

  public UserGroupDeleteRequest() {
    this.path = ApiAddress.URL_USER_GROUP_DELETE;
  }

  public void setGroupId(int id) {
    group.put("id", id);
  }

}
