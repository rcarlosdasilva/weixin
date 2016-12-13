package io.github.rcarlosdasilva.weixin.api.internal;

import io.github.rcarlosdasilva.weixin.common.dictionary.WebAuthorizeScope;
import io.github.rcarlosdasilva.weixin.model.JsapiSignature;

/**
 * 相关工具
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface HelperApi {

  /**
   * 网页授权获取code地址跳转.
   * 
   * <p>
   * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，
   * 默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开一个微信页面，
   * 该页面最终会跳转到开发者指定的页面。这里会将开发者指定的页面处理成可被微信授权的地址链接。
   * 
   * @param scope
   *          {@link WebAuthorizeScope} 应用授权作用域，snsapi_base
   *          （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
   *          （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权， 也能获取其信息）
   * @param redirectTo
   *          授权后跳转到url
   * @param param
   *          重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
   * @return 授权链接
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN"
   *      >微信网页授权</a>
   */
  String webAuthorize(WebAuthorizeScope scope, String redirectTo, String param);

  /**
   * 网页授权获取code地址跳转.
   * 
   * <p>
   * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，
   * 默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开一个微信页面，
   * 该页面最终会跳转到开发者指定的页面。这里会将开发者指定的页面处理成可被微信授权的地址链接。
   * 
   * @param scope
   *          {@link WebAuthorizeScope} 应用授权作用域，snsapi_base
   *          （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
   *          （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权， 也能获取其信息）
   * @param redirectTo
   *          授权后跳转到url
   * @return 授权链接
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN"
   *      >微信网页授权</a>
   */
  String webAuthorize(WebAuthorizeScope scope, String redirectTo);

  /**
   * JS-SDK使用权限签名算法.
   * 
   * @param url
   *          当前网页的URL，不包含#及其后面部分
   * @return 签名
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN"
   *      >JS-SDK使用权限签名算法</a>
   */
  JsapiSignature generateJsapiSignature(String url);

  /**
   * 判断ip是否是可信任的微信ip.
   * 
   * @param ip
   *          ip地址
   * @return 是否合法
   * @see CommonApi#getWeixinIps()
   */
  boolean isLegalRequestIp(String ip);

}
