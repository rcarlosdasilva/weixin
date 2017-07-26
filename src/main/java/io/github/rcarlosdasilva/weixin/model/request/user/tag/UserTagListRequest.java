package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取标签列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserTagListRequest extends BasicWeixinRequest {

  public UserTagListRequest() {
    this.path = ApiAddress.URL_USER_TAG_LIST;
  }

}
