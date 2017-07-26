package io.github.rcarlosdasilva.weixin.model.request.template;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.Industry;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 设置行业信息请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class TemplateIndustrySetReqeust extends BasicWeixinRequest {

  @SerializedName("industry_id1")
  private int primary;
  @SerializedName("industry_id2")
  private int secondary;

  public TemplateIndustrySetReqeust() {
    this.path = ApiAddress.URL_TEMPLATE_INDUSTRY_SET;
  }

  /**
   * 第一行业.
   * 
   * @param primary
   *          {@link Industry}
   */
  public void setPrimary(Industry primary) {
    this.primary = primary.getCode();
  }

  /**
   * 第二行业.
   * 
   * @param secondary
   *          {@link Industry}
   */
  public void setSecondary(Industry secondary) {
    this.secondary = secondary.getCode();
  }

}
