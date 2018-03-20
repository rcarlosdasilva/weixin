package io.github.rcarlosdasilva.weixin.handler

import mu.KotlinLogging
import okhttp3.*
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * Http请求工具
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object HttpHandler {

  private val logger = KotlinLogging.logger {}

  private val client: OkHttpClient =
    OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build()

  /**
   * 普通请求
   *
   * @param url     请求地址
   * @param method  请求方法
   * @param content 请求参数体
   * @param type  指定请求内容格式，JSON或XML
   * @return response字符串
   */
  fun request(url: String, method: HttpMethod, content: String, type: ContentType): String? =
    generate(url, method, content, type).run { doRequest(this)?.body()!!.string() }

  /**
   * 发送请求，并返回二进制流
   *
   * @param url     请求地址
   * @param method  请求方法
   * @param content 请求参数体
   * @param type  指定请求内容格式，JSON或XML
   * @return response二进制流
   */
  fun requestStream(url: String, method: HttpMethod, content: String, type: ContentType): InputStream? =
    generate(url, method, content, type).run { doRequest(this)?.body()!!.byteStream() }

  /**
   * 以POST方法上传一个Multipart数据，内含多个文件
   *
   * @param url             请求地址
   * @param multiFiles      多个文件信息
   * @param additionalData 附加表单数据
   * @return response字符串
   */
  fun requestByFiles(url: String, multiFiles: List<MultiFile>, additionalData: List<FormData>?): String? {
    val bodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)

    additionalData?.forEach { bodyBuilder.addFormDataPart(it.key, it.value) }
    multiFiles.forEach {
      bodyBuilder.addFormDataPart(it.key, it.filename, RequestBody.create(it.type.mediaType, it.file))
    }

    return Request.Builder().url(url).post(bodyBuilder.build()).build().run {
      doRequest(this)?.body()!!.string()
    }
  }

  private fun doRequest(request: Request): Response? =
    try {
      val response = client.newCall(request).execute()
      if (!response.isSuccessful) {
        logger.error { response.message() }
        null
      } else {
        response
      }
    } catch (ex: IOException) {
      logger.error(ex) { "OkHttp Request Exception" }
      null
    }

  private fun generate(url: String, method: HttpMethod, content: String, type: ContentType): Request =
    Request.Builder().url(url).let {
      when (method) {
        HttpMethod.GET -> it.build()
        HttpMethod.HEAD -> it.head().build()
        HttpMethod.POST -> it.post(RequestBody.create(type.mediaType, content)).build()
        HttpMethod.PUT -> it.put(RequestBody.create(type.mediaType, content)).build()
        HttpMethod.PATCH -> it.patch(RequestBody.create(type.mediaType, content)).build()
        HttpMethod.DELETE -> it.delete(RequestBody.create(type.mediaType, content)).build()
      }
    }

}

data class FormData(val key: String, val value: String)

data class MultiFile(val key: String, val filename: String, val file: File, val type: ContentType)

enum class HttpMethod { GET, HEAD, POST, PUT, PATCH, DELETE }

enum class ContentType(val text: String) {

  /**
   * .*（ 二进制流，不知道下载文件类型）.
   */
  ANY("application/octet-stream"),
  /**
   * JSON.
   */
  JSON("application/json; charset=utf-8"),
  /**
   * XML.
   */
  XML("application/xml; charset=utf-8"),
  /**
   * TEXT.
   */
  TEXT("text/plain"),
  /**
   * video/avi.
   */
  AVI("video/avi"),
  /**
   * BMP.
   */
  BMP("application/x-bmp"),
  /**
   * DOC.
   */
  DOC("application/msword"),
  /**
   * GIF.
   */
  GIF("image/gif"),
  /**
   * HTML.
   */
  HTML("text/html"),
  /**
   * ICO.
   */
  ICO("image/x-icon"),
  /**
   * JPEG.
   */
  JPEG("image/jpeg"),
  /**
   * MDB.
   */
  MDB("application/msaccess"),
  /**
   * MP4.
   */
  MP4("video/mpeg4"),
  /**
   * PDF.
   */
  PDF("application/pdf"),
  /**
   * PNG.
   */
  PNG("image/png"),
  /**
   * PPT.
   */
  PPT("application/vnd.ms-powerpoint"),
  /**
   * SWF.
   */
  SWF("application/x-shockwave-flash"),
  /**
   * VCF.
   */
  VCF("text/x-vcard"),
  /**
   * WMA.
   */
  WMA("audio/x-ms-wma"),
  /**
   * WMV.
   */
  WMV("video/x-ms-wmv"),
  /**
   * XLS.
   */
  XLS("application/vnd.ms-excel");

  val mediaType = MediaType.parse(text)

}