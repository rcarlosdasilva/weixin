package io.github.rcarlosdasilva.weixin.model.response.user;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.user.bean.OpenIdCollection;

public class BlackListQueryResponse {

  private int total;
  private int count;
  private OpenIdCollection data;
  @SerializedName("next_openid")
  private String nextOpenId;

  /**
   * 用户总数.
   * 
   * @return total
   */
  public int getTotal() {
    return total;
  }

  /**
   * 当前数量.
   * 
   * @return count
   */
  public int getCount() {
    return count;
  }

  /**
   * OpenIds.
   * 
   * @return {@link OpenIdCollection}
   */
  public OpenIdCollection getData() {
    return data;
  }

  /**
   * 拉取的最后OpenId.
   * 
   * @return open_id
   */
  public String getNextOpenId() {
    return nextOpenId;
  }

}
