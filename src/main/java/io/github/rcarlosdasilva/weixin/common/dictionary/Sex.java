package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 性别
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum Sex {

  /**
   * 未知.
   */
  UNKNOWN(0),
  /**
   * 男性.
   */
  MALE(1),
  /**
   * 女性.
   */
  FEMALE(2);

  private int code;

  Sex(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
