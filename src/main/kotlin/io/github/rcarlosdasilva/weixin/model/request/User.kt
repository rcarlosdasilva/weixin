package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*
import io.github.rcarlosdasilva.weixin.terms.data.Language

/**
 * 设置用户备注名请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserRemarkNameRequest(
  @SerializedName("openid") private val openId: String,
  @SerializedName("remark") private val name: String
) : MpRequest() {
  init {
    this.path = URL_USER_REMARK_NAME
  }
}

/**
 * 获取用户信息请求模型，批量请求用户信息时使用，无需Token
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserInfoRequest(
  @SerializedName("openid") private val openId: String,
  @SerializedName("lang") private val language: Language?
) : MpRequest() {
  init {
    this.path = URL_USER_INFO
  }

  override fun toString(): String = "$path?access_token=$accessToken&openid=$openId&lang=$language"
}

/**
 * 批量获取用户信息请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserInfoListRequest : MpRequest() {
  @SerializedName("user_list")
  val userList = mutableListOf<UserInfoRequest>()

  init {
    this.path = URL_USER_INFO_LIST
  }
}

/**
 * 获取用户列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserOpenIdListRequest(private val nextOpenId: String? = null) : MpRequest() {
  init {
    this.path = URL_USER_ALL_OPENID_LIST
  }

  override fun toString(): String = "$path?access_token=$accessToken" + (nextOpenId?.let { "&next_openid=$it" } ?: "")
}

/**
 * 获取网页授权下用户信息请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserInfoByWebAuthorizeRequest(
  private val webAccessToken: String,
  private val openId: String,
  private val language: Language
) : MpRequest() {
  init {
    this.path = URL_USER_INFO_BY_WEB_AUTHORIZE
  }

  override fun toString(): String = "$path?access_token=$webAccessToken&openid=$openId&lang=$language"
}

/**
 * 获取标签下用户列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserOpenIdListWithTagRequest(
  @SerializedName("tagid") private val tagId: Int,
  @SerializedName("next_openid") private val nextOpenId: String? = null
) : MpRequest() {
  init {
    this.path = URL_USER_OPENID_LIST_WITH_TAG
  }
}

/**
 * 获取黑名单列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class BlackListOpenIdListRequest(
  @SerializedName("begin_openid") private val beginOpenId: String? = null
) : MpRequest() {
  init {
    this.path = URL_BLACK_LIST_QUERY
  }
}

/**
 * 拉黑用户请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class BlackListAppendRequest : MpRequest() {
  @SerializedName("openid_list")
  val userList = mutableListOf<String>()

  init {
    this.path = URL_BLACK_LIST_APPEND
  }
}

/**
 * 取消拉黑用户请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class BlackListCancelRequest : MpRequest() {
  @SerializedName("openid_list")
  val userList = mutableListOf<String>()

  init {
    this.path = URL_BLACK_LIST_CANCEL
  }
}
