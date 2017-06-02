package io.github.rcarlosdasilva.weixin.api.internal.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.OpenAuthApi;
import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.common.dictionary.WebAuthorizeScope;
import io.github.rcarlosdasilva.weixin.core.cache.impl.MixCacheHandler;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformAccessTokenException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformPreAuthCodeException;
import io.github.rcarlosdasilva.weixin.core.exception.OpenPlatformNotFoundException;
import io.github.rcarlosdasilva.weixin.core.exception.OpenPlatformTicketNotFoundException;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import io.github.rcarlosdasilva.weixin.model.AccessToken.AccessTokenType;
import io.github.rcarlosdasilva.weixin.model.OpenPlatform;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthAccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthGetLicenseInformationRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthGetLicensorInformationRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthGetLicensorOptionRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthPreAuthCodeRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthRefreshLicensorAccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthSetLicensorOptionRequest;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthAccessTokenResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicensorOptionResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthPreAuthCodeResponse;

public class OpenAuthApiImpl extends BasicApi implements OpenAuthApi {

  private final Logger logger = LoggerFactory.getLogger(OpenAuthApiImpl.class);
  private final Object lock = new Object();

  public OpenAuthApiImpl(String accountKey) {
    super(accountKey);
  }

  private OpenPlatform getOpenPlatformInfo() {
    OpenPlatform openPlatform = Registration.getInstance().getOpenPlatform();
    if (openPlatform == null) {
      logger.warn("未配置微信开放平台信息");
      throw new OpenPlatformNotFoundException();
    }
    return openPlatform;
  }

  @Override
  public String askAccessToken() {
    Object tokenObject = MixCacheHandler.getInstance()
        .get(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN);
    OpenPlatformAuthAccessTokenResponse token = null;
    if (tokenObject != null) {
      token = (OpenPlatformAuthAccessTokenResponse) tokenObject;
    }

    if (null == token || token.isExpired()) {
      synchronized (this.lock) {
        if (null == token || token.isExpired()) {
          if (null == token) {
            logger.debug("For: >> 无缓存过的component_access_token，请求access_token");
          } else {
            logger.debug("For: >> 因component_access_token过期，重新请求。失效的access_token：[{}]",
                token.getAccessToken());
          }
          token = requestAccessToken();
        }
      }
    }

    if (token == null) {
      logger.error("无法获取微信开放平台的component_access_token");
      throw new CanNotFetchOpenPlatformAccessTokenException();
    }

    return token.getAccessToken();
  }

  private synchronized OpenPlatformAuthAccessTokenResponse requestAccessToken() {
    logger.debug("For: >> 正在获取component_access_token");
    Object ticketObject = MixCacheHandler.getInstance()
        .get(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET);
    String ticket = null;
    if (ticketObject != null) {
      ticket = ticketObject.toString();
    }
    if (Strings.isNullOrEmpty(ticket)) {
      logger.warn("获取不到微信开放平台的ticket，可能微信还未将ticket通知到当前服务器");
      throw new OpenPlatformTicketNotFoundException();
    }

    OpenPlatform openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthAccessTokenRequest requestModel = new OpenPlatformAuthAccessTokenRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setAppSecret(openPlatform.getAppSecret());
    requestModel.setTicket(ticket);

    OpenPlatformAuthAccessTokenResponse responseModel = post(
        OpenPlatformAuthAccessTokenResponse.class, requestModel);

    if (responseModel != null) {
      responseModel.setType(AccessTokenType.OPEN_PLATFORM);
      MixCacheHandler.getInstance().put(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN,
          responseModel);
      logger.debug("For: >> 获取到access_token：[{}]", responseModel.getAccessToken());

      return responseModel;
    }

    return null;
  }

