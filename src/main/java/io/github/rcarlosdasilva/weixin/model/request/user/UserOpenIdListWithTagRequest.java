package io.github.rcarlosdasilva.weixin.model.request.user;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取标签下用户列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class UserOpenIdListWithTagRequest extends BasicRequest {

  @SerializedName("tagid")
  private int tagId;
  @SerializedName("next_openid")
  private String nextOpenId;

  public UserOpenIdListWithTagRequest() {
    this.path = ApiAddress.URL_USER_OPENID_LIST_WITH_TAG;
  }

  /**
   * 标签id.
   * 
   * @param tagId
   *          tag id
   */
  public void setTagId(int tagId) {
    this.tagId = tagId;
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

}
