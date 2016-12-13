package io.github.rcarlosdasilva.weixin.model.request.user.group;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 更新用户组请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserGroupUpdateRequest extends BasicRequest {

  private Map<String, Object> group = Maps.newHashMap();

  public UserGroupUpdateRequest() {
    this.path = ApiAddress.URL_USER_GROUP_UPDATE;
  }

  /**
   * 组id.
   */
  public void setGroupId(int id) {
    group.put("id", id);
  }

  /**
   * 组名.
   */
  public void setNewGroupName(String newName) {
    group.put("name", newName);
  }

}
