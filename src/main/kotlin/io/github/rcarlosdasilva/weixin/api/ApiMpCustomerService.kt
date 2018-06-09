package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.*
import io.github.rcarlosdasilva.weixin.terms.CUSTOM_AVATAR_UPLOAD_KEY
import java.io.File
import java.util.*

/**
 * 公众号新版客服API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpCustomerService(private val account: Mp) : Api(account) {

  /**
   * 获取客服的基本信息
   *
   * @return 客服列表
   */
  fun accounts(): List<CustomerServiceAccount>? =
      get(CustomerServiceAccountListResponse::class.java, CustomerServiceAccountListRequest()).accounts

  /**
   * 获取在线客服的基本信息
   *
   * @return 客服列表
   */
  fun accountsOnline(): List<CustomerServiceAccount>? =
      get(CustomerServiceAccountListOnlineResponse::class.java, CustomerServiceAccountListOnlineRequest()).accounts

  /**
   * 添加客服账号（只是添加账号，并未绑定微信账号）
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @param nickname 客服昵称，最长16个字
   * @return 是否添加
   */
  fun addAccount(accountPrefix: String, nickname: String): Boolean =
      post(Boolean::class.java, CustomerServiceAccountAppendRequest("$accountPrefix@${account.mpId}", nickname))

  /**
   * 邀请个人微信绑定客服账号
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @param wxId 接收绑定邀请的客服微信号，可以是手机号、微信号、QQ号
   * @return 是否绑定
   */
  fun bindAccount(accountPrefix: String, wxId: String): Boolean =
      post(Boolean::class.java, CustomerServiceAccountBindingRequest("$accountPrefix@${account.mpId}", wxId))

  /**
   * 删除客服账号
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @return 是否删除
   */
  fun deleteAccount(accountPrefix: String): Boolean =
      get(Boolean::class.java, CustomerServiceAccountDeleteRequest("$accountPrefix@${account.mpId}"))

  /**
   * 更新客服昵称
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @param nickname 客服昵称，最长16个字
   * @return 是否更新
   */
  fun updateAccount(accountPrefix: String, nickname: String): Boolean =
      post(Boolean::class.java, CustomerServiceAccountUpdateRequest("$accountPrefix@${account.mpId}", nickname))

  /**
   * 上传客服头像
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @param fileName 文件名
   * @param file 文件
   * @return 是否上传
   */
  fun uploadAccountAvatar(accountPrefix: String, fileName: String, file: File): Boolean =
      upload(Boolean::class.java,
          CustomerServiceAccountUploadAvatarRequest("$accountPrefix@${account.mpId}"),
          CUSTOM_AVATAR_UPLOAD_KEY,
          fileName,
          file,
          null)

  /**
   * 创建会话
   *
   * 在客服和用户之间创建一个会话，如果该客服和用户会话已存在，则直接返回0。指定的客服帐号必须已经绑定微信号且在线。
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @param openId 关注用户open_id
   * @return 是否创建
   */
  fun createSession(accountPrefix: String, openId: String): Boolean =
      post(Boolean::class.java, CustomerServiceSessionCreateRequest("$accountPrefix@${account.mpId}", openId))

  /**
   * 关闭会话
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @param openId 关注用户open_id
   * @return 是否关闭
   */
  fun closeSession(accountPrefix: String, openId: String): Boolean =
      post(Boolean::class.java, CustomerServiceSessionCloseRequest("$accountPrefix@${account.mpId}", openId))

  /**
   * 获取一个客户的会话，如果不存在，则kf_account为空
   *
   * @param openId 关注用户open_id
   * @return 会话状态
   */
  fun sessionStatus(openId: String): CustomerServiceSession =
      get(CustomerServiceSessionStatusResponse::class.java, CustomerServiceSessionStatusRequest(openId))

  /**
   * 获取指定客服的会话列表
   *
   * @param accountPrefix 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   * 这里account只需传账号前缀
   * @return 会话状态列表
   */
  fun sessions(accountPrefix: String): List<CustomerServiceSession>? =
      get(CustomerServiceSessionListResponse::class.java, CustomerServiceSessionListRequest("$accountPrefix@${account.mpId}")).sessions

  /**
   * 获取未接入会话列表
   *
   * @return 未接入会话列表
   */
  fun sessionsOnHold(): CustomerServiceSessionListOnHoldResponse =
      get(CustomerServiceSessionListOnHoldResponse::class.java, CustomerServiceSessionListOnHoldRequest())

  /**
   * 获取聊天记录
   *
   * 聊天记录中，对于图片、语音、视频，分别展示成文本格式的 image, voice, video。
   * 对于较可能包含重要信息的图片消息，后续将提供图片拉取URL，近期将上线。
   *
   * @param start 起始时间
   * @param end 结束时间，每次查询时段不能超过24小时
   * @param size 每次获取的最大条数，最大10000条
   * @param messageId 消息id顺序从小到大，从1开始
   * @return 聊天记录列表
   */
  fun messageRecords(start: Date, end: Date, size: Int, messageId: Long): CustomerServiceMessageRecordsResponse =
      post(CustomerServiceMessageRecordsResponse::class.java, CustomerServiceMessageRecordsRequest(start.time, end.time, messageId, size))

}