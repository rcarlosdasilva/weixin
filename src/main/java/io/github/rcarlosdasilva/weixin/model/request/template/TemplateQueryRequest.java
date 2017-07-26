package io.github.rcarlosdasilva.weixin.model.request.template;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取模板列表请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class TemplateQueryRequest extends BasicWeixinRequest {

  public TemplateQueryRequest() {
    this.path = ApiAddress.URL_TEMPLATE_QUERY;
  }

}
