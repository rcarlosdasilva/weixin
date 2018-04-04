package io.github.rcarlosdasilva.weixin.model.request

import io.github.rcarlosdasilva.weixin.handler.Freeze
import io.github.rcarlosdasilva.weixin.handler.JsonHandler

/**
 * 请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface Request {

  /**
   * 转换模型数据成URL.
   *
   * @return 链接
   */
  fun url(): String

  /**
   * 转换模型数据为JSON格式字符串.
   *
   * @return JSON数据
   */
  fun json(): String

  /**
   * 对公众号是access_token，对开放平台是component_access_token
   */
  fun updateAccessToken(accessToken: String)

}


abstract class BasicRequest : Request {

  @Freeze
  var path: String? = null
  @Freeze
  lateinit var accessToken: String

  override fun url(): String = this.toString()

  override fun json(): String = JsonHandler.toJson(this, this.javaClass)

  override fun updateAccessToken(accessToken: String) {
    this.accessToken = accessToken
  }

}


/**
 * 基本公众号请求模型
 *
 * @author Dean Zhao (rcarlosdailva@qq.com)
 */
open class MpRequest : BasicRequest() {
  override fun toString(): String = "$path?access_token=$accessToken"
}

/**
 * 基本开放平台请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
open class OpRequest : BasicRequest() {
  override fun toString(): String = "$path?component_access_token=$accessToken"
}