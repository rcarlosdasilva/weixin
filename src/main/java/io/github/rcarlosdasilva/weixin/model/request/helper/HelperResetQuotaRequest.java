package io.github.rcarlosdasilva.weixin.model.request.helper;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

public class HelperResetQuotaRequest extends BasicWeixinRequest {

  @SerializedName("appid")
  private String appId;

  public HelperResetQuotaRequest() {
    this.path = ApiAddress.URL_HELPER_RESET_QUOTA;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

}
