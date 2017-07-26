package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 数据统计，用户分析用户的渠道
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum StatisticsUserSource {

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
  NEWS_MPNAME(57),
  /**
   * 75代表公众号文章广告.
   */
  ARTICLE_AD(75),
  /**
   * 78代表朋友圈广告.
   */
  MOMENTS_AD(78);

  private int code;

  StatisticsUserSource(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  /**
   * 根据code获取枚举对象.
   * 
   * @param code
   *          代码
   * @return 枚举
   */
  public static StatisticsUserSource byCode(int code) {
    for (StatisticsUserSource source : values()) {
      if (source.code == code) {
        return source;
      }
    }
    throw new IllegalArgumentException("No matching result for [" + code + "]");
  }

}
