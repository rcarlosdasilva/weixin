package io.github.rcarlosdasilva.weixin.model.request.template;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 删除模板请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class TemplateDeleteRequest extends BasicWeixinRequest {

  @SerializedName("template_id")
  private String id;

  public TemplateDeleteRequest() {
    this.path = ApiAddress.URL_TEMPLATE_DELETE;
  }

  public void setId(String id) {
    this.id = id;
  }

}
