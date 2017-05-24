package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 公众号类型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum AccountType {

  /** 订阅号. */
  SUBSCRIPTION(0),
  /** 由历史老帐号升级后的订阅号 */
  SUBSCRIPTION_OLD(1),
  /** 服务号. */
  SERVICE(2),
  /** 企业号. */
  ENTERPRICE(3);

  private int code;

  AccountType(int code) {
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
  public static AccountType byCode(int code) {
    for (AccountType type : values()) {
      if (type.code == code) {
        return type;
      }
    }
    return null;
  }

}
