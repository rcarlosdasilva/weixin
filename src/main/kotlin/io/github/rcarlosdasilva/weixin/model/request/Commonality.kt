package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.*

/**
 * 微信服务器IP列表请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ServerIpsRequest : MpRequest() {
  init {
    this.path = URL_CERTIFICATE_SERVER_IP
  }
}

/**
 * 短连接请求模型
 *
 * @param url 要转换的连接
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ShortUrlRequest(
  @SerializedName("long_url") private var url: String? = null
) : MpRequest() {
  @SerializedName("action")
  private val action = SHROT_URL_ACTION

  init {
    this.path = URL_COMMON_SHORT_URL
  }
}

/**
 * 创建二维码请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class QrCodeCreateRequest(
  @SerializedName("expire_seconds") private val expireSeconds: Long? = null,
  @SerializedName("action_name") private val action: String
) : MpRequest() {
  @SerializedName("action_info")
  val info = mutableMapOf<String, Any>()

  init {
    this.path = URL_COMMON_QR_CREATE
  }
}

/**
 * 换取二维码图片请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class QrCodeDownloadRequest(private var ticket: String) : MpRequest() {
  init {
    this.path = URL_COMMON_QR_SHOW
  }

  override fun toString(): String = "$path?ticket=$ticket"
}

class ResetQuotaRequest(private var appid: String) : MpRequest() {
  init {
    this.path = URL_HELPER_RESET_QUOTA
  }
}
