package io.github.rcarlosdasilva.weixin.api.op.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.BasicApi;
import io.github.rcarlosdasilva.weixin.api.op.OpCertificateApi;
import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.common.dictionary.ResultCode;
import io.github.rcarlosdasilva.weixin.core.OpenPlatform;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.GeneralCacheableObject;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformAccessTokenException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformPreAuthCodeException;
import io.github.rcarlosdasilva.weixin.core.exception.ExecuteException;
import io.github.rcarlosdasilva.weixin.core.exception.OpenPlatformNotFoundException;
import io.github.rcarlosdasilva.weixin.core.exception.OpenPlatformTicketNotFoundException;
import io.github.rcarlosdasilva.weixin.core.listener.OpenPlatformAccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.OpenPlatformLisensorAccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.model.AccessToken;
import io.github.rcarlosdasilva.weixin.model.OpAccount;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthAccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthGetLicenseInformationRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthGetLicensorInformationRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthGetLicensorOptionRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthPreAuthCodeRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthRefreshLicensorAccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformAuthSetLicensorOptionRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.auth.OpenPlatformResetQuotaRequest;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthAccessTokenResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicensorOptionResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthPreAuthCodeResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensedAccessToken;

public class OpCertificateApiImpl extends BasicApi implements OpCertificateApi {

  private final Logger logger = LoggerFactory.getLogger(OpCertificateApiImpl.class);
  private final Object lock = new Object();

  public OpCertificateApiImpl() {
    super();
  }

  private OpAccount getOpenPlatformInfo() {
    OpAccount openPlatform = Registry.openPlatform();
    if (openPlatform == null) {
      logger.warn("未配置微信开放平台信息");
      throw new OpenPlatformNotFoundException();
    }
    return openPlatform;
  }

  @Override
  public String askAccessToken() {
    AccessToken token = CacheHandler.of(AccessToken.class)
        .get(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN);

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

  private synchronized AccessToken requestAccessToken() {
    logger.debug("For: >> 正在获取component_access_token");
    GeneralCacheableObject cacheableObject = CacheHandler.of(GeneralCacheableObject.class)
        .get(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET);
    String ticket = null;
    if (cacheableObject != null) {
      ticket = cacheableObject.getObj().toString();
    }
    if (Strings.isNullOrEmpty(ticket)) {
      logger.warn("获取不到微信开放平台的ticket，可能微信还未将ticket通知到当前服务器");
      throw new OpenPlatformTicketNotFoundException();
    }

    OpAccount openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthAccessTokenRequest requestModel = new OpenPlatformAuthAccessTokenRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setAppSecret(openPlatform.getAppSecret());
    requestModel.setTicket(ticket);

    AccessToken accessToken = post(OpenPlatformAuthAccessTokenResponse.class, requestModel);

    if (accessToken != null) {
      accessToken.setAccountMark(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN);
      CacheHandler.of(AccessToken.class)
          .put(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN, accessToken);
      logger.debug("For: >> 获取到access_token：[{}]", accessToken.getAccessToken());

      final OpenPlatformAccessTokenUpdatedListener listener = Registry
          .listener(OpenPlatformAccessTokenUpdatedListener.class);
      if (listener != null) {
        logger.debug("For: >> 调用监听器OpenPlatformAccessTokenUpdatedListener");
        listener.updated(accessToken.getAccessToken(), accessToken.getExpiresIn());
      }

      return accessToken;
    }

    return null;
  }

  @Override
  public String askPreAuthCode() {
    OpAccount openPlatform = getOpenPlatformInfo();
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
    return getLicensingInformation(license);
  }

  @Override
  public OpenPlatformAuthGetLicenseInformationResponse getLicensingInformation(String license) {
    OpAccount openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthGetLicenseInformationRequest requestModel = new OpenPlatformAuthGetLicenseInformationRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setAuthCode(license);

    OpenPlatformAuthGetLicenseInformationResponse responseModel = post(
        OpenPlatformAuthGetLicenseInformationResponse.class, requestModel);
    invokeListener(responseModel.getLicensingInformation().getAppId(),
        responseModel.getLicensedAccessToken());
    return responseModel;
  }

  @Override
  public OpenPlatformAuthGetLicenseInformationResponse refreshLicensorAccessToken(
      String licensoraAppId, String refreshToken) {
    OpAccount openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthRefreshLicensorAccessTokenRequest requestModel = new OpenPlatformAuthRefreshLicensorAccessTokenRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);
    requestModel.setLicensorRefreshToken(refreshToken);

    OpenPlatformAuthGetLicenseInformationResponse responseModel = post(
        OpenPlatformAuthGetLicenseInformationResponse.class, requestModel);
    invokeListener(licensoraAppId, responseModel.getLicensedAccessToken());
    return responseModel;
  }

