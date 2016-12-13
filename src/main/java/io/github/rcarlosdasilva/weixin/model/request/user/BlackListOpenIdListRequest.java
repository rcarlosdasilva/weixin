package io.github.rcarlosdasilva.weixin.model.request.user;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取黑名单列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class BlackListOpenIdListRequest extends BasicRequest {

  @SerializedName("begin_openid")
  private String beginOpenId;

  public BlackListOpenIdListRequest() {
    this.path = ApiAddress.URL_BLACK_LIST_QUERY;
  }

  /**
   * 第一个拉取的OpenId.
   */
  public void setBeginOpenId(String beginOpenId) {
    this.beginOpenId = beginOpenId;
  }

}
