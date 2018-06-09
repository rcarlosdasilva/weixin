package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName

class CustomerServiceAccount {
  /**
   * 客服编号.
   */
  @SerializedName("kf_id")
  val id: Int = 0
  /**
   * 完整客服帐号，格式为：帐号前缀@公众号微信号.
   */
  @SerializedName("kf_account")
  val account: String? = null
  /**
   * 客服昵称.
   */
  @SerializedName("kf_nick")
  val nickname: String? = null
  /**
   * 客服头像.
   */
  @SerializedName("kf_headimgurl")
  val avatar: String? = null
  /**
   * 客服在线状态，目前为：1、web 在线.
   */
  val status: Int = 0
  /**
   * 如果客服帐号尚未绑定微信号，但是已经发起了一个绑定邀请，则此处显示绑定邀请的微信号.
   */
  @SerializedName("invite_wx")
  val invitee: String? = null
  /**
   * 如果客服帐号尚未绑定微信号，但是已经发起过一个绑定邀请，邀请的过期时间，为unix 时间戳.
   */
  @SerializedName("invite_expire_time")
  val inviteExpire: String? = null
  /**
   * 邀请的状态，有等待确认“waiting”，被拒绝“rejected”，过期“expired”.
   */
  @SerializedName("invite_status")
  val inviteStatus: String? = null
  /**
   * 客服当前正在接待的会话数.
   */
  @SerializedName("accepted_case")
  val acceptedCase: Int = 0
}

class CustomerServiceMessageRecord {
  /**
   * 关注用户open_id.
   */
  @SerializedName("openid")
  val openId: String? = null
  /**
   * 操作码，2002（客服发送信息），2003（客服接收消息）.
   */
  @SerializedName("opercode")
  val operationCode: Int = 0
  /**
   * 聊天记录内容.
   */
  val text: String? = null
  /**
   * 操作时间，unix时间戳.
   */
  val time: Long = 0
  /**
   * 完整客服帐号，格式为：帐号前缀@公众号微信号.
   */
  @SerializedName("worker")
  val account: String? = null
}

open class CustomerServiceSession {
  /**
   * 完整客服帐号，格式为：帐号前缀@公众号微信号.
   */
  @SerializedName("kf_account")
  val account: String? = null
  /**
   * 关注的用户open_id.
   */
  @SerializedName("openid")
  val openId: String? = null
  /**
   * 会话接入的时间.
   */
  @SerializedName("createtime")
  val createTime: Long = 0
  /**
   * 关注用户的最后一条消息的时间.
   */
  @SerializedName("latest_time")
  val latestTime: Long = 0
}


class CustomerServiceAccountListOnlineResponse {
  /**
   * 客服列表.
   */
  @SerializedName("kf_online_list")
  val accounts: List<CustomerServiceAccount>? = null
}

class CustomerServiceAccountListResponse {
  /**
   * 客服列表.
   */
  @SerializedName("kf_list")
  val accounts: List<CustomerServiceAccount>? = null
}

class CustomerServiceMessageRecordsResponse {
  /**
   * 本次返回聊天记录条数.
   */
  @SerializedName("number")
  val size: Int = 0
  /**
   * 本次聊天记录结束的信息id，将此id放入下次请求的messageId中，以继续获取后续的记录.
   */
  @SerializedName("msgid")
  val messageId: Long = 0
  /**
   * 聊天记录列表.
   */
  @SerializedName("recordlist")
  val records: List<CustomerServiceMessageRecord>? = null
}

class CustomerServiceSessionListResponse {
  /**
   * 会话列表.
   */
  @SerializedName("sessionlist")
  val sessions: List<CustomerServiceSession>? = null
}

class CustomerServiceSessionStatusResponse : CustomerServiceSession()

class CustomerServiceSessionListOnHoldResponse {
  /**
   * 未接入会话数量.
   */
  val count: Int = 0
  /**
   * 未接入会话列表，最多返回100条数据，按照来访顺序.
   */
  @SerializedName("waitcaselist")
  val sessions: List<CustomerServiceSession>? = null
}
