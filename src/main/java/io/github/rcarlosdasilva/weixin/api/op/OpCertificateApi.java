package io.github.rcarlosdasilva.weixin.api.op;

import io.github.rcarlosdasilva.weixin.core.handler.NotificationHandlerProxy;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicensorOptionResponse;

/**
 * 开放平台三方平台认证API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface OpCertificateApi {

  /**
   * 获取微信开放平台服务凭证(component_access_token).
   * <p>
   * 第三方平台component_access_token是第三方平台的下文中接口的调用凭据，也叫做令牌（component_access_token）。
   * 每个令牌是存在有效期（2小时）的，且令牌的调用不是无限制的，请第三方平台做好令牌的管理，在令牌快过期时（比如1小时50分）再进行刷新。
   * 
   * @return component_access_token
   */
  String askAccessToken();

  /**
   * 获取预授权码pre_auth_code.
   * <p>
   * 该API用于获取预授权码。预授权码用于公众号或小程序授权时的第三方平台方安全验证。
   * <p>
   * <b>直接调用{@link #openPlatformAuthorize(String)}接口，不需要单独调用获取预授权码</b>
   * 
   * @return pre_auth_code
   */
  String askPreAuthCode();

  /**
   * 使用授权码换取公众号或小程序的接口调用凭据和授权信息.
   * <p>
   * 该API用于使用授权码换取授权公众号或小程序的授权信息，并换取authorizer_access_token和authorizer_refresh_token。
   * 授权码的获取，需要在用户在第三方平台授权页中完成授权流程后，在回调URI中通过URL参数提供给第三方平台方。
   * <b>请注意，由于现在公众号或小程序可以自定义选择部分权限授权给第三方平台，
   * 因此第三方平台开发者需要通过该接口来获取公众号或小程序具体授权了哪些权限，而不是简单地认为自己声明的权限就是公众号或小程序授权的权限。</b>
   * <p>
   * <b>已使用{@link NotificationHandlerProxy}自动获取接口调用凭据和授权信息，如无特殊需要，无需调用</b>
   * 
   * @param license
   *          授权码
   * @return {@link OpenPlatformAuthGetLicenseInformationResponse}
   * @deprecated replaced by {@link #getLicensingInformation(String)}
   */
  @Deprecated
  OpenPlatformAuthGetLicenseInformationResponse getLicenseInformation(String license);

  /**
   * 使用授权码换取公众号或小程序的接口调用凭据和授权信息.
   * <p>
   * 该API用于使用授权码换取授权公众号或小程序的授权信息，并换取authorizer_access_token和authorizer_refresh_token。
   * 授权码的获取，需要在用户在第三方平台授权页中完成授权流程后，在回调URI中通过URL参数提供给第三方平台方。
   * <b>请注意，由于现在公众号或小程序可以自定义选择部分权限授权给第三方平台，
   * 因此第三方平台开发者需要通过该接口来获取公众号或小程序具体授权了哪些权限，而不是简单地认为自己声明的权限就是公众号或小程序授权的权限。</b>
   * <p>
   * <b>已使用{@link NotificationHandlerProxy}自动获取接口调用凭据和授权信息，如无特殊需要，无需调用</b>
   * 
   * @param license
   *          授权码
   * @return {@link OpenPlatformAuthGetLicenseInformationResponse}
   */
  OpenPlatformAuthGetLicenseInformationResponse getLicensingInformation(String license);

  /**
   * 获取（刷新）授权公众号或小程序的接口调用凭据（令牌）.
   * <p>
   * 该API用于在授权方令牌（authorizer_access_token）失效时，可用刷新令牌（authorizer_refresh_token）获取新的令牌。
   * <b>请注意，此处token是2小时刷新一次，开发者需要自行进行token的缓存，避免token的获取次数达到每日的限定额度。
   * 缓存方法可以参考：http://mp.weixin.qq.com/wiki/2/88b2bf1265a707c031e51f26ca5e6512.html</b>
   * <p>
   * <b>这里的授权access_token会自动更新，开发者无需关心</b>
   * 
   * @param licensoraAppId
   *          授权方appid
   * @param refreshToken
   *          授权方的刷新令牌，刷新令牌主要用于第三方平台获取和刷新已授权用户的access_token，
   *          只会在授权时刻提供，请妥善保存。一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
   * @return {@link OpenPlatformAuthGetLicenseInformationResponse}
   */
  OpenPlatformAuthGetLicenseInformationResponse refreshLicensorAccessToken(String licensoraAppId,
      String refreshToken);

  /**
   * 获取授权方的帐号基本信息.
   * <p>
   * 该API用于获取授权方的基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL。
   * 需要特别记录授权方的帐号类型，在消息及事件推送时，对于不具备客服接口的公众号，需要在5秒内立即响应；
   * 而若有客服接口，则可以选择暂时不响应，而选择后续通过客服接口来发送消息触达粉丝。
   * <p>
   * <b>已使用{@link NotificationHandlerProxy}自动获取接口调用凭据和授权信息，如无特殊需要，无需调用</b>
   * 
   * @param licensoraAppId
   *          授权方appid
   * @return {@link OpenPlatformAuthGetLicenseInformationResponse}
   */
  OpenPlatformAuthGetLicenseInformationResponse getLicensorInformation(String licensoraAppId);

  /**
   * 获取授权方的选项设置信息.
   * <p>
   * 该API用于获取授权方的公众号或小程序的选项设置信息，如：地理位置上报，语音识别开关，
   * 多客服开关。注意，获取各项选项设置信息，需要有授权方的授权，详见权限集说明。
   * 
   * @param licensoraAppId
   *          授权方appid
   * @param optionName
   *          选项名称
   * @return {@link OpenPlatformAuthGetLicensorOptionResponse}
   */
  OpenPlatformAuthGetLicensorOptionResponse getLicensorOption(String licensoraAppId,
      String optionName);

  /**
   * 设置授权方的选项信息.
   * <p>
   * 该API用于设置授权方的公众号或小程序的选项信息，如：地理位置上报，语音识别开关，
   * 多客服开关。注意，设置各项选项设置信息，需要有授权方的授权，详见权限集说明。
   * 
   * @param licensoraAppId
   *          授权方appid
   * @param optionName
   *          选项名称
   * @param value
   *          选项值
   * @return boolean
   */
  boolean setLicensorOption(String licensoraAppId, String optionName, String value);

  /**
   * 微信开放平台第三方平台授权页面地址.
   * <p>
   * 第三方平台方可以在自己的网站:中放置“微信公众号授权”或者“小程序授权”的入口，引导公众号和小程序管理员进入授权页。 授权页网址为 <a href=
   * "#">https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=xxxx&amp;pre_auth_code=xxxxx&amp;redirect_uri=xxxx</a>
   * ， 该网址中第三方平台方需要提供第三方平台方appid、预授权码和回调URI<br>
   * 授权流程完成后，授权页会自动跳转进入回调URI，并在URL参数中返回授权码和过期时间<b>(redirect_url?auth_code=xxx&amp;expires_in=600)</b>
   * <p>
   * <b> 这个方法会自动获取新的预授权码，不需要再去调用 {@link #askPreAuthCode()}接口</b> <br>
   * 授权码(auth_code)会在回调地址中当参数传递回来，同时，微信服务器会通知当前服务器“授权成功通知(InfoType =
   * authorized)”。注意：这里
   * {@link NotificationHandlerProxy}会在这个微信的通知中，自动使用授权码获取以下信息：,<br>
   * 1. “授权信息”和“授权方接口调用凭据以及凭据刷新码<br>
   * 2. 授权方的账号基本信息（暂时不提供小程序的方式，可手动获取） ，无需再利用回调地址中的auth_code获取<br>
   * 
   * @param redirectTo
   *          回调URI
   * @return 授权页面地址
   */
  String openPlatformAuthorize(String redirectTo);

  /**
   * 第三方平台对其所有API调用次数清零（只与第三方平台相关，与公众号无关，接口如api_component_token）.
   * 
   * @return 如果是超出清零的请求次数限制返回false
   */
  boolean resetQuota();

}
