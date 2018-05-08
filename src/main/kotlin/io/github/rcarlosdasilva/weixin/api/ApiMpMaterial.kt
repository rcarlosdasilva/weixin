package io.github.rcarlosdasilva.weixin.api

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import io.github.rcarlosdasilva.weixin.core.ExecuteException
import io.github.rcarlosdasilva.weixin.handler.FormData
import io.github.rcarlosdasilva.weixin.handler.ResponseParser
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.request.Article
import io.github.rcarlosdasilva.weixin.model.response.*
import io.github.rcarlosdasilva.weixin.terms.MATERIAL_FILE_UPLOAD_KEY
import io.github.rcarlosdasilva.weixin.terms.MATERIAL_VIDEO_FORM_INTRODUCTION
import io.github.rcarlosdasilva.weixin.terms.MATERIAL_VIDEO_FORM_KEY
import io.github.rcarlosdasilva.weixin.terms.MATERIAL_VIDEO_FORM_TITLE
import io.github.rcarlosdasilva.weixin.terms.data.MaterialType
import java.io.File

/**
 * 公众号素材管理相关API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpMaterial(account: Mp) : Api(account) {

  /**
   * 新增临时素材
   *
   * 公众号经常有需要用到一些临时性的多媒体素材的场景，例如在使用接口特别是发送消息时，对多媒体文件、多媒体消息的获取和调用等操作，
   * 是通过media_id来进行的。素材管理接口对所有认证的订阅号和服务号开放。通过本接口，公众号可以新增临时素材（即上传临时多媒体文件）。
   *
   * 注意点：
   * 1. 临时素材media_id是可复用的。
   * 2. 媒体文件在微信后台保存时间为3天，即3天后media_id失效。
   * 3. 上传临时素材的格式、大小限制与公众平台官网一致。
   *    图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
   *    语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
   *    视频（video）：10MB，支持MP4格式
   *    缩略图（thumb）：64KB，支持JPG格式
   * 4. 需使用https调用本接口。
   *
   * @param type 素材类型，不包含图文
   * @param fileName 文件名
   * @param file 素材文件
   * @return see [MaterialAddTemporaryResponse]
   */
  fun addTemporaryMaterial(type: MaterialType, fileName: String, file: File): MaterialAddTemporaryResponse {
    if (type == MaterialType.NEWS) {
      throw ExecuteException("临时素材不支持图文类型")
    }

    return upload(
      MaterialAddTemporaryResponse::class.java,
      MaterialAddTemporaryRequest(type.text),
      MATERIAL_FILE_UPLOAD_KEY,
      fileName, file, null
    )
  }

  /**
   * 获取临时素材
   *
   * 公众号可以使用本接口获取临时素材（即下载临时的多媒体文件）。请注意，视频文件不支持https下载，调用该接口需http协议。本接口即为原“下载多媒体文件”接口。
   *
   * @param materialId 媒体文件ID
   * @return 二进制流
   */
  fun getTemporaryMaterial(materialId: String): ByteArray =
    getStream(MaterialGetTemporaryRequest(materialId)).let { uncertainStream ->
      // 该接口如果是图片，则返回文件流，如是视频则返回json字符串
      val result = uncertainStream.use { readStream(it) }

      return try {
        // 假设返回的是json字符串
        val jsonText = String(result)
        // 尝试解析前面请求返回的json字符串
        val videoResponse = ResponseParser.parse(MaterialGetTemporaryWithVideoResponse::class.java, jsonText)
        // 如果能执行到这里，代表是视频，则再发一个请求将视频文件流拉回，否则上边会解析出错，直接返回前面请求的文件流
        getStream(MaterialGetTemporaryVideoRequest(videoResponse.videoUrl!!)).use { readStream(it) }
      } catch (ex: Exception) {
        result
      }
    }

  /**
   * 获取临时素材（高清语音素材获取接口）
   *
   * 公众号可以使用本接口获取从JSSDK的uploadVoice接口上传的临时语音素材，格式为speex，16K采样率。
   * 该音频比上文的临时素材获取接口（格式为amr，8K采样率）更加清晰，适合用作语音识别等对音质要求较高的业务。
   *
   * @param materialId 媒体文件ID
   * @return 二进制流
   */
  fun getTemporaryMaterialWithHqAudio(materialId: String): ByteArray =
    readStream(getStream(MaterialGetTemporaryWithHqAudioRequest(materialId)))

  /**
   * 新增其他类型永久素材（不包括视频，和图文）
   *
   * 通过POST表单来调用接口，表单id为media，包含需要上传的素材内容，有filename、filelength、content-type等信息。请注意：图片素材将进入公众平台官网素材管理模块中的默认分组。
   *
   * 如需新增视频素材，请使用 [.addTimelessMaterialVideo]
   *
   * @param type 素材类型
   * @param fileName 文件名
   * @param file 素材文件
   * @return see [MaterialAddTimelessResponse]
   */
  fun addTimelessMaterial(type: MaterialType, fileName: String, file: File): MaterialAddTimelessResponse {
    if (type == MaterialType.VIDEO) {
      throw ExecuteException("如需新增视频素材，请使用addTimelessMaterialVideo方法")
    }

    return upload(
      MaterialAddTimelessResponse::class.java,
      MaterialAddTimelessRequest(type.text),
      MATERIAL_FILE_UPLOAD_KEY,
      fileName,
      file,
      null
    )
  }

  /**
   * 新增其他类型永久素材（视频）
   *
   * 新增永久视频素材需特别注意 在上传视频素材时需要POST另一个表单，id为description，包含素材的描述信息，内容格式为JSON
   *
   * @param fileName 文件名
   * @param file 素材文件
   * @param title 视频素材的标题
   * @param description 视频素材的描述
   * @return see [MaterialAddTimelessResponse]
   */
  fun addTimelessMaterialVideo(
    fileName: String,
    file: File,
    title: String,
    description: String
  ): MaterialAddTimelessResponse {
    val obj = JsonObject().apply {
      addProperty(MATERIAL_VIDEO_FORM_TITLE, title)
      addProperty(MATERIAL_VIDEO_FORM_INTRODUCTION, description)
    }

    return upload(
      MaterialAddTimelessResponse::class.java,
      MaterialAddTimelessRequest(MaterialType.VIDEO.text),
      MATERIAL_FILE_UPLOAD_KEY,
      fileName,
      file,
      listOf(FormData(MATERIAL_VIDEO_FORM_KEY, obj.toString()))
    )
  }

  /**
   * 新增永久图文素材，图文内出现的图片使用 [addNewsImage] 获取url
   *
   * 公众号的素材库保存总数量有上限：图文消息素材、图片素材上限为5000，其他类型为1000。
   * 图文消息的具体内容中，微信后台将过滤外部的图片链接，图片url需通过"上传图文消息内的图片获取URL"接口上传图片获取。
   * "上传图文消息内的图片获取URL"接口所上传的图片，不占用公众号的素材库中图片数量的5000个的限制，图片仅支持jpg/png格式，大小必须在1MB以下。
   *
   * @param articles 图文列表
   * @return 新增的图文消息素材的media_id
   */
  fun addTimelessMaterialNews(articles: List<Article>): String =
    post(MaterialAddTimelessResponse::class.java, MaterialAddTimelessNewsRequest(articles)).url!!

  /**
   * 获取永久素材
   *
   * 在新增了永久素材后，开发者可以根据media_id通过本接口下载永久素材。公众号在公众平台官网素材管理模块中新建的永久素材，
   * 可通过"获取素材列表"获知素材的media_id。临时素材无法通过本接口获取
   *
   * 如果是视频消息素材，则返回视频的标题、描述与下载地址，因为视频较大，默认不下载。
   * 其他类型的素材消息，则响应的直接为素材的内容，开发者可以自行保存为文件。
   *
   * @param materialId 要获取的素材的media_id
   * @return see [MaterialGetTimelessResponse]
   */
  fun getTimelessMaterial(materialId: String): MaterialGetTimelessResponse =
    try {
      post(MaterialGetTimelessResponse::class.java, MaterialGetTimelessRequest(materialId))
    } catch (ex: JsonSyntaxException) {
      // Json字符串解析错误，尝试获取二进制流（文件），可能是在获取永久图片、音频素材
      postStream(MaterialGetTimelessRequest(materialId)).use { readStream(it) }.let {
        MaterialGetTimelessResponse().apply { stream = it }
      }
    }

  /**
   * 删除永久素材
   *
   * 在新增了永久素材后，开发者可以根据本接口来删除不再需要的永久素材，节省空间。
   * 请注意：
   * 1. 请谨慎操作本接口，因为它可以删除公众号在公众平台官网素材管理模块中新建的图文消息、
   * 语音、视频等素材（但需要先通过获取素材列表来获知素材的media_id）
   * 2. 临时素材无法通过本接口删除
   * 3. 调用该接口需https协议
   *
   * @param materialId 要删除的素材的media_id
   * @return 是否成功
   */
  fun deleteTimelessMaterial(materialId: String): Boolean =
    post(Boolean::class.java, MaterialDeleteTimelessRequest(materialId))

  /**
   * 修改永久图文素材
   *
   * 开发者可以通过本接口对永久图文素材进行修改。
   * 请注意：
   * 1. 也可以在公众平台官网素材管理模块中保存的图文消息（永久图文素材）
   * 2. 调用该接口需https协议
   *
   * @param materialId 要修改的图文消息的media_id
   * @param index 要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
   * @param article 图文内容
   * @return 是否成功
   */
  fun updateTimelessMaterialNews(materialId: String, index: Int, article: Article): Boolean =
    post(Boolean::class.java, MaterialUpdateTimelessNewsRequest(materialId, index, article))

  /**
   * 获取永久素材总数
   *
   * 开发者可以根据本接口来获取永久素材的列表，需要时也可保存到本地。
   * 请注意：
   * 1. 永久素材的总数，也会计算公众平台官网素材管理中的素材
   * 2. 图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
   * 3. 调用该接口需https协议
   *
   * @return see [MaterialCountTimelessResponse]
   */
  fun countTimelessMaterial(): MaterialCountTimelessResponse =
    get(MaterialCountTimelessResponse::class.java, MaterialCountTimelessRequest())

  /**
   * 获取永久素材列表
   *
   * 在新增了永久素材后，开发者可以分类型获取永久素材的列表。
   * 请注意：
   * 1. 获取永久素材的列表，也包含公众号在公众平台官网素材管理模块中新建的图文消息、语音、视频等素材
   * 2. 临时素材无法通过本接口获取
   * 3. 调用该接口需https协议
   *
   * @param type 素材类型
   * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
   * @param count 返回素材的数量，取值在1到20之间
   * @return see [MaterialListTimelessResponse]
   */
  fun listTimelessMaterial(type: MaterialType, offset: Int, count: Int): MaterialListTimelessResponse =
    post(MaterialListTimelessResponse::class.java, MaterialListTimelessRequest(type.text, offset, count))

  /**
   * 上传图文消息内的图片获取URL
   *
   * 本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。
   *
   * @param fileName 文件名
   * @param file 图片文件
   * @return 上传图片的URL，可用于后续群发中，放置到图文消息中
   */
  fun addNewsImage(fileName: String, file: File): String =
    upload(
      Material::class.java,
      MaterialAddNewsImageRequest(),
      MATERIAL_FILE_UPLOAD_KEY,
      fileName,
      file,
      null
    ).url!!

  /**
   * 上传图文消息素材（群发）
   *
   * @param articles 图文内容列表
   * @return see [MaterialAddNewsResponse]
   */
  @Deprecated(
    "接口地址为 media/uploadnews，与另一个接口 material/add_news作用相同，只保留一个即可",
    ReplaceWith(".addTimelessMaterialNews")
  )
  fun addNews(articles: List<Article>): MaterialAddNewsResponse =
    post(MaterialAddNewsResponse::class.java, MaterialAddNewsRequest(articles))

  /**
   * 转换视频素材media_id
   *
   * 请注意，此处视频的media_id需通过POST请求到下述接口特别地得到：
   * https://api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN
   *
   * 关于这个接口的具体用法，见微信文档的“消息管理 =》 发送消息-群发接口和原创校验”章节
   *
   * @param materialId 通过基础支持中的上传下载多媒体文件得到media_id
   * @param title 标题
   * @param description 描述
   * @return see [MaterialTransformVideoResponse]
   */
  fun transformVideo(
    materialId: String, title: String,
    description: String
  ): MaterialTransformVideoResponse =
    post(
      MaterialTransformVideoResponse::class.java,
      MaterialTransformVideoRequest(materialId, title, description)
    )

}