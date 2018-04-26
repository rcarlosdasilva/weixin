package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.GLOBAL_TRUE_NUMBER

class MenuCreateResponse {
  /**
   * 菜单id（个性化菜单）
   */
  @SerializedName("menuid")
  val menuId: Long = 0
}

class MenuInfoResponse {
  /**
   * 自定义菜单
   */
  val menu: BasicMenu? = null
  /**
   * 个性化菜单
   */
  @SerializedName("conditionalmenu")
  val conditionalMenu: List<Menu>? = null
}

class MenuCompleteResponse {
  @SerializedName("is_menu_open")
  private val menuOpened: Int = 0
  /**
   * 菜单是否开启
   *
   * @return is opened
   */
  val isMenuOpened: Boolean
    get() = menuOpened == GLOBAL_TRUE_NUMBER
  /**
   * 菜单信息
   */
  @SerializedName("selfmenu_info")
  val menu: CompleteMenu? = null
}


open class Menu {
  /**
   * 菜单id
   */
  @SerializedName("menuid")
  val menuId: Long = 0
  /**
   * 匹配规则
   */
  @SerializedName("matchrule")
  val matchRule: MatchRule? = null
}

class BasicMenu : Menu() {
  /**
   * 菜单按钮列表
   */
  @SerializedName("button")
  val buttons: List<BasicButton>? = null
}

class CompleteMenu : Menu() {
  /**
   * 菜单按钮列表
   */
  @SerializedName("button")
  val buttons: List<CompleteButton>? = null
}

open class Button {
  /**
   * 菜单名称
   */
  val name: String? = null
  /**
   * 菜单的类型，公众平台官网上能够设置的菜单类型有view（跳转网页）、text（返回文本，下同）、img、photo、video、
   * voice。使用API设置的则有8种，详见《自定义菜单创建接口》
   */
  val type: String? = null
  /**
   * value、url、key等字段，对于不同的菜单类型，value的值意义不同。
   *
   * 官网上设置的自定义菜单：
   * - Text:保存文字到value；
   * - Img、voice：保存mediaID到value；
   * - Video：保存视频下载链接到value；
   * - News：保存图文消息到news_info， 同时保存mediaID到value；
   * - View：保存链接到url。
   *
   * 使用API设置的自定义菜单：
   * - click、scancode_push、scancode_waitmsg、 pic_sysphoto、pic_photo_or_album、pic_weixin、location_select：保存值到key；
   * - view：保存链接到url
   */
  val key: String? = null
  /**
   * value、url、key等字段，对于不同的菜单类型，value的值意义不同。
   *
   * 官网上设置的自定义菜单：
   * - Text:保存文字到value；
   * - Img、voice：保存mediaID到value；
   * - Video：保存视频下载链接到value；
   * - News：保存图文消息到news_info， 同时保存mediaID到value；
   * - View：保存链接到url。
   *
   * 使用API设置的自定义菜单：
   * - click、scancode_push、scancode_waitmsg、 pic_sysphoto、pic_photo_or_album、pic_weixin、location_select：保存值到key；
   * - view：保存链接到url
   */
  val value: String? = null
  /**
   * value、url、key等字段，对于不同的菜单类型，value的值意义不同。
   *
   * 官网上设置的自定义菜单：
   * - Text:保存文字到value；
   * - Img、voice：保存mediaID到value；
   * - Video：保存视频下载链接到value；
   * - News：保存图文消息到news_info， 同时保存mediaID到value；
   * - View：保存链接到url。
   *
   * 使用API设置的自定义菜单：
   * - click、scancode_push、scancode_waitmsg、 pic_sysphoto、pic_photo_or_album、pic_weixin、location_select：保存值到key；
   * - view：保存链接到url
   */
  val url: String? = null
  /**
   * 调用新增永久素材接口返回的合法media_id
   */
  @SerializedName("media_id")
  val mediaId: String? = null
  val appid: String? = null
  @SerializedName("pagepath")
  val path: String? = null
  /**
   * 图文消息的信息
   */
  @SerializedName("news_info")
  val mediaCollection: MediaCollection? = null
}

class BasicButton : Button() {
  @SerializedName("sub_button")
  val subButtons: List<Button>? = null
}

class CompleteButton : Button() {
  @SerializedName("sub_button")
  val subButtons: SubButton? = null
}

class SubButton {
  @SerializedName("list")
  val buttons: List<Button>? = null
}

class MatchRule {
  /**
   * 用户标签的id，可通过用户标签管理接口获取
   */
  @SerializedName("tag_id")
  val tagId: Int = 0
  /**
   * 性别：男（1）女（2）
   */
  val sex: Int = 0
  /**
   * 国家信息
   */
  val country: String? = null
  /**
   * 省份信息
   */
  val province: String? = null
  /**
   * 城市信息
   */
  val city: String? = null
  /**
   * 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)
   */
  @SerializedName("client_platform_type")
  val clientPlatform: Int = 0
  /**
   * 语言信息
   */
  val language: String? = null
}

class MediaCollection {
  /**
   * 多媒体列表
   */
  @SerializedName("list")
  val media: List<Media>? = null
}

class Media {
  /**
   * 图文消息的标题
   */
  val title: String? = null
  /**
   * 作者
   */
  val author: String? = null
  /**
   * 摘要
   */
  val digest: String? = null
  @SerializedName("show_cover")
  private val showCover: Int = 0
  /**
   * 是否显示封面，0为不显示，1为显示
   */
  val isShowCover = showCover == GLOBAL_TRUE_NUMBER
  /**
   * 封面图片的URL
   */
  @SerializedName("cover_url")
  val coverUrl: String? = null
  /**
   * 正文的URL
   */
  @SerializedName("content_url")
  val contentUrl: String? = null
  /**
   * 原文的URL，若置空则无查看原文入口
   */
  @SerializedName("source_url")
  val sourceUrl: String? = null
}