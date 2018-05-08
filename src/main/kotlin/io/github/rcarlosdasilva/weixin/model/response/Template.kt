package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName

class TemplateIndustryGetResponse {
  /**
   * 模板所属行业的第一行业
   */
  @SerializedName("primary_industry")
  val primaryIndustry: Industry? = null
  /**
   * 模板所属行业的第二行业
   */
  @SerializedName("secondary_industry")
  val secondaryIndustry: Industry? = null
}

class TemplateAppendResponse {
  /**
   * 模板id
   */
  @SerializedName("template_id")
  val id: String? = null
}

class TemplateQueryResponse {
  /**
   * 模板列表
   */
  @SerializedName("template_list")
  val templates: List<Template>? = null
}

class Industry {
  /**
   * 帐号设置的主营行业
   */
  @SerializedName("first_class")
  val major: String? = null
  /**
   * 帐号设置的副营行业
   */
  @SerializedName("second_class")
  val minor: String? = null
}

class Template {
  /**
   * 公众帐号下模板消息ID
   */
  @SerializedName("template_id")
  val id: String? = null
  /**
   * 模板标题
   */
  val title: String? = null
  /**
   * 模板所属行业的一级行业
   */
  @SerializedName("primary_industry")
  val primaryIndustry: String? = null
  /**
   * 模板所属行业的二级行业
   */
  @SerializedName("deputy_industry")
  val secondaryIndustry: String? = null
  /**
   * 模板内容
   */
  val content: String? = null
  /**
   * 模板示例
   */
  val example: String? = null
}