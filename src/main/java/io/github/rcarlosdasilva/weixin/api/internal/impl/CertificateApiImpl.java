package io.github.rcarlosdasilva.weixin.api.internal.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.CertificateApi;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccessTokenCacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.JsTicketCacheHandler;
import io.github.rcarlosdasilva.weixin.core.json.Json;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.request.certificate.AccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.JsTicketRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.WaAccessTokenRefreshRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.WaAccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.WaAccessTokenVerifyRequest;
import io.github.rcarlosdasilva.weixin.model.response.certificate.AccessTokenResponse;
import io.github.rcarlosdasilva.weixin.model.response.certificate.JsTicketResponse;
import io.github.rcarlosdasilva.weixin.model.response.certificate.WaAccessTokenResponse;

/**
 * 认证相关API实现
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CertificateApiImpl extends BasicApi implements CertificateApi {

  private static final Logger logger = LoggerFactory.getLogger(CertificateApiImpl.class);

  private final Object lock = new Object();

  public CertificateApiImpl(String accountKey) {
    this.accountKey = accountKey;
  }

  @Override
  public String askAccessToken() {
    AccessTokenResponse token = AccessTokenCacheHandler.getInstance().get(this.accountKey);

    if (null == token || token.isExpired()) {
      synchronized (this.lock) {
        if (null == token || token.isExpired()) {
          if (null == token) {
            logger.debug("For:{} >> 无缓存过的access_token，请求access_token", this.accountKey);
          } else {
            logger.debug("For:{} >> 因access_token过期，重新请求。失效的access_token：[{}]", this.accountKey,
                token);
          }
          token = requestAccessToken();
        }
      }
    }

    return null == token ? null : token.getAccessToken();
  }

  @Override
  public void refreshAccessToken() {
    requestAccessToken();
  }

  @Override
  public void updateAccessToken(String token, long expiredAt) {
    if (Strings.isNullOrEmpty(token) || expiredAt < 0) {
      logger.warn("For:{} >> 使用错误的数据更新token： {}, {}", this.accountKey, token, expiredAt);
      return;
    }

    long expiresIn = 7200 * 1000;
    if (expiredAt > 0) {
      expiresIn = expiredAt - (System.currentTimeMillis() / 1000);
    }
    String responseMock = String.format("{'access_token':'%s','expires_in':%s}", token, expiresIn);
    AccessTokenResponse responseModel = Json.fromJson(responseMock, AccessTokenResponse.class);
    responseModel.updateExpireAt();
    AccessTokenCacheHandler.getInstance().put(this.accountKey, responseModel);
  }

  /**
   * 真正请求access_token代码.
   *
   * @return 请求结果
   */
  private synchronized AccessTokenResponse requestAccessToken() {
    logger.debug("For:{} >> 正在获取access_token", this.accountKey);
    Account account = AccountCacheHandler.getInstance().get(this.accountKey);
    AccessTokenRequest requestModel = new AccessTokenRequest();
    requestModel.setAppId(account.getAppId());
    requestModel.setAppSecret(account.getAppSecret());

    AccessTokenResponse responseModel = get(AccessTokenResponse.class, requestModel);

    if (responseModel != null) {
      responseModel.updateExpireAt();
      AccessTokenCacheHandler.getInstance().put(this.accountKey, responseModel);
      logger.debug("For:{} >> 获取到access_token：[{}]", this.accountKey,
          responseModel.getAccessToken());

      if (WeixinRegistry.getConfiguration() != null
          && WeixinRegistry.getConfiguration().getAccessTokenUpdatListener() != null) {
        WeixinRegistry.getConfiguration().getAccessTokenUpdatListener().updated(account.getKey(),
            account.getAppId(), responseModel.getAccessToken(), responseModel.getExpiresIn());
      }

      return responseModel;
    }

    return null;
  }

  @Override
  public final String askJsTicket() {
    JsTicketResponse ticket = JsTicketCacheHandler.getInstance().get(this.accountKey);

    if (ticket == null || ticket.expired()) {
      synchronized (this.lock) {
        if (null == ticket || ticket.expired()) {
          if (null == ticket) {
            logger.debug("For:{} >> 无缓存过的jsapi_ticket，请求jsapi_ticket", this.accountKey);
          } else {
            logger.debug("For:{} >> 因jsapi_ticket过期，重新请求。失效的jsapi_ticket：[{}]", this.accountKey,
                ticket);
          }
          ticket = requestJsTicket();
        }
      }
    }

    return null == ticket ? null : ticket.getJsTicket();
  }

  @Override
  public void refreshJsTicket() {
    requestJsTicket();
  }

  @Override
  public void updateJsTicket(String ticket, long expiredAt) {
    if (Strings.isNullOrEmpty(ticket) || expiredAt < 0) {
      logger.warn("For:{} >> 使用错误的数据更新ticket： {}, {}", this.accountKey, ticket, expiredAt);
      return;
    }

    long expiresIn = 7200 * 1000;
    if (expiredAt > 0) {
      expiresIn = expiredAt - (System.currentTimeMillis() / 1000);
    }
    String responseMock = String.format("{'access_token':'%s','expires_in':%s}", ticket, expiresIn);
    JsTicketResponse responseModel = Json.fromJson(responseMock, JsTicketResponse.class);
    responseModel.updateExpireAt();
    JsTicketCacheHandler.getInstance().put(this.accountKey, responseModel);
  }

  /**
   * 真正请求jsapi_ticket代码.
   *
   * @return 请求结果
   */
  private synchronized JsTicketResponse requestJsTicket() {
    logger.debug("For:{} >> 正在获取jsapi_ticket", this.accountKey);
    JsTicketRequest requestModel = new JsTicketRequest();

    JsTicketResponse responseModel = get(JsTicketResponse.class, requestModel);

    if (responseModel != null) {
      responseModel.updateExpireAt();
      JsTicketCacheHandler.getInstance().put(this.accountKey, responseModel);
      logger.debug("For:{} >> 获取jsapi_ticket：[{}]", this.accountKey, responseModel.getJsTicket());

      if (WeixinRegistry.getConfiguration() != null
          && WeixinRegistry.getConfiguration().getJsTicketUpdatedListener() != null) {
        Account account = AccountCacheHandler.getInstance().get(this.accountKey);
        WeixinRegistry.getConfiguration().getJsTicketUpdatedListener().updated(account.getKey(),
            account.getAppId(), responseModel.getJsTicket(), responseModel.getExpiresIn());
      }

      return responseModel;
    }

    return null;
  }

  @Override
  public WaAccessTokenResponse askWebAuthorizeAccessToken(String code) {
    Account account = AccountCacheHandler.getInstance().get(this.accountKey);
    WaAccessTokenRequest requestModel = new WaAccessTokenRequest();
    requestModel.setAppId(account.getAppId());
    requestModel.setAppSecret(account.getAppSecret());
    requestModel.setCode(code);

    return get(WaAccessTokenResponse.class, requestModel);
  }

  @Override
  public WaAccessTokenResponse refreshWebAuthorizeAccessToken(String refreshToken) {
    Account account = AccountCacheHandler.getInstance().get(this.accountKey);
    WaAccessTokenRefreshRequest requestModel = new WaAccessTokenRefreshRequest();
    requestModel.setAppId(account.getAppId());
    requestModel.setRefreshToken(refreshToken);

    return get(WaAccessTokenResponse.class, requestModel);
  }

  @Override
  public boolean verifyWebAuthorizeAccessToken(String accessToken, String openId) {
    WaAccessTokenVerifyRequest requestModel = new WaAccessTokenVerifyRequest();
    requestModel.setAccessToken(accessToken);
    requestModel.setOpenId(openId);

    return get(Boolean.class, requestModel);
  }

}
