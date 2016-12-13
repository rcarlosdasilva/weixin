package io.github.rcarlosdasilva.weixin.model.request.template;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.Industry;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 设置行业信息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class TemplateIndustrySetReqeust extends BasicRequest {

  @SerializedName("industry_id1")
  private int primary;
  @SerializedName("industry_id2")
  private int secondary;

  public TemplateIndustrySetReqeust() {
    this.path = ApiAddress.URL_TEMPLATE_INDUSTRY_SET;
  }

  /**
   * 第一行业.
   */
  public void setPrimary(Industry primary) {
    this.primary = primary.getCode();
  }

  /**
   * 第二行业.
   */
  public void setSecondary(Industry secondary) {
    this.secondary = secondary.getCode();
  }

}
