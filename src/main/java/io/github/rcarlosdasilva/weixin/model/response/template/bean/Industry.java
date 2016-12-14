package io.github.rcarlosdasilva.weixin.model.response.template.bean;

import com.google.gson.annotations.SerializedName;

public class Industry {

  @SerializedName("first_class")
  private String major;
  @SerializedName("second_class")
  private String minor;

  /**
   * 帐号设置的主营行业.
   * 
   * @return major
   */
  public String getMajor() {
    return major;
  }

  /**
   * 帐号设置的副营行业.
   * 
   * @return minor
   */
  public String getMinor() {
    return minor;
  }

}
