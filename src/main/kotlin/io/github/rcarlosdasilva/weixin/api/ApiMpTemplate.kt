package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.Template
import io.github.rcarlosdasilva.weixin.model.response.TemplateAppendResponse
import io.github.rcarlosdasilva.weixin.model.response.TemplateIndustryGetResponse
import io.github.rcarlosdasilva.weixin.model.response.TemplateQueryResponse
import io.github.rcarlosdasilva.weixin.terms.data.Industry

/**
 * 公众号消息模板API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpTemplate(account: Mp) : Api(account) {

  /**
   * 获取帐号设置的行业信息
   *
   * @return 行业信息
   */
  fun getIndustry(): TemplateIndustryGetResponse =
      get(TemplateIndustryGetResponse::class.java, TemplateIndustryGetReqeust())

  /**
   * 设置行业可在MP中完成，每月可修改行业1次，账号仅可使用所属行业中相关的模板
   *
   * @param primary 主行业
   * @param secondary 副行业
   * @return 设置成功
   */
  fun setIndustry(primary: Industry, secondary: Industry): Boolean =
      post(Boolean::class.java, TemplateIndustrySetReqeust(primary.code, secondary.code))

  /**
   * 从行业模板库选择模板到帐号后台
   *
   * @param templateCode 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
   * @return 模板ID
   */
  fun append(templateCode: String): String =
      post(TemplateAppendResponse::class.java, TemplateAppendRequest(templateCode)).id!!

  /**
   * 删除已添加至帐号下的模板
   *
   * @param templateId
   * 公众帐号下模板消息ID
   * @return 是否删除
   */
  fun delete(templateId: String): Boolean = post(Boolean::class.java, TemplateDeleteRequest(templateId))

  /**
   * 获取已添加至帐号下所有模板列表
   *
   * @return 模板信息列表
   */
  fun query(): List<Template> = get(TemplateQueryResponse::class.java, TemplateQueryRequest()).templates!!

}