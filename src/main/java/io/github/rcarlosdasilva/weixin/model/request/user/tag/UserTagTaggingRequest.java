package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 给用户设置标签请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagTaggingRequest extends BasicWeixinRequest {

  @SerializedName("tagid")
  private int id;
  @SerializedName("openid_list")
  private List<String> openIds;

  public UserTagTaggingRequest() {
    this.path = ApiAddress.URL_USER_TAG_TAGGING_USER;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOpenIds(List<String> openIds) {
    this.openIds = openIds;
  }

}
