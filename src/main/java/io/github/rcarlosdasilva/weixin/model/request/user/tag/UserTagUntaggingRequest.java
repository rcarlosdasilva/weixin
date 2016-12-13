package io.github.rcarlosdasilva.weixin.model.request.user.tag;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 为用户取消标签请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserTagUntaggingRequest extends BasicRequest {

  @SerializedName("tagid")
  private int id;
  @SerializedName("openid_list")
  private List<String> openIds;

  public UserTagUntaggingRequest() {
    this.path = ApiAddress.URL_USER_TAG_UNTAGGING_FROM_USER;
  }

  /**
   * 标签id.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * 用户OpenIds.
   */
  public void setOpenIds(List<String> openIds) {
    this.openIds = openIds;
  }

}
