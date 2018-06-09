package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*

/**
 * 添加客服账号请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceAccountAppendRequest(
    @SerializedName("kf_account") private val account: String,
    private val nickname: String
) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_ACCOUNT_APPEND
  }
}

/**
 * 邀请绑定客服账号请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceAccountBindingRequest(
    @SerializedName("kf_account") private val account: String,
    @SerializedName("invite_wx") private val wxId: String
) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_ACCOUNT_INVITE_BINDING
  }
}

/**
 * 删除客服账号请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceAccountDeleteRequest(private val account: String) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_ACCOUNT_DELETE
  }

  override fun toString(): String = "$path?access_token=$accessToken&kf_account=$account"
}

/**
 * 获取在线客服列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceAccountListOnlineRequest : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_ACCOUNT_LIST_ONLINE
  }
}

/**
 * 获取客服列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceAccountListRequest : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_ACCOUNT_LIST
  }
}

/**
 * 更新客服信息请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceAccountUpdateRequest(
    @SerializedName("kf_account") private val account: String,
    private val nickname: String
) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_ACCOUNT_UPDATE
  }
}

/**
 * 上传客服头像请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceAccountUploadAvatarRequest(private val account: String) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_ACCOUNT_UPLOAD_AVATAR
  }

  override fun toString(): String = "$path?access_token=$accessToken&kf_account=$account"
}

/**
 * 获取客服聊天记录请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceMessageRecordsRequest(
    @SerializedName("starttime") private val startTime: Long,
    @SerializedName("endtime") private val endTime: Long,
    @SerializedName("msgid") private val messageId: Long,
    @SerializedName("number") private val size: Int
) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_MESSAGE_RECORDS
  }
}

/**
 * 关闭客服会话请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceSessionCloseRequest(
    @SerializedName("kf_account") private val account: String,
    @SerializedName("openid") private val openId: String
) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_SESSION_CLOSE
  }
}

/**
 * 创建客服会话请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceSessionCreateRequest(
    @SerializedName("kf_account") private val account: String,
    @SerializedName("openid") private val openId: String
) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_SESSION_CREATE
  }
}

/**
 * 获取客服会话列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceSessionListRequest(private val account: String) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_SESSION_LIST
  }

  override fun toString(): String = "$path?access_token=$accessToken&kf_account=$account"
}

/**
 * 获取会话状态请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceSessionStatusRequest(private val openId: String) : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_SESSION_STATUS
  }

  override fun toString(): String = "$path?access_token=$accessToken&openid=$openId"
}

/**
 * 获取未接入会话列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class CustomerServiceSessionListOnHoldRequest : MpRequest() {
  init {
    this.path = URL_CUSTOMER_SERVICE_SESSION_WAITINGS
  }
}
