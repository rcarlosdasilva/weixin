package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 数据统计，图文分析分享场景
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum StatisticsNewsShareScene {

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

  private int code;

  StatisticsNewsShareScene(int code) {
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
  public static StatisticsNewsShareScene byCode(int code) {
    for (StatisticsNewsShareScene source : values()) {
      if (source.code == code) {
        return source;
      }
    }
    throw new IllegalArgumentException("No matching result for [" + code + "]");
  }

}
