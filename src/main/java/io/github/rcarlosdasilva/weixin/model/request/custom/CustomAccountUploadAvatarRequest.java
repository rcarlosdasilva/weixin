package io.github.rcarlosdasilva.weixin.model.request.custom;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 上传客服头像请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CustomAccountUploadAvatarRequest extends BasicWeixinRequest {

  private String account;

  public CustomAccountUploadAvatarRequest() {
    this.path = ApiAddress.URL_CUSTOM_ACCOUNT_UPLOAD_AVATAR;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .append("&kf_account=").append(this.account).toString();
  }

}
