package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.UserTag
import io.github.rcarlosdasilva.weixin.model.response.UserTagCreateResponse
import io.github.rcarlosdasilva.weixin.model.response.UserTagListBasedUserResposne
import io.github.rcarlosdasilva.weixin.model.response.UserTagListResponse

/**
 * 公众号用户标签API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpUserTag(account: Mp) : Api(account) {

  /**
   * 创建标签
   *
   * @param name 标签名
   * @return 标签id，由微信分配
   */
  fun create(name: String): UserTag = post(UserTagCreateResponse::class.java, UserTagCreateRequest(name)).tag!!

  /**
   * 删除标签
   *
   * @param id 标签id
   * @return 是否删除
   */
  fun delete(id: Int): Boolean = post(Boolean::class.java, UserTagDeleteRequest(id))

  /**
   * 编辑标签
   *
   * @param id 标签id
   * @param newName 新标签名
   * @return 是否更新
   */
  fun update(id: Int, newName: String): Boolean = post(Boolean::class.java, UserTagUpdateRequest(id, newName))

  /**
   * 获取公众号已创建的标签
   *
   * @return 标签列表
   */
  fun list(): List<UserTag> = get(UserTagListResponse::class.java, UserTagListRequest()).tags!!

  /**
   * 批量为用户打标签
   *
   * @param id 标签id
   * @param openIds 关注用户open_id列表
   * @return 是否成功
   */
  fun tag(id: Int, openIds: List<String>): Boolean = post(Boolean::class.java, UserTagTaggingRequest(id, openIds))

  /**
   * 批量为用户取消标签
   *
   * @param id 标签id
   * @param openIds 关注用户open_id列表
   * @return 是否成功
   */
  fun untag(id: Int, openIds: List<String>): Boolean =
      post(Boolean::class.java, UserTagUntaggingRequest(id, openIds))

  /**
   * 获取用户身上的标签列表
   *
   * @param openId 关注用户open_id
   * @return 标签列表
   */
  fun listBasedUser(openId: String): List<Int> =
      post(UserTagListBasedUserResposne::class.java, UserTagListBasedUserRequest(openId)).tags!!

}