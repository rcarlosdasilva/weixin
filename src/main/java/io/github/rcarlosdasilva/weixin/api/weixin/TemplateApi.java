package io.github.rcarlosdasilva.weixin.api.weixin;

import java.util.List;

import io.github.rcarlosdasilva.weixin.common.dictionary.Industry;
import io.github.rcarlosdasilva.weixin.model.response.template.TemplateIndustryGetResponse;
import io.github.rcarlosdasilva.weixin.model.response.template.bean.Template;

/**
 * 公众号消息模板API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface TemplateApi {

  /**
   * 设置行业可在MP中完成，每月可修改行业1次，账号仅可使用所属行业中相关的模板.
   * 
   * @param primary
   *          主行业
   * @param secondary
   *          副行业
   * @return 设置成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN"
   *      >模板消息接口</a>
   */
  boolean setIndustry(Industry primary, Industry secondary);

  /**
   * 获取帐号设置的行业信息.
   * 
   * @return 行业信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN"
   *      >模板消息接口</a>
   */
  TemplateIndustryGetResponse getIndustry();

  /**
   * 从行业模板库选择模板到帐号后台.
   * 
   * @param templateCode
   *          模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
   * @return 模板ID
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN"
   *      >模板消息接口</a>
   */
  String append(String templateCode);

  /**
   * 删除已添加至帐号下的模板.
   * 
   * @param templateId
   *          公众帐号下模板消息ID
   * @return 是否删除
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN"
   *      >模板消息接口</a>
   */
  boolean delete(String templateId);

  /**
   * 获取已添加至帐号下所有模板列表.
   * 
   * @return 模板信息列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN"
   *      >模板消息接口</a>
   */
  List<Template> query();

}
