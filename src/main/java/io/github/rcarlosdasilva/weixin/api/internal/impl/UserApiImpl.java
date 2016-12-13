package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.List;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.UserApi;
import io.github.rcarlosdasilva.weixin.model.request.user.BlackListAppendRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.BlackListCancelRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.BlackListOpenIdListRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.UserInfoByWebAuthorizeRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.UserInfoListRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.UserInfoRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.UserOpenIdListRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.UserOpenIdListWithTagRequest;
import io.github.rcarlosdasilva.weixin.model.request.user.UserRemarkNameRequest;
import io.github.rcarlosdasilva.weixin.model.response.user.BlackListQueryResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.UserListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.UserResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.bean.User;

/**
 * 用户相关API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserApiImpl extends BasicApi implements UserApi {

  @Override
  public boolean remarkName(String openId, String name) {
    UserRemarkNameRequest requestModel = new UserRemarkNameRequest();
    requestModel.setOpenId(openId);
    requestModel.setName(name);

    return post(Boolean.class, requestModel);
  }

  @Override
  public User getUserInfo(String openId) {
    UserInfoRequest requestModel = new UserInfoRequest();
    requestModel.setOpenId(openId);

    return get(UserResponse.class, requestModel);
  }

  @Override
  public List<User> getUsersInfo(List<String> openIds) {
    UserInfoListRequest requestModel = new UserInfoListRequest();
    for (String openId : openIds) {
      requestModel.addUserOpenId(openId);
    }

    UserListResponse responseModel = post(UserListResponse.class, requestModel);

    return responseModel == null ? null : responseModel.getUserList();
  }

  @Override
  public UserOpenIdListResponse listAllUsersOpenId() {
    return listAllUsersOpenId(null);
  }

  @Override
  public UserOpenIdListResponse listAllUsersOpenId(String nextOpenId) {
    UserOpenIdListRequest requestModel = new UserOpenIdListRequest();
    requestModel.setNextOpenId(nextOpenId);

    return get(UserOpenIdListResponse.class, requestModel);
  }

  @Override
  public User getUserInfoByWebAuthorize(String accessToken, String openId) {
    UserInfoByWebAuthorizeRequest requestModel = new UserInfoByWebAuthorizeRequest();
    requestModel.setAccessToken(accessToken);
    requestModel.setOpenId(openId);

    return get(UserResponse.class, requestModel);
  }

  @Override
  public UserOpenIdListResponse listUsersOpenIdWithTag(int tagId) {
    return listUsersOpenIdWithTag(tagId, null);
  }

  @Override
  public UserOpenIdListResponse listUsersOpenIdWithTag(int tagId, String nextOpenId) {
    UserOpenIdListWithTagRequest requestModel = new UserOpenIdListWithTagRequest();
    requestModel.setTagId(tagId);
    requestModel.setNextOpenId(nextOpenId);

    return post(UserOpenIdListResponse.class, requestModel);
  }

  @Override
  public BlackListQueryResponse listUsersInBlackList() {
    return listUsersInBlackList(null);
  }

  @Override
  public BlackListQueryResponse listUsersInBlackList(String beginOpenId) {
    BlackListOpenIdListRequest requestModel = new BlackListOpenIdListRequest();
    requestModel.setBeginOpenId(beginOpenId);

    return post(BlackListQueryResponse.class, requestModel);
  }

  @Override
  public boolean appendUsersToBlackList(List<String> openIds) {
    BlackListAppendRequest requestModel = new BlackListAppendRequest();
    requestModel.setList(openIds);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean cancelUsersFromBlackList(List<String> openIds) {
    BlackListCancelRequest requestModel = new BlackListCancelRequest();
    requestModel.setList(openIds);

    return post(Boolean.class, requestModel);
  }

}
