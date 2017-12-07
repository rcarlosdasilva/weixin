package io.github.rcarlosdasilva.weixin.api.weixin.impl;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.BasicApi;
import io.github.rcarlosdasilva.weixin.api.weixin.CertificateApi;
import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.common.dictionary.WebAuthorizeScope;
import io.github.rcarlosdasilva.weixin.core.OpenPlatform;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchAccessTokenException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformLicensorAccessTokenException;
import io.github.rcarlosdasilva.weixin.core.exception.InvalidAccountException;
import io.github.rcarlosdasilva.weixin.core.exception.LostWeixinLicensedRefreshTokenException;
import io.github.rcarlosdasilva.weixin.core.json.Json;
import io.github.rcarlosdasilva.weixin.core.listener.AccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.JsTicketUpdatedListener;
import io.github.rcarlosdasilva.weixin.model.AccessToken;
import io.github.rcarlosdasilva.weixin.model.JsTicket;
import io.github.rcarlosdasilva.weixin.model.JsapiSignature;
import io.github.rcarlosdasilva.weixin.model.WeixinAccount;
import io.github.rcarlosdasilva.weixin.model.request.certificate.AccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.JsTicketRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.WaAccessTokenRefreshRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.WaAccessTokenRequest;
import io.github.rcarlosdasilva.weixin.model.request.certificate.WaAccessTokenVerifyRequest;
import io.github.rcarlosdasilva.weixin.model.response.certificate.AccessTokenResponse;
import io.github.rcarlosdasilva.weixin.model.response.certificate.JsTicketResponse;
import io.github.rcarlosdasilva.weixin.model.response.certificate.WaAccessTokenResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;

