package io.github.rcarlosdasilva.weixin.model.response.template.bean;

import com.google.gson.annotations.SerializedName;

public class Template {

  @SerializedName("template_id")
  private String id;
  private String title;
  @SerializedName("primary_industry")
  private String primaryIndustry;
  @SerializedName("deputy_industry")
  private String secondaryIndustry;
  private String content;
  private String example;

  /**
   * 公众帐号下模板消息ID.
   * 
   * @return template id
   */
  public String getId() {
    return id;
  }

  /**
   * 模板标题.
   * 
   * @return title
   */
  public String getTitle() {
    return title;
  }

  /**
   * 模板所属行业的一级行业.
   * 
   * @return industry
   */
  public String getPrimaryIndustry() {
    return primaryIndustry;
  }

  /**
   * 模板所属行业的二级行业.
   * 
   * @return industry
   */
  public String getSecondaryIndustry() {
    return secondaryIndustry;
  }

  /**
   * 模板内容.
   * 
   * @return content
   */
  public String getContent() {
    return content;
  }

  /**
   * 模板示例.
   * 
   * @return example
   */
  public String getExample() {
    return example;
  }

}
