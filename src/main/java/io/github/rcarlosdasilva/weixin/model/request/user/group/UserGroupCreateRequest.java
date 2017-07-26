package io.github.rcarlosdasilva.weixin.model.request.user.group;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 创建用户组请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserGroupCreateRequest extends BasicWeixinRequest {

  private Map<String, String> group = Maps.newHashMap();

  public UserGroupCreateRequest() {
    this.path = ApiAddress.URL_USER_GROUP_CREATE;
  }

  public void setGroupName(String groupName) {
    group.put("name", groupName);
  }

}
