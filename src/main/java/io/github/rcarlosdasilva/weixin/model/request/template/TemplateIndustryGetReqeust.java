package io.github.rcarlosdasilva.weixin.model.request.template;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 获取行业信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class TemplateIndustryGetReqeust extends BasicRequest {

  public TemplateIndustryGetReqeust() {
    this.path = ApiAddress.URL_TEMPLATE_INDUSTRY_GET;
  }

}
