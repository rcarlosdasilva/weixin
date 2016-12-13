package io.github.rcarlosdasilva.weixin.common.dictionary;

public enum StatisticsMessageType {

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

  private int code;

  StatisticsMessageType(int code) {
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
  public static StatisticsMessageType byCode(int code) {
    for (StatisticsMessageType source : values()) {
      if (source.code == code) {
        return source;
      }
    }
    throw new IllegalArgumentException("No matching result for [" + code + "]");
  }

}
