package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*

/**
 * @param title 标题
 * @param thumbnailMaterialId 图文消息的封面图片素材id（必须是永久mediaID）
 * @param author 作者
 * @param digest 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
 * @param showCover 是否显示封面，0为false，即不显示，1为true，即显示
 * @param content 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
 * @param sourceUrl 图文消息的原文地址，即点击“阅读原文”后的URL
 */
class Article(
  private val title: String,
  @SerializedName("thumb_media_id") private val thumbnailMaterialId: String,
  private val author: String,
  private val digest: String?,
  @Expose private val showCover: Boolean,
  private val content: String,
  @SerializedName("content_source_url") private val sourceUrl: String
) {
  @SerializedName("show_cover_pic")
  private val showCoverPic = if (showCover) GLOBAL_TRUE_NUMBER else GLOBAL_FALSE_NUMBER
  @SerializedName("need_open_comment")
  private var openComment: Int = 0
  @SerializedName("only_fans_can_comment")
  private var onlyFansComment: Int = 0

  /**
   * @param openComment 是否打开评论，0不打开，1打开
   * @param onlyFansComment 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
   */
  constructor(
    title: String,
    thumbnailMaterialId: String,
    author: String,
    digest: String,
    showCover: Boolean,
    content: String,
    sourceUrl: String,
    openComment: Boolean,
    onlyFansComment: Boolean
  ) : this(title, thumbnailMaterialId, author, digest, showCover, content, sourceUrl) {
    this.openComment = if (openComment) GLOBAL_TRUE_NUMBER else GLOBAL_FALSE_NUMBER
    this.onlyFansComment = if (onlyFansComment) GLOBAL_TRUE_NUMBER else GLOBAL_FALSE_NUMBER
  }
}

/**
 * 上传图文消息内的图片获取URL请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialAddNewsImageRequest : MpRequest() {
  init {
    this.path = URL_MATERIAL_ADD_NEWS_IMAGE
  }
}

/**
 * 上传图文消息素材（群发）
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialAddNewsRequest(private val articles: List<Article>) : MpRequest() {
  init {
    this.path = URL_MATERIAL_ADD_NEWS
  }
}

/**
 * 新增素材请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialAddTemporaryRequest(private val type: String) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TEMPORARY_ADD
  }

  override fun toString(): String = "$path?access_token=$accessToken&type=$type"
}

/**
 * 新增图文素材请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialAddTimelessNewsRequest(private val articles: List<Article>) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TIMELESS_ADD_NEWS
  }
}

/**
 * 新增永久素材请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialAddTimelessRequest(private val type: String) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TIMELESS_ADD
  }

  override fun toString(): String = "$path?access_token=$accessToken&type=$type"
}

/**
 * 获取素材总数请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialCountTimelessRequest : MpRequest() {
  init {
    this.path = URL_MATERIAL_TIMELESS_COUNT
  }
}

/**
 * 删除永久素材请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialDeleteTimelessRequest(@SerializedName("media_id") private val materialId: String) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TIMELESS_DELETE
  }
}

/**
 * 获取临时素材文件请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialGetTemporaryRequest(@SerializedName("media_id") private val materialId: String) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TEMPORARY_GET
  }

  override fun toString(): String = "$path?access_token=$accessToken&media_id=$materialId"
}

/**
 * 获取临时视频素材文件请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialGetTemporaryVideoRequest(url: String) : MpRequest() {
  init {
    this.path = url
  }

  override fun toString(): String = this.path!!
}

class MaterialGetTemporaryWithHqAudioRequest(@SerializedName("media_id") private val materialId: String) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TEMPORARY_GET_HQ_AUDIO
  }

  override fun toString(): String = "$path?access_token=$accessToken&media_id=$materialId"
}

/**
 * 获取永久素材请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialGetTimelessRequest(@SerializedName("media_id") private val materialId: String) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TIMELESS_GET
  }
}

/**
 * 获取素材列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialListTimelessRequest(
  private val type: String,
  private val offset: Int,
  private val count: Int
) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TIMELESS_LIST
  }
}

/**
 * 转换视频media_id请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialTransformVideoRequest(
  @SerializedName("media_id") private val materialId: String,
  private val title: String,
  private val description: String
) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TRANSFORM_VIDEO
  }
}

/**
 * 修改永久图文素材请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaterialUpdateTimelessNewsRequest(
  @SerializedName("media_id") private val materialId: String,
  private val index: Int,
  @SerializedName("articles") private val article: Article
) : MpRequest() {
  init {
    this.path = URL_MATERIAL_TIMELESS_UPDATE
  }
}
