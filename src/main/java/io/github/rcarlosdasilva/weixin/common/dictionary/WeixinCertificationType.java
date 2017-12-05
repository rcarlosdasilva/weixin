package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 公众号认证类型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum WeixinCertificationType {

  /**
   * 未认证
   */
  UNC,
  /**
   * 微信认证
   */
  CWX,
  /**
   * 新浪微博认证
   */
  CSW,
  /**
   * 腾讯微博认证
   */
  CTW,
  /**
   * 已资质认证通过但还未通过名称认证
   */
  CFN,
  /**
   * 已资质认证通过、还未通过名称认证，但通过了新浪微博认证
   */
  CFS,
  /**
   * 代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
   */
  CFT

}
