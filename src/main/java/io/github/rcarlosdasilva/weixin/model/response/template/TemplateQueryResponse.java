package io.github.rcarlosdasilva.weixin.model.response.template;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.model.response.template.bean.Template;

public class TemplateQueryResponse {

  @SerializedName("template_list")
  private List<Template> templates;

  /**
   * 模板列表.
   * 
   * @return list of {@link Template}
   */
  public List<Template> getTemplates() {
    return templates;
  }

}
