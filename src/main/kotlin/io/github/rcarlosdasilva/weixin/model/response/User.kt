package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName


class UserResponse {
  /**
   * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
   */
  val subscribe: Int = 0
  /**
   * 用户的标识，对当前公众号唯一
   */
  @SerializedName("openid")
  val openId: String? = null
  /**
   * 用户的昵称
   */
  val nickname: String? = null
  /**
   * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
   */
  val sex: Int = 0
  /**
   * 用户所在国家
   */
  val country: String? = null
  /**
   * 用户所在省份
   */
  val province: String? = null
  /**
   * 用户所在城市
   */
  val city: String? = null
  /**
   * 用户的语言，简体中文为zh_CN
   */
  val language: String? = null
  /**
   * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
   * 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效
   */
  @SerializedName("headimgurl")
  val headImgUrl: String? = null
  /**
   * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
   */
  @SerializedName("subscribe_time")
  val subscribeTime: Long = 0
  /**
   * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
   */
  @SerializedName("unionid")
  val unionId: String? = null
  /**
   * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
   */
  val remark: String? = null
  /**
   * 用户所在的分组ID（兼容旧的用户分组接口）
   */
  @SerializedName("groupid")
  val groupId: Int = 0
  /**
   * 用户被打上的标签ID列表
   */
  @SerializedName("tagid_list")
  val tagList: List<Int>? = null
}

class UserListResponse {
  /**
   * 用户列表
   */
  @SerializedName("user_info_list")
  val userList: List<UserResponse>? = null
}


class UserOpenIdListResponse {
  /**
   * 关注该公众账号的总用户数
   */
  val total: Int = 0
  /**
   * 拉取的OPENID个数，最大值为10000
   */
  val count: Int = 0
  /**
   * OpenId集合
   */
  @SerializedName("data")
  val openIds: OpenIdCollection? = null
  /**
   * 拉取列表的最后一个用户的OPENID
   */
  @SerializedName("next_openid")
  val lastOpenId: String? = null
}

class OpenIdCollection {
  /**
   * OpenId列表
   */
  @SerializedName("openid")
  val list: List<String>? = null
}
