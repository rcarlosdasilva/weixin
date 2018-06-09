package io.github.rcarlosdasilva.weixin.api

import com.google.common.io.ByteStreams
import io.github.rcarlosdasilva.weixin.core.ExecuteException
import io.github.rcarlosdasilva.weixin.core.MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException
import io.github.rcarlosdasilva.weixin.core.Weixin
import io.github.rcarlosdasilva.weixin.handler.*
import io.github.rcarlosdasilva.weixin.model.Account
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.Op
import io.github.rcarlosdasilva.weixin.model.request.MpAccessTokenRequest
import io.github.rcarlosdasilva.weixin.model.request.OpAccessTokenRequest
import io.github.rcarlosdasilva.weixin.model.request.Request
import io.github.rcarlosdasilva.weixin.terms.ACCOUNT_PLATFORM_TYPE_OP
import mu.KotlinLogging
import java.io.File
import java.io.IOException
import java.io.InputStream

/**
 * API访问基础类
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@Suppress("UNCHECKED_CAST")
abstract class Api(private val account: Account) {

  private fun updateAccessToken(requestModel: Request) {
    if (requestModel !is MpAccessTokenRequest && requestModel !is OpAccessTokenRequest) {
      val accessToken = if (account.ap == ACCOUNT_PLATFORM_TYPE_OP) {
        Weixin.op().authentication.askAccessToken()
      } else {
        Weixin.mp(account.key).authentication.askAccessToken()
      }
      requestModel.updateAccessToken(accessToken)
    }
  }

  /**
   * 发送post请求
   *
   * @param <T> The Type of element
   * @param target 响应的封装类型
   * @param requestModel 请求模型
   * @return 响应封装对象
   */
  fun <T> post(target: Class<T>, requestModel: Request): T {
    updateAccessToken(requestModel)

    return object : RetryableRunner<T>() {
      override fun pending(): T {
        val responseText = HttpHandler.request(
            requestModel.url(),
            HttpMethod.POST,
            requestModel.json(),
            ContentType.JSON
        )
        return ResponseParser.parse(target, responseText)
      }
    }.run()

  }

  /**
   * 使用get方法获取二进制流
   *
   * @param requestModel 请求模型
   * @return 二进制流
   */
  protected fun postStream(requestModel: Request): InputStream {
    updateAccessToken(requestModel)

    return object : RetryableRunner<InputStream>() {
      override fun pending(): InputStream = HttpHandler.requestStream(
          requestModel.url(),
          HttpMethod.POST,
          requestModel.json(),
          ContentType.JSON
      )
    }.run()

  }

  /**
   * 发送get请求
   *
   * @param <T> The Type of element
   * @param target 响应的封装类型
   * @param requestModel 请求模型
   * @return 响应封装对象
   */
  protected operator fun <T> get(target: Class<T>, requestModel: Request): T {
    updateAccessToken(requestModel)

    return object : RetryableRunner<T>() {
      override fun pending(): T {
        val responseText = HttpHandler.request(requestModel.url(), HttpMethod.GET, "", ContentType.JSON)
        return ResponseParser.parse(target, responseText)
      }
    }.run()

  }

  /**
   * 使用get方法获取二进制流
   *
   * @param requestModel 请求模型
   * @return 二进制流
   */
  protected fun getStream(requestModel: Request): InputStream {
    updateAccessToken(requestModel)

    return object : RetryableRunner<InputStream>() {
      override fun pending(): InputStream = HttpHandler.requestStream(
          requestModel.url(),
          HttpMethod.GET,
          "",
          ContentType.JSON
      )
    }.run()

  }

  /**
   * 上传一个文件（post）
   *
   * @param <T> The Type of element
   * @param target 响应的封装类型
   * @param requestModel 请求模型
   * @param key 文件标识
   * @param fileName 文件名
   * @param file 文件路径
   * @param additionalData 附加数据，可当做参数传递出去
   * @return 响应封装对象
   */
  protected fun <T> upload(
      target: Class<T>,
      requestModel: Request,
      key: String,
      fileName: String,
      file: File,
      additionalData: List<FormData>?
  ): T {
    updateAccessToken(requestModel)

    return object : RetryableRunner<T>() {
      override fun pending(): T {
        val responseText = HttpHandler.requestByFiles(
            requestModel.url(),
            listOf(MultiFile(key, fileName, file, ContentType.ANY)),
            additionalData
        )
        return ResponseParser.parse(target, responseText)
      }
    }.run()
  }

  protected fun readStream(`is`: InputStream): ByteArray =
      try {
        `is`.use {
          ByteStreams.toByteArray(it)
        }
      } catch (ex: IOException) {
        throw ExecuteException("无法读取数据流", ex)
      }

  /**
   * 接口请求执行器
   *
   * @param <R> 返回类型
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  internal abstract inner class RetryableRunner<out R> {

    private val logger = KotlinLogging.logger { }

    private val retryTimes: Int
      get() = Weixin.registry.setting.retries

    /**
     * 执行
     *
     *
     * 开始执行方法 pending 中定义的内容，并在access_token无效时，进行刷新access_token同时重新尝试执行
     * N(N=times) 次 pending 方法，直至执行成功。
     *
     * 重试次数：在使用WeixinRegistry.registry()注册时可用setRetryTimes方法设置，默认2次，表示当 pending
     * 方法因 access_token 执行失败时的重试次数
     */
    fun run(): R {
      var times = 0
      while (true) {
        try {
          return pending()
        } catch (ex: MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException) {
          if (times++ >= retryTimes) {
            logger.error { "For:{${account.key}} >> 失败！已尝试重新执行${times - 1}次" }
            throw ExecuteException("请检查请求access_token凭证的信息，如appid和appsecret")
          }
          logger.error { "For:{${account.key}} >> 失败！第${times}次尝试重新执行" }

          Weixin.mp(account.key).authentication.refreshAccessToken()
        }
      }
    }

    /**
     * 具体接口请求执行内容
     */
    abstract fun pending(): R

  }

}

class MpApiWrapper(account: Mp) {

  val authentication = ApiMpAuthentication(account)
  val commonality = ApiMpCommonality(account)
  val user = ApiMpUser(account)
  val userTag = ApiMpUserTag(account)
  val menu = ApiMpMenu(account)
  val template = ApiMpTemplate(account)
  val material = ApiMpMaterial(account)
  val message = ApiMpMessage(account)
  val statistics = ApiMpStatistics(account)
  val customerService = ApiMpCustomerService(account)

}

class OpApiWrapper(account: Op) {

  val authentication = ApiOpAuthentication(account)

}