package io.github.rcarlosdasilva.weixin.model.notification

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamConverter

/**
 * 位置信息
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class LocationInfo {

  /**
   * X坐标信息 (Location_X)
   *
   * @return 坐标
   */
  @XStreamAlias("Location_X")
  val locationX: Double = 0.toDouble()
  /**
   * Y坐标信息 (Location_Y)
   *
   * @return 坐标
   */
  @XStreamAlias("Location_Y")
  val locationY: Double = 0.toDouble()
  /**
   * 精度，可理解为精度或者比例尺、越精细的话 scale越高 (Scale)
   *
   * @return 精度
   */
  @XStreamAlias("Scale")
  val scale: Int = 0
  /**
   * 地理位置的字符串信息 (Label)
   *
   * @return 位置
   */
  @XStreamAlias("Label")
  val address: String? = null
  /**
   * 朋友圈POI的名字，可能为空 (Poiname)
   *
   * @return 名字
   */
  @XStreamAlias("Poiname")
  val poiName: String? = null

}


/**
 * 发送的图片信息
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class PicsInfo {

  /**
   * 发送的图片数量 (Count)
   *
   * @return count
   */
  @XStreamAlias("Count")
  val count: Int = 0
  /**
   * 图片的MD5值，开发者若需要，可用于验证接收到图片
   *
   * @return 图片md5列表
   */
  @XStreamAlias("PicList")
  @XStreamConverter(SendPicsConverter::class)
  val pics: List<String>? = null

}


/**
 * 微信被动回复消息附加内容
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ResponseAdditionalInfo {

  var content: String? = null
  var mediaId: String? = null
  var mediaThumbId: String? = null
  var title: String? = null
  var description: String? = null
  var url: String? = null
  var otherUrl: String? = null

  constructor()

  /**
   * 图文消息用
   *
   * @param title
   * title
   * @param description
   * description
   * @param url
   * url
   * @param otherUrl
   * otherUrl
   */
  constructor(title: String, description: String, url: String, otherUrl: String) {
    this.title = title
    this.description = description
    this.url = url
    this.otherUrl = otherUrl
  }

}


/**
 * 二维码扫描信息
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ScanCodeInfo {

  /**
   * 扫描类型，一般是qrcode (ScanType)
   *
   * @return type
   */
  @XStreamAlias("ScanType")
  val type: String? = null
  /**
   * 扫描结果，即二维码对应的字符串信息 (ScanResult)
   *
   * @return result
   */
  @XStreamAlias("ScanResult")
  val result: String? = null

}
