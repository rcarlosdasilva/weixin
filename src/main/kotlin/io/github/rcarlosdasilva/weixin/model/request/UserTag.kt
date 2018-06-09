package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*

/**
 * 标签
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
data class UserTag(
    val id: Int? = null,
    val name: String? = null
)

/**
 * 创建标签请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserTagCreateRequest(private val tagName: String) : MpRequest() {
  private val tag = UserTag(null, tagName)

  init {
    this.path = URL_USER_TAG_CREATE
  }
}

/**
 * 删除标签请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserTagDeleteRequest(private val id: Int) : MpRequest() {
  private val tag = UserTag(id, null)

  init {
    this.path = URL_USER_TAG_DELETE
  }
}

/**
 * 更新标签请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserTagUpdateRequest(private val id: Int, private val tagName: String) : MpRequest() {
  private val tag = UserTag(id, tagName)

  init {
    this.path = URL_USER_TAG_UPDATE
  }
}

/**
 * 获取标签列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserTagListRequest : MpRequest() {
  init {
    this.path = URL_USER_TAG_LIST
  }
}

/**
 * 给用户设置标签请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserTagTaggingRequest(
    @SerializedName("tagid") private var id: Int,
    @SerializedName("openid_list") private var openIds: List<String>
) : MpRequest() {
  init {
    this.path = URL_USER_TAG_TAGGING_USER
  }
}

/**
 * 为用户取消标签请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserTagUntaggingRequest(
    @SerializedName("tagid") private var id: Int,
    @SerializedName("openid_list") private var openIds: List<String>
) : MpRequest() {
  init {
    this.path = URL_USER_TAG_UNTAGGING_FROM_USER
  }
}

/**
 * 获取用户上的标签列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class UserTagListBasedUserRequest(@SerializedName("openid") private var openId: String) : MpRequest() {
  init {
    this.path = URL_USER_TAG_LIST_BASE_USER
  }
}