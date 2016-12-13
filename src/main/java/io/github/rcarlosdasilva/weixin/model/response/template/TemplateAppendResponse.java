package io.github.rcarlosdasilva.weixin.model.response.template;

import com.google.gson.annotations.SerializedName;

public class TemplateAppendResponse {

  @SerializedName("template_id")
  private String id;

  /**
   * 模板id.
   */
  public String getId() {
    return id;
  }

}
