package io.github.rcarlosdasilva.weixin.model.response.user;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.user.bean.OpenIdCollection;

public class UserOpenIdListResponse {

  private int total;
  private int count;
  @SerializedName("data")
  private OpenIdCollection openIds;
  @SerializedName("next_openid")
  private String lastOpenId;

  /**
   * 关注该公众账号的总用户数.
   * 
   * @return total
   */
  public int getTotal() {
    return total;
  }

  /**
   * 拉取的OPENID个数，最大值为10000.
   * 
   * @return count
   */
  public int getCount() {
    return count;
  }

  /**
   * OpenId集合.
   * 
   * @return {@link OpenIdCollection}
   */
  public OpenIdCollection getOpenIds() {
    return openIds;
  }

  /**
   * 拉取列表的最后一个用户的OPENID.
   * 
   * @return open_id
   */
  public String getLastOpenId() {
    return lastOpenId;
  }

}
