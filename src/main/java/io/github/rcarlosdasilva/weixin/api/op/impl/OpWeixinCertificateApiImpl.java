package io.github.rcarlosdasilva.weixin.api.op.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.BasicApi;
import io.github.rcarlosdasilva.weixin.api.op.OpWeixinCertificateApi;
import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.common.dictionary.WebAuthorizeScope;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.exception.OpenPlatformNotFoundException;
import io.github.rcarlosdasilva.weixin.model.OpAccount;
import io.github.rcarlosdasilva.weixin.model.request.open.certificate.OpenCertificateWaAccessTokenRefreshRequest;
import io.github.rcarlosdasilva.weixin.model.request.open.certificate.OpenCertificateWaAccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.response.certificate.WaAccessTokenResponse;

public class OpWeixinCertificateApiImpl extends BasicApi implements OpWeixinCertificateApi {

  private final Logger logger = LoggerFactory.getLogger(OpCertificateApiImpl.class);

  public OpWeixinCertificateApiImpl() {
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
  public String webAuthorize(String licensoraAppId, WebAuthorizeScope scope, String redirectTo,
      String param) {
    OpAccount openPlatform = getOpenPlatformInfo();

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

  @Override
  public WaAccessTokenResponse askWebAuthorizeAccessToken(String appId, String code) {
    OpAccount openPlatform = getOpenPlatformInfo();
    OpenCertificateWaAccessTokenRequest requestModel = new OpenCertificateWaAccessTokenRequest();
    requestModel.setAppId(appId);
    requestModel.setCode(code);
    requestModel.setOpenPlatformAppId(openPlatform.getAppId());

    return get(WaAccessTokenResponse.class, requestModel);
  }

  @Override
  public WaAccessTokenResponse refreshWebAuthorizeAccessToken(String appId, String refreshToken) {
    OpAccount openPlatform = getOpenPlatformInfo();
    OpenCertificateWaAccessTokenRefreshRequest requestModel = new OpenCertificateWaAccessTokenRefreshRequest();
    requestModel.setAppId(appId);
    requestModel.setRefreshToken(refreshToken);
    requestModel.setOpenPlatformAppId(openPlatform.getAppId());

    return get(WaAccessTokenResponse.class, requestModel);
  }

}