/**
 * 认证相关API实现
 *
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class CertificateApiImpl extends BasicApi implements CertificateApi {

  private final Logger logger = LoggerFactory.getLogger(CertificateApiImpl.class);
  private final Object lock = new Object();

  public CertificateApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public String askAccessToken() {
    AccessToken token = CacheHandler.of(AccessToken.class).get(this.accountKey);

    if (null == token || token.isExpired()) {
      synchronized (this.lock) {
        if (null == token) {
          logger.debug("For:{} >> 无缓存过的access_token，请求access_token", this.accountKey);
        } else {
          logger.debug("For:{} >> 因access_token过期，重新请求。失效的access_token：[{}]", this.accountKey,
              token);
        }

        final WeixinAccount account = Registry.lookup(this.accountKey);
        if (account == null) { // 不应该为空
          throw new InvalidAccountException();
        }
        if (account.isWithOpenPlatform()) {
          // 使用微信开放平台获取access_token。在公众号授权后，会自动获取第一次授权方的access_token
          final String refreshToken = null == token ? account.getRefreshToken()
              : token.getRefreshToken();
          token = refreshLicensedAccessToken(account.getAppId(), refreshToken);
        } else {
          // 使用公众号appid和appsecret获取access_token
          token = requestAccessToken();
        }
      }
    }

    if (token == null) {
      logger.error("无法获取微信公众号的access_token");
      throw new CanNotFetchAccessTokenException();
    }

    return token.getAccessToken();
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

    long expiresIn = 7200L * 1000L;
    if (expiredAt > 0) {
      expiresIn = expiredAt - System.currentTimeMillis();
    }
    String responseMock = String.format("{'access_token':'%s','expires_in':%s}", token,
        (expiresIn / 1000));
    AccessTokenResponse responseModel = Json.fromJson(responseMock, AccessTokenResponse.class);
    CacheHandler.of(AccessToken.class).put(this.accountKey, responseModel);
  }

  /**
   * 更新使用开放平台的授权方的access_token.
   * 
   * @return 请求结果
   */
  private synchronized AccessToken refreshLicensedAccessToken(String licensorAppId,
      String refreshToken) {
    Preconditions.checkNotNull(licensorAppId);

    if (Strings.isNullOrEmpty(refreshToken)) {
      logger.error("找不到正确的授权方access_token刷新令牌，或许需要授权方重新授权");
      throw new LostWeixinLicensedRefreshTokenException();
    }

    OpenPlatformAuthGetLicenseInformationResponse response = OpenPlatform.certificate()
        .refreshLicensorAccessToken(licensorAppId, refreshToken);
    if (response == null
        || Strings.isNullOrEmpty(response.getLicensedAccessToken().getAccessToken())) {
      logger.error("获取不到授权方的access_token");
      throw new CanNotFetchOpenPlatformLicensorAccessTokenException();
    }

    AccessToken accessToken = response.getLicensedAccessToken();
    CacheHandler.of(AccessToken.class).put(this.accountKey, accessToken);
    logger.debug("For:{} >> 开放平台更新授权方access_token：[{}]", this.accountKey,
        accessToken.getAccessToken());

    return accessToken;
  }

  /**
   * 真正请求access_token代码.
   *
   * @return 请求结果
   */
  private synchronized AccessToken requestAccessToken() {
    logger.debug("For:{} >> 正在获取access_token", this.accountKey);
    WeixinAccount account = Registry.lookup(this.accountKey);
    AccessTokenRequest requestModel = new AccessTokenRequest();
    requestModel.setAppId(account.getAppId());
    requestModel.setAppSecret(account.getAppSecret());

    AccessToken accessToken = get(AccessTokenResponse.class, requestModel);

    if (accessToken != null) {
      CacheHandler.of(AccessToken.class).put(this.accountKey, accessToken);
      logger.debug("For:{} >> 获取到access_token：[{}]", this.accountKey, accessToken.getAccessToken());

      final AccessTokenUpdatedListener listener = Registry
          .listener(AccessTokenUpdatedListener.class);
      if (listener != null) {
        logger.debug("For:{} >> 调用监听器AccessTokenUpdatedListener", this.accountKey);
        listener.updated(account.getKey(), account.getAppId(), accessToken.getAccessToken(),
            accessToken.getExpiresIn());
      }

      return accessToken;
    }

    return null;
  }

  @Override
  public final String askJsTicket() {
    JsTicket ticket = CacheHandler.of(JsTicket.class).get(this.accountKey);

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

    long expiresIn = 7200L * 1000L;
    if (expiredAt > 0) {
      expiresIn = expiredAt - System.currentTimeMillis();
    }
    String responseMock = String.format("{'access_token':'%s','expires_in':%s}", ticket,
        (expiresIn / 1000));
    JsTicketResponse responseModel = Json.fromJson(responseMock, JsTicketResponse.class);
    responseModel.updateExpireAt();
    CacheHandler.of(JsTicket.class).put(this.accountKey, responseModel);
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
      CacheHandler.of(JsTicket.class).put(this.accountKey, responseModel);
      logger.debug("For:{} >> 获取jsapi_ticket：[{}]", this.accountKey, responseModel.getJsTicket());

      final JsTicketUpdatedListener listener = Registry.listener(JsTicketUpdatedListener.class);
      if (listener != null) {
        logger.debug("For:{} >> 调用监听器JsTicketUpdatedListener", this.accountKey);
        WeixinAccount account = Registry.lookup(this.accountKey);
        listener.updated(account.getKey(), account.getAppId(), responseModel.getJsTicket(),
            responseModel.getExpiresIn());
      }

      return responseModel;
    }

    return null;
  }

  @Override
  public WaAccessTokenResponse askWebAuthorizeAccessToken(String code) {
    WeixinAccount account = Registry.lookup(this.accountKey);
    WaAccessTokenRequest requestModel = new WaAccessTokenRequest();
    requestModel.setAppId(account.getAppId());
    requestModel.setAppSecret(account.getAppSecret());
    requestModel.setCode(code);

    return get(WaAccessTokenResponse.class, requestModel);
  }

  @Override
  public WaAccessTokenResponse refreshWebAuthorizeAccessToken(String refreshToken) {
    WeixinAccount account = Registry.lookup(this.accountKey);
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

  @Override
  public String webAuthorize(WebAuthorizeScope scope, String redirectTo, String param) {
    WeixinAccount account = Registry.lookup(this.accountKey);

    return new StringBuilder(ApiAddress.URL_WEB_AUTHORIZE).append("?appid=")
        .append(account.getAppId()).append("&redirect_uri=").append(Utils.urlEncode(redirectTo))
        .append("&response_type=code&scope=").append(scope)
        .append(Strings.isNullOrEmpty(param) ? "" : ("&state=" + param)).append("#wechat_redirect")
        .toString();
  }

  @Override
  public String webAuthorize(WebAuthorizeScope scope, String redirectTo) {
    return webAuthorize(scope, redirectTo, null);
  }

  @Override
  public JsapiSignature generateJsapiSignature(String url) {
    WeixinAccount account = Registry.lookup(this.accountKey);
    String ticket = Weixin.with(this.accountKey).certificate().askJsTicket();
    String timestamp = Long.toString(System.currentTimeMillis() / 1000);
    String nonce = UUID.randomUUID().toString();

    String raw = new StringBuilder("jsapi_ticket=").append(ticket).append("&noncestr=")
        .append(nonce).append("&timestamp=").append(timestamp).append("&url=").append(url)
        .toString();
    String signature = null;

    signature = DigestUtils.sha1Hex(raw);

    return new JsapiSignature(account.getAppId(), ticket, signature, url, timestamp, nonce);
  }

}
