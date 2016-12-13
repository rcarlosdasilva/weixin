package io.github.rcarlosdasilva.weixin.model.response.template;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.template.bean.Industry;

public class TemplateIndustryGetResponse {

  @SerializedName("primary_industry")
  private Industry primaryIndustry;
  @SerializedName("secondary_industry")
  private Industry secondaryIndustry;

  /** 模板所属行业的第一行业. */
  public Industry getPrimaryIndustry() {
    return primaryIndustry;
  }

  /** 模板所属行业的第二行业. */
  public Industry getSecondaryIndustry() {
    return secondaryIndustry;
  }

}
