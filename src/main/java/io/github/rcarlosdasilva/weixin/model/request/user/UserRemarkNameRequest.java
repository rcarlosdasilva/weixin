package io.github.rcarlosdasilva.weixin.model.request.user;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 设置用户备注名请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class UserRemarkNameRequest extends BasicWeixinRequest {

  @SerializedName("openid")
  private String openId;
  @SerializedName("remark")
  private String name;

  public UserRemarkNameRequest() {
    this.path = ApiAddress.URL_USER_REMARK_NAME;
  }

  /**
   * 用户OpenId.
   * 
   * @param openId
   *          open_id
   */
  public void setOpenId(String openId) {
    this.openId = openId;
  }

  /**
   * 备注名.
   * 
   * @param name
   *          name
   */
  public void setName(String name) {
    this.name = name;
  }

}
