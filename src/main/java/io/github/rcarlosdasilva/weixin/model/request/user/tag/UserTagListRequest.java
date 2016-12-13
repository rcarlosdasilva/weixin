package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取标签列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagListRequest extends BasicRequest {

  public UserTagListRequest() {
    this.path = ApiAddress.URL_USER_TAG_LIST;
  }

}
