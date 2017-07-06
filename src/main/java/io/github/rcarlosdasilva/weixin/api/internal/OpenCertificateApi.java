package io.github.rcarlosdasilva.weixin.api.internal;

import io.github.rcarlosdasilva.weixin.common.dictionary.WebAuthorizeScope;
import io.github.rcarlosdasilva.weixin.model.response.certificate.WaAccessTokenResponse;

/**
 * 开放平台下，对公众号的认证相关API
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface OpenCertificateApi {

  /**
   * 开放平台代理公众号的网页授权获取code地址跳转.
   * <p>
   * 与普通公众平台的规则基本一致，多一个开放平台appid
   * <p>
   * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，
   * 默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开一个微信页面，
   * 该页面最终会跳转到开发者指定的页面。这里会将开发者指定的页面处理成可被微信授权的地址链接。
   * 
   * @param licensoraAppId
   *          授权方appid
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
   *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318590&token=&lang=zh_CN"
   *      >第一步：请求CODE</a>
   */
  String webAuthorize(String licensoraAppId, WebAuthorizeScope scope, String redirectTo,
      String param);

  /**
   * 开放平台代理公众号的网页授权获取code地址跳转.
   * <p>
   * 与普通公众平台的规则基本一致，多一个开放平台appid
   * <p>
   * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，
   * 默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开一个微信页面，
   * 该页面最终会跳转到开发者指定的页面。这里会将开发者指定的页面处理成可被微信授权的地址链接。
   * 
   * @param licensoraAppId
   *          授权方appid
   * @param scope
   *          {@link WebAuthorizeScope} 应用授权作用域，snsapi_base
   *          （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
   *          （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权， 也能获取其信息）
   * @param redirectTo
   *          授权后跳转到url
   * @return 授权链接
   * @see <a href=
   *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318590&token=&lang=zh_CN"
   *      >第一步：请求CODE</a>
   */
  String webAuthorize(String licensoraAppId, WebAuthorizeScope scope, String redirectTo);

  /**
   * 开放平台下，通过code换取网页授权access_token.
   * 
   * <p>
   * 这里通过code换取的是一个特殊的网页授权access_token,与基础支持中的access_token（
   * 该access_token用于调用其他接口）不同。如果网页授权的作用域为snsapi_base，
   * 则本步骤中获取到网页授权access_token的同时，也获取到了openid，snsapi_base式的网页授权流程即到此为止。
   * 
   * <p>
   * 首先保证通过网页授权链接（参考 {@code Weixin.with(key).url().webAuthorize()}
   * ），获取微信返回的code。code作为换取access_token的票据，每次用户授权带上的code将不一样，
   * code只能使用一次，5分钟未被使用自动过期。
   * 
   * @param appId
   *          授权方appid
   * @param code
   *          code票据
   * @return {@link WaAccessTokenResponse}
   * @see <a href=
   *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318590&token=&lang=zh_CN"
   *      >第二步：通过code换取access_token</a>
   */
  WaAccessTokenResponse askWebAuthorizeAccessToken(String appId, String code);

  /**
   * 开放平台下，刷新access_token.
   * 
   * <p>
   * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
   * refresh_token拥有较长的有效期（7天、30天、60天、90天），当refresh_token失效的后， 需要用户重新授权。
   * 
   * @param appId
   *          授权方appid
   * @param refreshToken
   *          刷新token
   * @return {@link WaAccessTokenResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN"
   *      >第三步：刷新access_token（如果需要）</a>
   */
  WaAccessTokenResponse refreshWebAuthorizeAccessToken(String appId, String refreshToken);

}
