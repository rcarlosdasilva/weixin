package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.List;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.UserGroupApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.user.group.UserGroupCreateRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.group.UserGroupDeleteRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.group.UserGroupGetRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.group.UserGroupListRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.group.UserGroupMoveMemberBatchRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.group.UserGroupMoveMemberRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.group.UserGroupUpdateRequest;
import io.github.rcarlosdasilva.weixin.model.response.user.group.UserGroupGetResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.group.UserGroupListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.group.UserGroupResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.group.bean.UserGroup;

/**
 * 用户组相关API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserGroupApiImpl extends BasicApi implements UserGroupApi {

  public UserGroupApiImpl(String accountKey) {
    this.accountKey = accountKey;
  }

  @Override
  public int create(String name) {
    UserGroupCreateRequest requestModel = new UserGroupCreateRequest();
    requestModel.setGroupName(name);

    UserGroupResponse responseModel = post(UserGroupResponse.class, requestModel);

    return responseModel == null ? Convention.GLOBAL_FAIL_ID_INT : responseModel.getGroup().getId();
  }

  @Override
  public List<UserGroup> list() {
    UserGroupListRequest requestModel = new UserGroupListRequest();

    UserGroupListResponse responseModel = get(UserGroupListResponse.class, requestModel);

    return responseModel == null ? null : responseModel.getGroups();
  }

  @Override
  public int getByOpenId(String openId) {
    UserGroupGetRequest requestModel = new UserGroupGetRequest();
    requestModel.setOpenId(openId);

    UserGroupGetResponse responseModel = post(UserGroupGetResponse.class, requestModel);

    return responseModel == null ? Convention.GLOBAL_FAIL_ID_INT : responseModel.getGroupId();
  }

  @Override
  public boolean update(int id, String newName) {
    UserGroupUpdateRequest requestModel = new UserGroupUpdateRequest();
    requestModel.setGroupId(id);
    requestModel.setNewGroupName(newName);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean moveMemberTo(int toGroupId, String openId) {
    UserGroupMoveMemberRequest requestModel = new UserGroupMoveMemberRequest();
    requestModel.setOpenId(openId);
    requestModel.setToGroupId(toGroupId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean moveMemberBatchTo(int toGroupId, List<String> openIds) {
    UserGroupMoveMemberBatchRequest requestModel = new UserGroupMoveMemberBatchRequest();
    requestModel.setOpenIds(openIds);
    requestModel.setToGroupId(toGroupId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean delete(int id) {
    UserGroupDeleteRequest requestModel = new UserGroupDeleteRequest();
    requestModel.setGroupId(id);

    return post(Boolean.class, requestModel);
  }

}
