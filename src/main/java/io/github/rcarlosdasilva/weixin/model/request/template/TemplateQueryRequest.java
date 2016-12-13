package io.github.rcarlosdasilva.weixin.model.request.template;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取模板列表请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class TemplateQueryRequest extends BasicRequest {

  public TemplateQueryRequest() {
    this.path = ApiAddress.URL_TEMPLATE_QUERY;
  }

}
