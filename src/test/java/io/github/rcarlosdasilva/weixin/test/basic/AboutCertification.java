package io.github.rcarlosdasilva.weixin.test.basic;

import io.github.rcarlosdasilva.weixin.core.Weixin;

/**
 * 关于认证相关
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class AboutCertification {

  /**
   * 获取凭证.
   */
  public static void main(String[] args) {
    RegisterAndUse.reg();
    System.out.println("Access Token: " + Weixin.with(RegisterAndUse.DEFAULT_KEY).certificate().askAccessToken());
    System.out.println("jsapi_ticket: " + Weixin.with(RegisterAndUse.DEFAULT_KEY).certificate().askJsTicket());

    /*
     * 下面代码是网页授权相关
     */
    System.out.println("Access Token in Web Page: "
        + Weixin.with(RegisterAndUse.DEFAULT_KEY).certificate().askWebAuthorizeAccessToken("code").getAccessToken());
    System.out.println("Refresh Token -- "
        + Weixin.with(RegisterAndUse.DEFAULT_KEY).certificate().refreshWebAuthorizeAccessToken("refreshToken"));
    System.out.println(""
        + Weixin.with(RegisterAndUse.DEFAULT_KEY).certificate().verifyWebAuthorizeAccessToken("accessToken", "openId"));
  }

}
