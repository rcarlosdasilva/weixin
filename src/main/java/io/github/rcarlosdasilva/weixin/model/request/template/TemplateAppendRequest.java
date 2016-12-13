package io.github.rcarlosdasilva.weixin.model.request.template;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 添加模板请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class TemplateAppendRequest extends BasicRequest {

  @SerializedName("template_id_short")
  private String code;

  public TemplateAppendRequest() {
    this.path = ApiAddress.URL_TEMPLATE_APPEND;
  }

  /**
   * 模板id.
   */
  public void setCode(String code) {
    this.code = code;
  }

}
