package io.github.rcarlosdasilva.weixin.api.weixin.impl;

import java.util.List;

import io.github.rcarlosdasilva.weixin.api.BasicApi;
import io.github.rcarlosdasilva.weixin.api.weixin.TemplateApi;
import io.github.rcarlosdasilva.weixin.common.dictionary.Industry;
import io.github.rcarlosdasilva.weixin.model.request.template.TemplateAppendRequest;
import io.github.rcarlosdasilva.weixin.model.request.template.TemplateDeleteRequest;
import io.github.rcarlosdasilva.weixin.model.request.template.TemplateIndustryGetReqeust;
import io.github.rcarlosdasilva.weixin.model.request.template.TemplateIndustrySetReqeust;
import io.github.rcarlosdasilva.weixin.model.request.template.TemplateQueryRequest;
import io.github.rcarlosdasilva.weixin.model.response.template.TemplateAppendResponse;
import io.github.rcarlosdasilva.weixin.model.response.template.TemplateIndustryGetResponse;
import io.github.rcarlosdasilva.weixin.model.response.template.TemplateQueryResponse;
import io.github.rcarlosdasilva.weixin.model.response.template.bean.Template;

/**
 * 模板消息API实现
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class TemplateApiImpl extends BasicApi implements TemplateApi {

  public TemplateApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public boolean setIndustry(Industry primary, Industry secondary) {
    TemplateIndustrySetReqeust requestModel = new TemplateIndustrySetReqeust();
    requestModel.setPrimary(primary);
    requestModel.setSecondary(secondary);

    return post(Boolean.class, requestModel);
  }

  @Override
  public TemplateIndustryGetResponse getIndustry() {
    TemplateIndustryGetReqeust requestModel = new TemplateIndustryGetReqeust();

    return get(TemplateIndustryGetResponse.class, requestModel);
  }

  @Override
  public String append(String templateCode) {
    TemplateAppendRequest requestModel = new TemplateAppendRequest();
    requestModel.setCode(templateCode);

    TemplateAppendResponse responseModel = post(TemplateAppendResponse.class, requestModel);
    return responseModel == null ? null : responseModel.getId();
  }

  @Override
  public boolean delete(String templateId) {
    TemplateDeleteRequest requestModel = new TemplateDeleteRequest();
    requestModel.setId(templateId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public List<Template> query() {
    TemplateQueryRequest requestModel = new TemplateQueryRequest();

    TemplateQueryResponse responseModel = get(TemplateQueryResponse.class, requestModel);
    return responseModel == null ? null : responseModel.getTemplates();
  }

}
