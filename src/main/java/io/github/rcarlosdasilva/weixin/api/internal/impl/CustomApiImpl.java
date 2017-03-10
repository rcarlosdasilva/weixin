package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.CustomApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCache;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomAccountAppendRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomAccountBindingRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomAccountDeleteRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomAccountListOnlineRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomAccountListRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomAccountUpdateRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomAccountUploadAvatarRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomMessageRecordsRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomSessionCloseRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomSessionCreateRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomSessionListRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomSessionStatusRequest;
import io.github.rcarlosdasilva.weixin.model.request.custom.CustomSessionWaitingsRequest;
import io.github.rcarlosdasilva.weixin.model.response.custom.CustomAccountListOnlineResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.CustomAccountListResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.CustomMessageRecordsResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.CustomSessionListResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.CustomSessionStatusResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.CustomSessionWaitingsResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomAccount;
import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomSession;

/**
 * 客服相关API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CustomApiImpl extends BasicApi implements CustomApi {

  public CustomApiImpl(String accountKey) {
    this.accountKey = accountKey;
  }

  @Override
  public List<CustomAccount> accountList() {
    CustomAccountListRequest requestModel = new CustomAccountListRequest();

    CustomAccountListResponse responseModel = get(CustomAccountListResponse.class, requestModel);
    return responseModel == null ? null : responseModel.getCustoms();
  }

  @Override
  public List<CustomAccount> accountListOnline() {
    CustomAccountListOnlineRequest requestModel = new CustomAccountListOnlineRequest();

    CustomAccountListOnlineResponse responseModel = get(CustomAccountListOnlineResponse.class,
        requestModel);
    return responseModel == null ? null : responseModel.getCustoms();
  }

  @Override
  public boolean accountAppend(String accountPrefix, String nickname) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomAccountAppendRequest requestModel = new CustomAccountAppendRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setNickname(nickname);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean accountBinding(String accountPrefix, String wxId) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomAccountBindingRequest requestModel = new CustomAccountBindingRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setWxId(wxId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean accountDelete(String accountPrefix) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomAccountDeleteRequest requestModel = new CustomAccountDeleteRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());

    return get(Boolean.class, requestModel);
  }

  @Override
  public boolean accountUpdate(String accountPrefix, String nickname) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomAccountUpdateRequest requestModel = new CustomAccountUpdateRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setNickname(nickname);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean accountUploadAvatar(String accountPrefix, String fileName, File file) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomAccountUploadAvatarRequest requestModel = new CustomAccountUploadAvatarRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());

    return upload(Boolean.class, requestModel, Convention.CUSTOM_AVATAR_UPLOAD_KEY, fileName, file,
        null);
  }

  @Override
  public boolean sessionCreate(String accountPrefix, String openId) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomSessionCreateRequest requestModel = new CustomSessionCreateRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setOpenId(openId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean sessionClose(String accountPrefix, String openId) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomSessionCloseRequest requestModel = new CustomSessionCloseRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setOpenId(openId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public CustomSession sessionStatus(String openId) {
    CustomSessionStatusRequest requestModel = new CustomSessionStatusRequest();
    requestModel.setOpenId(openId);

    return get(CustomSessionStatusResponse.class, requestModel);
  }

  @Override
  public List<CustomSession> sessionList(String accountPrefix) {
    Account account = AccountCache.instance().get(this.accountKey);
    CustomSessionListRequest requestModel = new CustomSessionListRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());

    CustomSessionListResponse responseModel = get(CustomSessionListResponse.class, requestModel);
    return responseModel == null ? null : responseModel.getSessions();
  }

  @Override
  public CustomSessionWaitingsResponse sessionWaitings() {
    CustomSessionWaitingsRequest requestModel = new CustomSessionWaitingsRequest();

    return get(CustomSessionWaitingsResponse.class, requestModel);
  }

  @Override
  public CustomMessageRecordsResponse messageRecords(Date start, Date end, int size,
      long messageId) {
    CustomMessageRecordsRequest requestModel = new CustomMessageRecordsRequest();
    requestModel.setStartTime(start.getTime());
    requestModel.setEndTime(end.getTime());
    requestModel.setSize(size);
    requestModel.setMessageId(messageId);

    return post(CustomMessageRecordsResponse.class, requestModel);
  }

}
