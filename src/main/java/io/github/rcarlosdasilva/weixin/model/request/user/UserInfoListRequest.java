package io.github.rcarlosdasilva.weixin.model.request.user;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.Language;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 批量获取用户信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserInfoListRequest extends BasicWeixinRequest {

  @SerializedName("user_list")
  private List<UserInfoRequest> userList = Lists.newArrayList();

  public UserInfoListRequest() {
    this.path = ApiAddress.URL_USER_INFO_LIST;
  }

  /**
   * 添加一个用户OpenId.
   * 
   * @param openId
   *          open_id
   */
  public void addUserOpenId(String openId) {
    UserInfoRequest requestModel = new UserInfoRequest();
    requestModel.setOpenId(openId);
    this.userList.add(requestModel);
  }

  /**
   * 添加一个用户OpenId.
   * 
   * @param openId
   *          open_id
   * @param language
   *          语言
   */
  public void addUserOpenId(String openId, Language language) {
    UserInfoRequest requestModel = new UserInfoRequest();
    requestModel.setOpenId(openId);
    requestModel.setLanguage(language);
    this.userList.add(requestModel);
  }

  /**
   * 添加一个用户OpenId.
   * 
   * @param request
   *          {@link UserInfoRequest}
   */
  public void addUserRequest(UserInfoRequest request) {
    this.userList.add(request);
  }

}
