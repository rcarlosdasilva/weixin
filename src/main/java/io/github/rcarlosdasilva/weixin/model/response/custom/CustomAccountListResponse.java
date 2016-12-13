package io.github.rcarlosdasilva.weixin.model.response.custom;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomAccount;

public class CustomAccountListResponse {

  @SerializedName("kf_list")
  private List<CustomAccount> customs;

  /**
   * 客服列表.
   */
  public List<CustomAccount> getCustoms() {
    return customs;
  }

}
