package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*

class Menu {
  val buttons = mutableListOf<Button>()
  var matchRule: MatchRule? = null
}

open class SubButton(val name: String, val type: String) {
  var key: String? = null
  var url: String? = null
  @SerializedName("media_id")
  var mediaId: String? = null
  var appid: String? = null
  @SerializedName("pagepath")
  var path: String? = null
}

class Button(name: String, type: String) : SubButton(name, type) {
  @SerializedName("sub_button")
  val subButtons = mutableListOf<SubButton>()
}

open class MatchRule {
  @SerializedName("tag_id")
  var tagId: Int = 0
  var sex: Int = 0
  var country: String? = null
  var province: String? = null
  var city: String? = null
  @SerializedName("client_platform_type")
  var clientPlatform: Int = 0
  var language: String? = null
}

/**
 * 创建菜单请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MenuCreateRequest(@SerializedName("button") private val buttons: List<Button>) : MpRequest() {
  @SerializedName("matchrule")
  var matchRule: MatchRule? = null

  init {
    this.path = URL_MENU_CREATE
  }

  fun withConditional() {
    this.path = URL_MENU_CONDITIONAL_CREATE
  }
}

/**
 * 删除菜单请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MenuDeleteRequest(@SerializedName("menuid") private val menuId: Long? = null) : MpRequest() {
  init {
    this.path = URL_MENU_DELETE
  }

  /**
   * 个性化菜单
   */
  fun withConditional() {
    this.path = URL_MENU_CONDITIONAL_DELETE
  }
}

/**
 * 获取自定义菜单配置请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MenuQueryCompleteRequest : MpRequest() {
  init {
    this.path = URL_MENU_QUERY_COMPLETE
  }
}

/**
 * 查询菜单请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MenuQueryRequest : MpRequest() {
  init {
    this.path = URL_MENU_QUERY
  }
}

/**
 * 测试个性化菜单请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MenuTestRequest(@SerializedName("user_id") private val userLabel: String) : MpRequest() {
  init {
    this.path = URL_MENU_CONDITIONAL_TEST
  }
}
