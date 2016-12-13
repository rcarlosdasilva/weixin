package io.github.rcarlosdasilva.weixin.model.request.menu.bean;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MatchRule {

  @SerializedName("tag_id")
  private int tagId;
  private int sex;
  private String country;
  private String province;
  private String city;
  @SerializedName("client_platform_type")
  private int clientPlatformType;
  private String language;

  public void setTagId(int tagId) {
    this.tagId = tagId;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setClientPlatformType(int clientPlatformType) {
    this.clientPlatformType = clientPlatformType;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

}
