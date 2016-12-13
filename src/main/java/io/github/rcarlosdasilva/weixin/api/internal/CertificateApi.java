package io.github.rcarlosdasilva.weixin.api.internal;

import io.github.rcarlosdasilva.weixin.model.response.certificate.WaAccessTokenResponse;

/**
 * 认证API
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface CertificateApi {

  /**
   * 获取微信服务凭证(access_token).
   * 
   * <p>
   * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。
   * 
   * @return 凭证
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN"
   *      >获取access_token</a>
   */
  String askAccessToken();

  /**
   * 刷新微信服务凭证(access_token).
   */
  void refreshAccessToken();

  /**
   * 获取JS SDK凭证(jsapi_ticket).
   * 
   * <p>
   * jsapi_ticket是公众号用于调用微信JS接口的临时票据。
   * 
   * @return 凭证
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN"
   *      >获取api_ticket</a>
   */
  String askJsTicket();

  /**
   * 通过code换取网页授权access_token.
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
   * @param code
   *          code票据
   * @return {@link WaAccessTokenResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN"
   *      >通过code换取网页授权access_token</a>
   */
  WaAccessTokenResponse askWebAuthorizeAccessToken(String code);

  /**
   * 刷新access_token.
   * 
   * <p>
   * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
   * refresh_token拥有较长的有效期（7天、30天、60天、90天），当refresh_token失效的后， 需要用户重新授权。
   * 
   * @param refreshToken
   *          刷新token
   * @return {@link WaAccessTokenResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN"
   *      >刷新access_token（如果需要）</a>
   */
  WaAccessTokenResponse refreshWebAuthorizeAccessToken(String refreshToken);

  /**
   * 检验授权凭证（access_token）是否有效.
   * 
   * @param accessToken
   *          网页授权access_token
   * @param openId
   *          OpenId
   * @return 是否有效
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN"
   *      >检验授权凭证（access_token）是否有效</a>
   */
  boolean verifyWebAuthorizeAccessToken(String accessToken, String openId);

}
