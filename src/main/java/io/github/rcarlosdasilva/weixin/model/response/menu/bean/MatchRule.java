package io.github.rcarlosdasilva.weixin.model.response.menu.bean;

import com.google.gson.annotations.SerializedName;

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

  /** 用户标签的id，可通过用户标签管理接口获取. */
  public int getTagId() {
    return tagId;
  }

  /** 性别：男（1）女（2）. */
  public int getSex() {
    return sex;
  }

  /** 国家信息. */
  public String getCountry() {
    return country;
  }

  /** 省份信息. */
  public String getProvince() {
    return province;
  }

  /** 城市信息. */
  public String getCity() {
    return city;
  }

  /** 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3). */
  public int getClientPlatformType() {
    return clientPlatformType;
  }

  /** 语言信息. */
  public String getLanguage() {
    return language;
  }

}
