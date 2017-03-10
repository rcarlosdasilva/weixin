package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.List;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.UserTagApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.UserTagCreateRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.UserTagDeleteRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.UserTagListBasedUserRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.UserTagListRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.UserTagTaggingRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.UserTagUntaggingRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.tag.UserTagUpdateRequest;
import io.github.rcarlosdasilva.weixin.model.response.user.tag.UserTagCreateResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.tag.UserTagListBasedUserResposne;
import io.github.rcarlosdasilva.weixin.model.response.user.tag.UserTagListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.tag.bean.UserTag;

/**
 * 用户标签API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagApiImpl extends BasicApi implements UserTagApi {

  public UserTagApiImpl(String accountKey) {
    this.accountKey = accountKey;
  }

  @Override
  public int create(String name) {
    UserTagCreateRequest requestModel = new UserTagCreateRequest();
    requestModel.setTagName(name);

    UserTagCreateResponse responseModel = post(UserTagCreateResponse.class, requestModel);
    return responseModel == null ? Convention.GLOBAL_FAIL_ID_INT : responseModel.getTag().getId();
  }

  @Override
  public List<UserTag> list() {
    UserTagListRequest requestModel = new UserTagListRequest();

    UserTagListResponse responseModel = get(UserTagListResponse.class, requestModel);
    return responseModel == null ? null : responseModel.getTags();
  }

  @Override
  public boolean update(int id, String newName) {
    UserTagUpdateRequest requestModel = new UserTagUpdateRequest();
    requestModel.setTagId(id);
    requestModel.setTagName(newName);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean delete(int id) {
    UserTagDeleteRequest requestModel = new UserTagDeleteRequest();
    requestModel.setTagId(id);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean tagging(int id, List<String> openIds) {
    UserTagTaggingRequest requestModel = new UserTagTaggingRequest();
    requestModel.setId(id);
    requestModel.setOpenIds(openIds);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean untagging(int id, List<String> openIds) {
    UserTagUntaggingRequest requestModel = new UserTagUntaggingRequest();
    requestModel.setId(id);
    requestModel.setOpenIds(openIds);

    return post(Boolean.class, requestModel);
  }

  @Override
  public List<Integer> listBasedUser(String openId) {
    UserTagListBasedUserRequest requestModel = new UserTagListBasedUserRequest();
    requestModel.setOpenId(openId);

    UserTagListBasedUserResposne responseModel = post(UserTagListBasedUserResposne.class,
        requestModel);
    return responseModel == null ? null : responseModel.getTags();
  }

}
