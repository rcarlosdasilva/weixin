package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 数据统计，图文分析用户渠道
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum StatisticsNewsUserSource {

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

  private int code;

  StatisticsNewsUserSource(int code) {
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
  public static StatisticsNewsUserSource byCode(int code) {
    for (StatisticsNewsUserSource source : values()) {
      if (source.code == code) {
        return source;
      }
    }
    throw new IllegalArgumentException("No matching result for [" + code + "]");
  }

}
