package io.github.rcarlosdasilva.weixin.model.request.message;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Template;

/**
 * 推送模板消息请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@SuppressWarnings("unused")
public class MessageSendWithTemplateRequest extends BasicRequest {

  @SerializedName("touser")
  private String to;
  @SerializedName("template_id")
  private String templateId;
  private String url;
  private Map<String, Template> data;

  public MessageSendWithTemplateRequest() {
    this.path = ApiAddress.URL_MESSAGE_SEND_WITH_TEMPLATE;
  }

  /**
   * 推送到OpenId.
   * 
   * @param to
   *          to
   */
  public void setTo(String to) {
    this.to = to;
  }

  /**
   * 模板id.
   * 
   * @param templateId
   *          template id
   */
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  /**
   * 模板跳转URL.
   * 
   * @param url
   *          url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 模板内容.
   * 
   * @param data
   *          内容
   */
  public void setData(Map<String, Template> data) {
    this.data = data;
  }

}