  private void invokeListener(String licensoraAppId, LicensedAccessToken licensedAccessToken) {
    final OpenPlatformLisensorAccessTokenUpdatedListener listener = Registry
        .listener(OpenPlatformLisensorAccessTokenUpdatedListener.class);
    if (listener != null) {
      logger.debug("For: >> 调用监听器OpenPlatformLisensorAccessTokenUpdatedListener");
      listener.updated(licensoraAppId, licensedAccessToken.getAccessToken(),
          licensedAccessToken.getRefreshToken(), licensedAccessToken.getExpiresIn());
    }
  }

  @Override
  public OpenPlatformAuthGetLicenseInformationResponse getLicensorInformation(
      String licensoraAppId) {
    OpAccount openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthGetLicensorInformationRequest requestModel = new OpenPlatformAuthGetLicensorInformationRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);

    return post(OpenPlatformAuthGetLicenseInformationResponse.class, requestModel);
  }

  @Override
  public OpenPlatformAuthGetLicensorOptionResponse getLicensorOption(String licensoraAppId,
      String optionName) {
    OpAccount openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthGetLicensorOptionRequest requestModel = new OpenPlatformAuthGetLicensorOptionRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);
    requestModel.setOptionName(optionName);

    return post(OpenPlatformAuthGetLicensorOptionResponse.class, requestModel);
  }

  @Override
  public boolean setLicensorOption(String licensoraAppId, String optionName, String value) {
    OpAccount openPlatform = getOpenPlatformInfo();
    OpenPlatformAuthSetLicensorOptionRequest requestModel = new OpenPlatformAuthSetLicensorOptionRequest();
    requestModel.setAppId(openPlatform.getAppId());
    requestModel.setLicensorAppId(licensoraAppId);
    requestModel.setOptionName(optionName);
    requestModel.setValue(value);

    return post(Boolean.class, requestModel);
  }

  @Override
  public String openPlatformAuthorize(String redirectTo) {
    String preAuthCode = OpenPlatform.certificate().askPreAuthCode();
    OpAccount openPlatform = Registry.openPlatform();
    if (openPlatform == null) {
      logger.warn("未配置微信开放平台信息");
      throw new OpenPlatformNotFoundException();
    }

    return new StringBuilder(ApiAddress.URL_OPEN_PLATFORM_AUTHORIZE).append("?component_appid=")
        .append(openPlatform.getAppId()).append("&pre_auth_code=").append(preAuthCode)
        .append("&redirect_uri=").append(Utils.urlEncode(redirectTo)).toString();
  }

  @Override
  public boolean resetQuota() {
    OpenPlatformResetQuotaRequest requestModel = new OpenPlatformResetQuotaRequest();
    requestModel.setAppId(Registry.openPlatform().getAppId());

    try {
      return post(Boolean.class, requestModel);
    } catch (ExecuteException ex) {
      if (ex.getCode() != null && ex.getCode() == ResultCode.RESULT_48006) {
        return false;
      }
      throw ex;
    }
  }

}
