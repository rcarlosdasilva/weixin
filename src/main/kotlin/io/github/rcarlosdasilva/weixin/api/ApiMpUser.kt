package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.core.ExecuteException
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.UserListResponse
import io.github.rcarlosdasilva.weixin.model.response.UserOpenIdListResponse
import io.github.rcarlosdasilva.weixin.model.response.UserResponse
import io.github.rcarlosdasilva.weixin.terms.data.Language
import mu.KotlinLogging

/**
 * 公众号用户相关API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpUser(account: Mp) : Api(account) {

  private val logger = KotlinLogging.logger { }

  /**
   * 设置用户备注名
   *
   * 开发者可以通过该接口对指定用户设置备注名，该接口暂时开放给微信认证的服务号
   *
   * @param openId OpenId
   * @param name 新的备注名，长度必须小于30字符
   * @return 是否成功
   */
  fun remarkName(openId: String, name: String): Boolean =
      post(Boolean::class.java, UserRemarkNameRequest(openId, name))

  /**
   * 获取用户信息
   *
   * @param openId OpenId
   * @param language 语言
   * @return [UserResponse] 用户信息
   **/
  @JvmOverloads
  fun getUserInfo(openId: String, language: Language = Language.ZH_CN): UserResponse =
      get(UserResponse::class.java, UserInfoRequest(openId, language))

  /**
   * 批量获取用户信息
   *
   * @param openIds OpenId列表
   * @param language 语言
   * @return [UserResponse] 用户信息列表
   **/
  @JvmOverloads
  fun getUsersInfo(openIds: List<String>, language: Language = Language.ZH_CN): List<UserResponse> =
      post(UserListResponse::class.java, UserInfoListRequest().apply {
        openIds.forEach { userList.add(UserInfoRequest(it, language)) }
      }).userList!!

  /**
   * 获取公众号的关注者列表
   *
   * 关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
   * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
   *
   * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
   * @return [UserOpenIdListResponse] 列表信息
   */
  @JvmOverloads
  fun listAllUsersOpenId(nextOpenId: String? = null): UserOpenIdListResponse =
      get(UserOpenIdListResponse::class.java, UserOpenIdListRequest(nextOpenId))

  /**
   * 网页授权，拉取用户信息(需scope为 snsapi_userinfo)
   *
   * 通过网页授权获取的access_token，获取用户信息（用户不需要关注公众号，只要授权即可）
   *
   * @param webAccessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
   * @param openId OpenId
   * @param language 语言
   * @return [UserResponse] 用户信息
   */
  @JvmOverloads
  fun getUserInfoByWebAuthorize(
      webAccessToken: String,
      openId: String,
      language: Language = Language.ZH_CN
  ): UserResponse =
      get(UserResponse::class.java, UserInfoByWebAuthorizeRequest(webAccessToken, openId, language))


  /**
   * 获取标签下粉丝列表
   *
   * @param tagId 标签id
   * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
   * @return [UserOpenIdListResponse] 列表信息
   */
  @JvmOverloads
  fun listUsersOpenIdWithTag(tagId: Int, nextOpenId: String? = null): UserOpenIdListResponse =
      post(UserOpenIdListResponse::class.java, UserOpenIdListWithTagRequest(tagId, nextOpenId))

  /**
   * 获取黑名单中的用户列表
   *
   * 公众号可通过该接口来获取帐号的黑名单列表，黑名单列表由一串 OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
   * 该接口每次调用最多可拉取 10000 个OpenID，当列表数较多时，可以通过多次拉取的方式来满足需求。
   *
   * @param nextOpenId 当 begin_openid 为空时，默认从开头拉取
   * @return [UserOpenIdListResponse]
   */
  @JvmOverloads
  fun listUsersInBlack(nextOpenId: String? = null): UserOpenIdListResponse =
      post(UserOpenIdListResponse::class.java, BlackListOpenIdListRequest(nextOpenId))

  /**
   * 把用户拉黑
   *
   * @param openIds 需要拉入黑名单的用户的openid，一次拉黑最多允许20个
   * @return 成功
   */
  fun appendUsersToBlack(openIds: List<String>): Boolean {
    if (openIds.size > 20) {
      logger.warn { "一次拉黑用户数量不能超过20人" }
      throw ExecuteException("超出人数限制")
    }
    return post(Boolean::class.java, BlackListAppendRequest().apply {
      openIds.forEach { userList.add(it) }
    })
  }

  /**
   * 取消拉黑用户
   *
   * @param openIds 需要取消拉黑名单的用户的openid，一次拉黑最多允许20个
   * @return 成功
   */
  fun cancelUsersFromBlack(openIds: List<String>): Boolean {
    if (openIds.size > 20) {
      logger.warn { "一次取消黑名单用户数量不能超过20人" }
      throw ExecuteException("超出人数限制")
    }
    return post(Boolean::class.java, BlackListCancelRequest().apply {
      openIds.forEach { userList.add(it) }
    })
  }

}