  @Override
  public String askPreAuthCode() {
    OpenPlatform openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthPreAuthCodeRequest requestModel = new OpenPlatformAuthPreAuthCodeRequest();
    requestModel.setAppId(openPlatform.getAppId());

    OpenPlatformAuthPreAuthCodeResponse responseModel = post(
        OpenPlatformAuthPreAuthCodeResponse.class, requestModel);
    if (responseModel == null) {
      logger.error("无法获取微信开放平台预授权码");
      throw new CanNotFetchOpenPlatformPreAuthCodeException();
    }

    return responseModel.getPreAuthCode();
  }

  @Override
  public OpenPlatformAuthGetLicenseInformationResponse getLicenseInformation(String license) {
    OpenPlatform openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthGetLicenseInformationRequest requestModel = new OpenPlatformAuthGetLicenseInformationRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setAuthCode(license);

    return post(OpenPlatformAuthGetLicenseInformationResponse.class, requestModel);
  }

  @Override
  public OpenPlatformAuthGetLicenseInformationResponse refreshLicensorAccessToken(
      String licensoraAppId, String refreshToken) {
    OpenPlatform openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthRefreshLicensorAccessTokenRequest requestModel = new OpenPlatformAuthRefreshLicensorAccessTokenRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);
    requestModel.setLicensorRefreshToken(refreshToken);

    return post(OpenPlatformAuthGetLicenseInformationResponse.class, requestModel);
  }

  @Override
  public OpenPlatformAuthGetLicenseInformationResponse getLicensorInformation(
      String licensoraAppId) {
    OpenPlatform openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthGetLicensorInformationRequest requestModel = new OpenPlatformAuthGetLicensorInformationRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);

    return post(OpenPlatformAuthGetLicenseInformationResponse.class, requestModel);
  }

  @Override
  public OpenPlatformAuthGetLicensorOptionResponse getLicensorOption(String licensoraAppId,
      String optionName) {
    OpenPlatform openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthGetLicensorOptionRequest requestModel = new OpenPlatformAuthGetLicensorOptionRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);
    requestModel.setOptionName(optionName);

    return post(OpenPlatformAuthGetLicensorOptionResponse.class, requestModel);
  }

  @Override
  public boolean setLicensorOption(String licensoraAppId, String optionName, String value) {
    OpenPlatform openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthSetLicensorOptionRequest requestModel = new OpenPlatformAuthSetLicensorOptionRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);
    requestModel.setOptionName(optionName);
    requestModel.setValue(value);

    return post(Boolean.class, requestModel);
  }

  @Override
  public String openPlatformAuthorize(String redirectTo) {
    String preAuthCode = Weixin.withOpenPlatform().openAuth().askPreAuthCode();
    OpenPlatform openPlatform = Registration.getInstance().getOpenPlatform();
    if (openPlatform == null) {
      logger.warn("未配置微信开放平台信息");
      throw new OpenPlatformNotFoundException();
    }

    return new StringBuilder(ApiAddress.URL_OPEN_PLATFORM_AUTHORIZE).append("?component_appid=")
        .append(openPlatform.getAppId()).append("&pre_auth_code=").append(preAuthCode)
        .append("&redirect_uri=").append(redirectTo).toString();
  }

  @Override
  public String webAuthorize(String licensoraAppId, WebAuthorizeScope scope, String redirectTo,
      String param) {
    OpenPlatform openPlatform = getOpenPlatformInfo();

    return new StringBuilder(ApiAddress.URL_WEB_AUTHORIZE).append("?appid=").append(licensoraAppId)
        .append("&redirect_uri=").append(Utils.urlEncode(redirectTo))
        .append("&response_type=code&scope=").append(scope)
        .append(Strings.isNullOrEmpty(param) ? "" : ("&state=" + param)).append("&component_appid=")
        .append(openPlatform.getAppId()).append("#wechat_redirect").toString();
  }

  @Override
  public String webAuthorize(String licensoraAppId, WebAuthorizeScope scope, String redirectTo) {
    return webAuthorize(licensoraAppId, scope, redirectTo, null);
  }

}
