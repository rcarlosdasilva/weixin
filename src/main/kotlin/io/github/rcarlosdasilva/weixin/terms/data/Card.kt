package io.github.rcarlosdasilva.weixin.terms.data

/**
 * 卡券类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class CardType {

  /** 团购券.  */
  GROUPON,
  /** 代金券.  */
  CASH,
  /** 折扣券.  */
  DISCOUNT,
  /** 兑换券.  */
  GIFT,
  /** 优惠券.  */
  GENERAL_COUPON

}

/**
 * 卡券的Code码类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class CardCode {

  /** 文本.  */
  CODE_TYPE_TEXT,
  /** 一维码.  */
  CODE_TYPE_BARCODE,
  /** 二维码.  */
  CODE_TYPE_QR,
  /** 二维码无code显示.  */
  CODE_TYPE_ONLY_QR,
  /** 一维码无code显示.  */
  CODE_TYPE_ONLY_BARCODE,
  /** 不显示code和条形码类型.  */
  CODE_TYPE_NONE

}

/**
 * 卡券颜色
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class CardColor(private val code: String) {

  /** Color010 - 浅绿 #63b359.  */
  COLOR_010("Color010"),
  /** Color020 - 深绿 #2c9f67.  */
  COLOR_020("Color020"),
  /** Color030 - 浅蓝 #509fc9.  */
  COLOR_030("Color030"),
  /** Color040 - 深蓝 #5885cf.  */
  COLOR_040("Color040"),
  /** Color050 - 紫色 #9062c0.  */
  COLOR_050("Color050"),
  /** Color060 - 屎黄 #d09a45.  */
  COLOR_060("Color060"),
  /** Color070 - 土黄 #e4b138.  */
  COLOR_070("Color070"),
  /** Color080 - 淡橙 #ee903c.  */
  COLOR_080("Color080"),
  /** Color081 - 深橙 #f08500.  */
  COLOR_081("Color081"),
  /** Color082 - 翠绿 #a9d92d.  */
  COLOR_082("Color082"),
  /** Color090 - 砖红 #dd6549.  */
  COLOR_090("Color090"),
  /** Color100 - 猪血色A #cc463d.  */
  COLOR_100("Color100"),
  /** Color101 - 猪血色B #cf3e36.  */
  COLOR_101("Color101"),
  /** Color102 - 灰蓝 #5E6671.  */
  COLOR_102("Color102");

  override fun toString(): String = code

}

/**
 * 卡券日期类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class CardDate {

  /** 固定日期区间.  */
  DATE_TYPE_FIX_TIME_RANGE,
  /** 固定时长，自领取后按天算.  */
  DATE_TYPE_FIX_TERM

}

/**
 * 卡券商家服务类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class CardService {

  /** 外卖服务.  */
  BIZ_SERVICE_DELIVER,
  /** 停车位.  */
  BIZ_SERVICE_FREE_PARK,
  /** 可带宠物.  */
  BIZ_SERVICE_WITH_PET,
  /** 免费WIFI.  */
  BIZ_SERVICE_FREE_WIFI

}

/**
 * 限制类型枚举值
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class CardWeek {

  /** 周一.  */
  MONDAY,
  /** 周二.  */
  TUESDAY,
  /** 周三.  */
  WEDNESDAY,
  /** 周四.  */
  THURSDAY,
  /** 周五.  */
  FRIDAY,
  /** 周六.  */
  SATURDAY,
  /** 周日.  */
  SUNDAY

}