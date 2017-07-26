package io.github.rcarlosdasilva.weixin.model.request.template;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 获取行业信息请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class TemplateIndustryGetReqeust extends BasicWeixinRequest {

  public TemplateIndustryGetReqeust() {
    this.path = ApiAddress.URL_TEMPLATE_INDUSTRY_GET;
  }

}
