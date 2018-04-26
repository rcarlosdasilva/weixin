package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*

/**
 * 获取行业信息请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class TemplateIndustryGetReqeust : MpRequest() {
  init {
    this.path = URL_TEMPLATE_INDUSTRY_GET
  }
}

/**
 * 设置行业信息请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class TemplateIndustrySetReqeust(
  @SerializedName("industry_id1") private val primary: Int,
  @SerializedName("industry_id2") private val secondary: Int
) : MpRequest() {
  init {
    this.path = URL_TEMPLATE_INDUSTRY_SET
  }
}

/**
 * 添加模板请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class TemplateAppendRequest(@SerializedName("template_id_short") private val code: String) : MpRequest() {
  init {
    this.path = URL_TEMPLATE_APPEND
  }
}

/**
 * 删除模板请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class TemplateDeleteRequest(@SerializedName("template_id") private val id: String) : MpRequest() {
  init {
    this.path = URL_TEMPLATE_DELETE
  }
}

/**
 * 获取模板列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class TemplateQueryRequest : MpRequest() {
  init {
    this.path = URL_TEMPLATE_QUERY
  }
}