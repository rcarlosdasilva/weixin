package io.github.rcarlosdasilva.weixin.model.response.user.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OpenIdCollection {

  @SerializedName("openid")
  private List<String> list;

  /**
   * OpenId列表.
   * 
   * @return list of open_id
   */
  public List<String> getList() {
    return list;
  }

}
