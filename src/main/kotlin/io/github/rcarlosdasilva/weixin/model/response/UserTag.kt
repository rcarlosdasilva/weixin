package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName

class UserTag {
  /**
   * 标签id
   */
  val id: Int = 0
  /**
   * 标签名
   */
  val name: String? = null
  /**
   * 标签下用户数
   */
  val count: Int = 0

}

class UserTagCreateResponse {
  val tag: UserTag? = null
}

class UserTagListResponse {
  val tags: List<UserTag>? = null
}

class UserTagListBasedUserResposne {
  @SerializedName("tagid_list")
  val tags: List<Int>? = null
}