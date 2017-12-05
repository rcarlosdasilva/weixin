package io.github.rcarlosdasilva.weixin.core;

import io.github.rcarlosdasilva.weixin.api.op.OpCertificateApi;
import io.github.rcarlosdasilva.weixin.api.op.OpWeixinCertificateApi;
import io.github.rcarlosdasilva.weixin.api.op.impl.OpCertificateApiImpl;
import io.github.rcarlosdasilva.weixin.api.op.impl.OpWeixinCertificateApiImpl;

/**
 * 微信开放平台API使用统一入口
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class OpenPlatform {

  private static OpCertificateApi certificate = new OpCertificateApiImpl();
  private static OpWeixinCertificateApi openCertificate = new OpWeixinCertificateApiImpl();

  private OpenPlatform() {
    throw new IllegalStateException("OpenPlatform class");
  }

  /**
   * 微信开放平台授权流程API功能.
   * 
   * @return 授权流程入口
   */
  public static OpCertificateApi certificate() {
    return certificate;
  }

  /**
   * 微信开放平台授权公众号的认证相关API功能.
   * 
   * @return 授权公众号认证入口
   */
  public static OpWeixinCertificateApi weixinCertificate() {
    return openCertificate;
  }

}
