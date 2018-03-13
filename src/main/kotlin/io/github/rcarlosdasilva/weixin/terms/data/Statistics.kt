package io.github.rcarlosdasilva.weixin.terms.data

/**
 * 数据统计，用户分析用户的渠道
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class StatisticsUserSource(val code: Int) {

  /**
   * 0代表其他合计.
   */
  SUMMATION(0),
  /**
   * 1代表公众号搜索.
   */
  SEARCH(1),
  /**
   * 17代表名片分享.
   */
  SHARE(17),
  /**
   * 30代表扫描二维码.
   */
  QR_SCAN(30),
  /**
   * 43代表图文页右上角菜单.
   */
  NEWS_MENU(43),
  /**
   * 51代表支付后关注（在支付完成页）.
   */
  AFTER_PAY(51),
  /**
   * 57代表图文页内公众号名称.
   */
  NEWS_MP_NAME(57),
  /**
   * 75代表公众号文章广告.
   */
  ARTICLE_AD(75),
  /**
   * 78代表朋友圈广告.
   */
  MOMENTS_AD(78);

  companion object {
    fun with(code: Int): StatisticsUserSource? = values().find { it.code == code }
  }

}

/**
 * 数据统计，消息类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class StatisticsMessageType(val code: Int) {

  /**
   * 1代表文字.
   */
  TEXT(1),
  /**
   * 2代表图片.
   */
  IMAGE(2),
  /**
   * 3代表语音.
   */
  VOICE(3),
  /**
   * 4代表视频.
   */
  VIDEO(4),
  /**
   * 6代表第三方应用消息（链接消息）.
   */
  APP(6);

  companion object {
    fun with(code: Int): StatisticsMessageType? = values().find { it.code == code }
  }

}

/**
 * 数据统计，图文分析分享场景
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class StatisticsNewsShareScene(val code: Int) {

  /**
   * 1代表好友转发.
   */
  REPOST(1),
  /**
   * 2代表朋友圈.
   */
  MOMENTS(2),
  /**
   * 3代表腾讯微博.
   */
  TX_WEIBO(3),
  /**
   * 255代表其他.
   */
  OTHER(255);


  companion object {
    fun with(code: Int): StatisticsNewsShareScene? = values().find { it.code == code }
  }

}

/**
 * 数据统计，图文分析用户渠道
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class StatisticsNewsUserSource(val code: Int) {

  /**
   * 0代表会话.
   */
  SESSION(0),
  /**
   * 1代表好友.
   */
  FRIEND(1),
  /**
   * 2代表朋友圈.
   */
  MOMENTS(2),
  /**
   * 3代表腾讯微博.
   */
  TX_WEIBO(3),
  /**
   * 4代表历史消息页.
   */
  HISTORY(4),
  /**
   * 51代表其他.
   */
  OTHER(5);

  companion object {
    fun byCode(code: Int): StatisticsNewsUserSource? = values().find { it.code == code }
  }

}