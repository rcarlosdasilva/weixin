package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 公众号认证状态类型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum AccountVerifiedType {

  /** 未认证 */
  UNVERIFIED(-1),
  /** 微信认证 */
  verified(0),
  /** 新浪微博认证 */
  sina_weibo_verified(1),
  /** 腾讯微博认证 */
  tencent_webo_verified(2),
  /** 已资质认证通过但还未通过名称认证 */
  QUALIFICATION_VERIFIED(3),
  /** 已资质认证通过、还未通过名称认证，但通过了新浪微博认证 */
  QUALIFICATION_AND_SINA_WEIBO_VERIFIED(4),
  /** 已资质认证通过、还未通过名称认证，但通过了腾讯微博认证 */
  QUALIFICATION_AND_TENCENT_WEIBO_VERIFIED(5);

  private int code;

  AccountVerifiedType(int code) {
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
  public static AccountVerifiedType byCode(int code) {
    for (AccountVerifiedType type : values()) {
      if (type.code == code) {
        return type;
      }
    }
    return null;
  }

}
