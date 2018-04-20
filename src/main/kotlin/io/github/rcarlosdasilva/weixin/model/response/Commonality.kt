package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName

class ServerIpsResponse {
  /**
   * 微信服务器IP地址列表.
   */
  val ipList: List<String>? = null
}

class ShortUrlResponse {
  /**
   * 短连接.
   */
  val shortUrl: String? = null
}

/**
 * 带参数的二维码创建结果.
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class QrCodeCreateResponse {
  /**
   * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码.
   */
  val ticket: String? = null
  /**
   * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）.
   */
  @SerializedName("expire_seconds")
  val expireSeconds: Long = 0
  /**
   * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片.
   */
  val url: String? = null
}