package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.GLOBAL_TRUE_NUMBER

class Article {
  /**
   * 图文消息的标题
   */
  val title: String? = null
  /**
   * 图文消息的封面图片素材id（必须是永久mediaID）
   */
  @SerializedName("thumb_media_id")
  val thumbnailMaterialId: String? = null
  /**
   * 作者
   */
  val author: String? = null
  /**
   * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
   */
  val digest: String? = null
  @SerializedName("show_cover_pic")
  private val showCover: Int = 0
  /**
   * 是否显示封面，0为false，即不显示，1为true，即显示
   */
  val isShowCover: Boolean
    get() = showCover == GLOBAL_TRUE_NUMBER
  /**
   * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
   */
  val content: String? = null
  /**
   * 图文页的URL
   */
  val url: String? = null
  /**
   * 图文消息的原文地址，即点击“阅读原文”后的URL
   */
  @SerializedName("content_source_url")
  val sourceUrl: String? = null
  @SerializedName("need_open_comment")
  private val openComment: Int = 0
  /**
   * 是否打开评论，0不打开，1打开
   */
  val isOpenComment: Boolean
    get() = openComment == GLOBAL_TRUE_NUMBER
  @SerializedName("only_fans_can_comment")
  private val onlyFansComment: Int = 0
  /**
   * 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
   */
  val isOnlyFansComment: Boolean
    get() = onlyFansComment == GLOBAL_TRUE_NUMBER
}

class ArticleCollection {
  /**
   * 图文列表
   */
  @SerializedName("news_item")
  val articles: List<Article>? = null
}

abstract class AbstractMaterial {
  /**
   * 媒体文件/图文消息上传后获取的唯一标识
   */
  @SerializedName("media_id")
  val id: String? = null
  /**
   * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图），图文消息（news）
   */
  val type: String? = null
}

class Material {
  /**
   * 素材media_id
   */
  @SerializedName("media_id")
  val id: String? = null
  /**
   * 文件名称
   */
  val name: String? = null
  /**
   * 素材url
   */
  val url: String? = null
  /**
   * 这篇图文消息素材的最后更新时间
   */
  @SerializedName("update_time")
  val updateTime: Long = 0
  /**
   * 图文信息
   */
  val content: ArticleCollection? = null
}

class MaterialAddNewsResponse : AbstractMaterial() {
  /**
   * 媒体文件上传时间
   */
  @SerializedName("created_at")
  val createAt: Long = 0
  /**
   * 上传图片的URL，可用于后续群发中，放置到图文消息中
   */
  val url: String? = null
}

class MaterialAddTemporaryResponse : AbstractMaterial() {
  /**
   * 媒体文件上传时间戳
   */
  @SerializedName("created_at")
  val createdAt: Long = 0
  /**
   * 媒体文件上传后，获取时的唯一标识(用于缩略图文件)
   */
  @SerializedName("thumb_media_id")
  val thumbnailMaterialId: String? = null
}

class MaterialAddTimelessResponse : AbstractMaterial() {
  /**
   * 新增的图片素材的图片URL（仅新增图片素材时会返回该字段）
   */
  val url: String? = null
}

class MaterialCountTimelessResponse {
  /**
   * 语音总数量
   */
  @SerializedName("voice_count")
  val voiceCount: Int = 0
  /**
   * 视频总数量
   */
  @SerializedName("video_count")
  val videoCount: Int = 0
  /**
   * 图片总数量
   */
  @SerializedName("image_count")
  val imageCount: Int = 0
  /**
   * 图文总数量
   */
  @SerializedName("news_count")
  val newsCount: Int = 0
}

class MaterialGetTemporaryWithVideoResponse {
  /**
   * 视频素材的路径
   */
  @SerializedName("video_url")
  val videoUrl: String? = null
}

class MaterialGetTimelessResponse {
  /**
   * 如果是除视频或图文之外的其他类型的素材消息，则响应的直接为素材的内容，开发者可以自行保存为文件
   */
  var stream: ByteArray? = null
  /**
   * 如果返回的是视频消息素材，视频标题
   */
  val title: String? = null
  /**
   * 如果返回的是视频消息素材，视频描述
   */
  val description: String? = null
  /**
   * 如果返回的是视频消息素材，视频下载地址
   */
  @SerializedName("down_url")
  val downloadUrl: String? = null
  /**
   * 如果请求的素材为图文消息，获取图文列表
   */
  @SerializedName("news_item")
  val articles: List<Article>? = null
}

class MaterialListTimelessResponse {
  /**
   * 该类型的素材的总数
   */
  @SerializedName("total_count")
  val totalCount: Int = 0
  /**
   * 本次调用获取的素材的数量
   */
  @SerializedName("item_count")
  val itemCount: Int = 0
  /**
   * 素材内容列表
   */
  @SerializedName("item")
  val items: List<Material>? = null
}

class MaterialTransformVideoResponse : AbstractMaterial() {
  /**
   * 创建时间
   */
  val createAt: Long = 0
}
