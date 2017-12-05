package io.github.rcarlosdasilva.weixin.api.weixin.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import io.github.rcarlosdasilva.weixin.api.BasicApi;
import io.github.rcarlosdasilva.weixin.api.weixin.CustomerServiceApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.model.WeixinAccount;
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
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CustomerServiceApiImpl extends BasicApi implements CustomerServiceApi {

  public CustomerServiceApiImpl(String accountKey) {
    super(accountKey);
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
    WeixinAccount account = Registry.lookup(this.accountKey);
    CustomAccountAppendRequest requestModel = new CustomAccountAppendRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setNickname(nickname);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean accountBinding(String accountPrefix, String wxId) {
    WeixinAccount account = Registry.lookup(this.accountKey);
    CustomAccountBindingRequest requestModel = new CustomAccountBindingRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setWxId(wxId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean accountDelete(String accountPrefix) {
    WeixinAccount account = Registry.lookup(this.accountKey);
    CustomAccountDeleteRequest requestModel = new CustomAccountDeleteRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());

    return get(Boolean.class, requestModel);
  }

  @Override
  public boolean accountUpdate(String accountPrefix, String nickname) {
    WeixinAccount account = Registry.lookup(this.accountKey);
    CustomAccountUpdateRequest requestModel = new CustomAccountUpdateRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setNickname(nickname);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean accountUploadAvatar(String accountPrefix, String fileName, File file) {
    WeixinAccount account = Registry.lookup(this.accountKey);
    CustomAccountUploadAvatarRequest requestModel = new CustomAccountUploadAvatarRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());

    return upload(Boolean.class, requestModel, Convention.CUSTOM_AVATAR_UPLOAD_KEY, fileName, file,
        null);
  }

  @Override
  public boolean sessionCreate(String accountPrefix, String openId) {
    WeixinAccount account = Registry.lookup(this.accountKey);
    CustomSessionCreateRequest requestModel = new CustomSessionCreateRequest();
    requestModel.setAccount(accountPrefix + "@" + account.getMpId());
    requestModel.setOpenId(openId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean sessionClose(String accountPrefix, String openId) {
    WeixinAccount account = Registry.lookup(this.accountKey);
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
    WeixinAccount account = Registry.lookup(this.accountKey);
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
