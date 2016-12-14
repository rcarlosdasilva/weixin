package io.github.rcarlosdasilva.weixin.model.request.user;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取用户列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserOpenIdListRequest extends BasicRequest {

  private String nextOpenId;

  public UserOpenIdListRequest() {
    this.path = ApiAddress.URL_USER_ALL_OPENID_LIST;
  }

  /**
   * 第一个拉取的OpenId.
   * 
   * @param nextOpenId
   *          open_id
   */
  public void setNextOpenId(String nextOpenId) {
    this.nextOpenId = nextOpenId;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(this.path).append("?access_token=")
        .append(this.accessToken);
    if (!Strings.isNullOrEmpty(this.nextOpenId)) {
      sb.append("&next_openid=").append(this.nextOpenId);
    }
    return sb.toString();
  }

}
