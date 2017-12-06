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
    System.out.println("Access Token: " + Weixin.unique().certificate().askAccessToken());
    System.out.println("jsapi_ticket: " + Weixin.unique().certificate().askJsTicket());

    /*
     * 下面代码是网页授权相关
     */
    System.out.println("Access Token in Web Page: "
        + Weixin.unique().certificate().askWebAuthorizeAccessToken("code").getAccessToken());
    System.out.println("Refresh Token -- "
        + Weixin.unique().certificate().refreshWebAuthorizeAccessToken("refreshToken"));
    System.out.println(""
        + Weixin.unique().certificate().verifyWebAuthorizeAccessToken("accessToken", "openId"));
  }

}
