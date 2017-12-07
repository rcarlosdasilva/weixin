package io.github.rcarlosdasilva.weixin.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationEventType;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationInfoType;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationMessageType;
import io.github.rcarlosdasilva.weixin.core.OpenPlatform;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.GeneralCacheableObject;
import io.github.rcarlosdasilva.weixin.core.encryption.Encryptor;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformLiceningInformationException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformLicenseException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformLicensorAccessTokenException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformTicketException;
import io.github.rcarlosdasilva.weixin.core.exception.WeirdWeixinNotificationException;
import io.github.rcarlosdasilva.weixin.core.parser.NotificationParser;
import io.github.rcarlosdasilva.weixin.model.AccessToken;
import io.github.rcarlosdasilva.weixin.model.OpAccount;
import io.github.rcarlosdasilva.weixin.model.WeixinAccount;
import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.builder.NotificationResponseBuilder;
import io.github.rcarlosdasilva.weixin.model.notification.Event;
import io.github.rcarlosdasilva.weixin.model.notification.Message;
import io.github.rcarlosdasilva.weixin.model.notification.Notification;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponse;
import io.github.rcarlosdasilva.weixin.model.notification.OpenInfo;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensingInformation;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensorInfromation;

/**
 * 微信推送通知代理类.
 * <p>
 * 微信的加密解密使用AES，默认会引起JRE的一个java.security.InvalidKeyException: Illegal key
 * size异常，请参考{@link Encryptor}解决
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class NotificationHandlerProxy {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static NotificationHandlerProxy instance = null;

  private NotificationHandler handler;

  private NotificationHandlerProxy(NotificationHandler handler) {
    Preconditions.checkNotNull(handler);
    this.handler = handler;
  }

  /**
   * 设置微信推送处理器，并生成代理.
   * 
   * @param handler
   *          {@link NotificationHandler}
   */
  public static void proxy(NotificationHandler handler) {
    instance = new NotificationHandlerProxy(handler);
  }

  /**
   * 获取单例代理.
   * 
   * @return {@link NotificationHandlerProxy}
   */
  public static NotificationHandlerProxy instance() {
    Preconditions.checkNotNull(instance);
    return instance;
  }

  /**
   * 处理微信推送来的内容，（处理明文模式）不推荐.
   * 
   * @param content
   *          推送内容，对应POST请求的数据
   * @return 回复微信的内容
   */
  public String process(String content) {
    return process(content, null, -1L, null);
  }

  /**
   * 处理微信推送来的内容，授权流程也使用这个方法（公众号平台下的安全模式、加密模式，不推荐）.
   * <p>
   * 建议在公众号平台下使用
   * 
   * @param content
   *          推送内容，对应POST请求的数据
   * @param signature
   *          签名串，公众号平台对应URL参数的signature，开放平台的消息签名，对应msg_signature参数
   * @param timestamp
   *          时间戳，对应URL参数的timestamp
   * @param nonce
   *          随机串，对应URL参数的nonce
   * @return 回复微信的内容
   */
  public String process(String content, String signature, long timestamp, String nonce) {
    return process(null, content, signature, timestamp, nonce);
  }

  /**
   * 处理微信推送来的内容，（可处理开放平台、及公众号平台下的安全模式、加密模式）推荐.
   * <p>
   * 建议在开放平台下使用
   * 
   * @param appId
   *          （开放平台下）授权方公众号appid
   * @param content
   *          推送内容，对应POST请求的数据
   * @param signature
   *          签名串，公众号平台对应URL参数的signature，开放平台的消息签名，对应msg_signature参数
   * @param timestamp
   *          时间戳，对应URL参数的timestamp
   * @param nonce
   *          随机串，对应URL参数的nonce
   * @return 回复微信的内容
   */
  public String process(String appId, String content, String signature, long timestamp,
      String nonce) {
    logger.debug("正在处理微信通知: at {} --- {} || 签名[{}] || 随机码[{}]", timestamp, content, signature,
        nonce);

    Notification notification = NotificationParser.parse(content);
    if (notification == null) {
      logger.warn("无法解析推送内容: {}", content);
      return null;
    }

    // 开放平台下授权过程中的公众号appid
    final String authorizingAppId = notification.getAppId();
    // 开放平台下，普通消息或事件消息的公众号appid，或公众号平台下公众号的mpid
    final String normalAppId = appId == null ? notification.getToUser() : appId;
    final String recipient = Strings.isNullOrEmpty(authorizingAppId) ? normalAppId
        : authorizingAppId;

    if (Strings.isNullOrEmpty(recipient)) {
      logger.warn("获取不到appid或tousername：{}", content);
      if (Registry.setting().isThrowException()) {
        throw new WeirdWeixinNotificationException();
      }
    }

    notification = decryptNotification(notification, recipient, signature, timestamp, nonce);
    if (notification == null) {
      return null;
    }

    NotificationMessageType messageType = notification.getMessageType();
    NotificationInfoType infoType = notification.getInfoType();

    NotificationResponseBuilder builder = Builder.buildNotificationResponse().with(notification);
    if (messageType != null) {
      if (messageType == NotificationMessageType.EVENT) {
        logger.debug("开始处理事件推送..");
        processEvent(builder, notification);
      } else {
        logger.debug("开始处理消息推送..");
        processMessage(builder, notification);
      }
    }
    if (infoType != null) {
      processInfo(infoType, builder, notification);
    }

    return encryptNotification(builder, notification.getAccount());
  }

  private Notification decryptNotification(final Notification originalNotification,
      String recipient, String signature, long timestamp, String nonce) {
    final OpAccount openPlatform = Registry.openPlatform();
    WeixinAccount account = Registry.lookup(recipient);
    if (openPlatform == null && account == null) {
      logger.warn("没有配置开放平台，并且找不到对应的公众号配置(Account): {}", recipient);
      return null;
    }

    Notification decryptedNotification = null;
    if (account == null) {
      logger.debug("未找到对应的公众号配置(Account): {}，这是一个新的公众号，正在使用开放平台配置解密..", recipient);
      decryptedNotification = Encryptor.decrypt(openPlatform.getAesToken(),
          openPlatform.getAesKey(), originalNotification.getCiphertext(), signature, timestamp,
          nonce);
    } else {
      if (account.isWithOpenPlatform()) {
        if (openPlatform == null) {
          logger.warn("没有配置开放平台，但公众号配置(Account): {} 为开放平台授权", recipient);
          return null;
        }

        logger.debug("正在使用开放平台配置对公众号(Account): {} 解密..", recipient);
        decryptedNotification = Encryptor.decrypt(openPlatform.getAesToken(),
            openPlatform.getAesKey(), originalNotification.getCiphertext(), signature, timestamp,
            nonce);
      } else {
        if (account.isSafeMode()) {
          logger.debug("正在使用公众号配置(Account): {} 解密..", recipient);
          decryptedNotification = Encryptor.decrypt(account.getAesToken(), account.getAesKey(),
              originalNotification.getCiphertext(), signature, timestamp, nonce);
        } else {
          logger.debug("检测到公众号配置(Account): {} 为明文模式，不需要解密", recipient);
          return originalNotification;
        }
      }
    }

    if (decryptedNotification == null) {
      logger.warn("无法解密，或解密失败: {}", recipient);
      return null;
    }
    logger.debug("解密成功");

    decryptedNotification.setAccount(account);

    return decryptedNotification;
  }

  private String encryptNotification(NotificationResponseBuilder builder, WeixinAccount account) {
    NotificationResponse response = builder.build();
    logger.debug("处理完毕，已生成返回数据");

    if (response == null) {
      return Convention.WEIXIN_NOTIFICATION_RESPONSE_NOTHING;
    } else {
      final OpAccount openPlatform = Registry.openPlatform();

      String reply = NotificationParser.toXml(response);
      if (account == null || account.isWithOpenPlatform()) {
        logger.debug("正在使用开放平台配置加密..");
        reply = Encryptor.encrypt(openPlatform.getAppId(), openPlatform.getAesToken(),
            openPlatform.getAesKey(), reply);
        if (Strings.isNullOrEmpty(reply)) {
          logger.error("无法加密：{}", account);
          return null;
        }
        logger.debug("加密成功");
      } else {
        logger.debug("正在使用公众号配置(Account): {} 加密..", account.getAppId());
        reply = Encryptor.encrypt(account.getAppId(), account.getAesToken(), account.getAesKey(),
            reply);
      }
      return reply;
    }
  }

  /**
   * 公众号推送的xml中使用的是toUserName，开放平台推送的xml中使用的是appid
   * 
   * @param notification
   *          notification
   * @return boolean
   */
  public boolean isOpenPlatformActive(Notification notification) {
    return Registry.openPlatform() != null && !Strings.isNullOrEmpty(notification.getAppId());
  }

  /**
   * 处理事件类通知.
   */
  private void processEvent(NotificationResponseBuilder builder, Notification notification) {
    Event event = notification.getEvent();
    NotificationEventType type = event.getType();

    switch (type) {
      case CLICK:
        handler.doEventOfMenuForClick(builder, notification, event.getKey());
        break;
      case VIEW:
        handler.doEventOfMenuForView(builder, notification, event.getKey(), event.getMenuId());
        break;
      case SCAN_QR_PUSH:
        handler.doEventOfMenuForScanQrPush(builder, notification, event.getKey(),
            event.getScanCodeInfo());
        break;
      case SCAN_QR_WAIT_MSG:
        handler.doEventOfMenuForScanQrWait(builder, notification, event.getKey(),
            event.getScanCodeInfo());
        break;
      case PIC_PHOTO:
        handler.doEventOfMenuForPicPhoto(builder, notification, event.getKey(),
            event.getPicsInfo());
        break;
      case PIC_PHOTO_OR_ALBUM:
        handler.doEventOfMenuForPicPhotoOrAlbum(builder, notification, event.getKey(),
            event.getPicsInfo());
        break;
      case PIC_WX_ALBUM:
        handler.doEventOfMenuForPicWxAlbum(builder, notification, event.getKey(),
            event.getPicsInfo());
        break;
      case LOCATION:
        handler.doEventOfMenuForLocation(builder, notification, event.getKey(),
            event.getLocationInfo());
        break;
      case MASS_SEND_FINISH:
        handler.doEventOfMessageForMass(builder, notification, event.getMessageId(),
            event.getStatus(), event.getTotalCount(), event.getFilterCount(), event.getSentCount(),
            event.getErrorCount());
        break;
      case TEMPLATE_SEND_FINISH:
        handler.doEventOfMessageForTemplate(builder, notification, event.getMessageId(),
            event.getStatus());
        break;
      case SUBSCRIBE:
        handler.doEventOfCommonForSubscribe(builder, notification, event.getKey(),
            event.getTicket());
        break;
      case UNSUBSCRIBE:
        handler.doEventOfCommonForUnsubscribe(builder, notification);
        break;
      case SCAN:
        handler.doEventOfCommonForScanQr(builder, notification, event.getKey(), event.getTicket());
        break;
      case REPORT_LOCATION:
        handler.doEventOfCommonForLocation(builder, notification, event.getLatitude(),
            event.getLongitude(), event.getPrecision());
        break;
      case VERIFY_QUALIFICATION_SUCCESS:
        handler.doEventOfVerifyForQualificationSuccess(builder, notification,
            event.getExpiredTime());
        break;
      case VERIFY_QUALIFICATION_FAIL:
        handler.doEventOfVerifyForQualificationFail(builder, notification, event.getFailTime(),
            event.getFailReason());
        break;
      case VERIFY_NAMING_SUCCESS:
        handler.doEventOfVerifyForNamingSuccess(builder, notification, event.getExpiredTime());
        break;
      case VERIFY_NAMING_FAIL:
        handler.doEventOfVerifyForNamingFail(builder, notification, event.getFailTime(),
            event.getFailReason());
        break;
      case VERIFY_ANNUAL:
        handler.doEventOfVerifyForAnnual(builder, notification, event.getExpiredTime());
        break;
      case VERIFY_EXPIRED:
        handler.doEventOfVerifyForExpired(builder, notification, event.getExpiredTime());
        break;
      default:
    }
  }

  /**
   * 处理消息类通知.
   */
  private void processMessage(NotificationResponseBuilder builder, Notification notification) {
    NotificationMessageType type = notification.getMessageType();
    Message message = notification.getMessage();

    switch (type) {
      case TEXT:
        handler.doMessageForText(builder, notification, message.getMessageId(),
            message.getContent());
        break;
      case IMAGE:
        handler.doMessageForImage(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getPicUrl());
        break;
      case VOICE:
        handler.doMessageForVoice(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getFormat(), message.getRecognition());
        break;
      case VIDEO:
        handler.doMessageForVideo(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getMediaThumbId());
        break;
      case SHORT_VIDEO:
        handler.doMessageForShortVideo(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getMediaThumbId());
        break;
      case LOCATION:
        handler.doMessageForLocation(builder, notification, message.getMessageId(),
            message.getLocationX(), message.getLocationY(), message.getScale(),
            message.getAddress());
        break;
      case LINK:
        handler.doMessageForLink(builder, notification, message.getMessageId(), message.getTitle(),
            message.getDescription(), message.getUrl());
        break;
      default:
    }
  }

  private void processInfo(NotificationInfoType infoType, NotificationResponseBuilder builder,
      Notification notification) {
    OpenInfo info = notification.getOpenInfo();
    switch (infoType) {
      case VERIFY_TICKET:
        processInfoWithVerifyTicket(info, builder, notification);
        break;
      case AUTHORIZE_SUCCEEDED:
        processInfoWhenSuccessed(info, builder, notification);
        break;
      case AUTHORIZE_CANCELED:
        processInfoWhenCanceled(info, builder, notification);
        break;
      case AUTHORIZE_UPDATED:
        processInfoWhenUpdated(info, builder, notification);
        break;
      default:
    }
  }

  private void processInfoWithVerifyTicket(OpenInfo info, NotificationResponseBuilder builder,
      Notification notification) {
    String ticket = notification.getOpenInfo().getTicket();
    if (Strings.isNullOrEmpty(ticket)) {
      logger.warn("无法获取到开放平台发放的Ticket");
      throw new CanNotFetchOpenPlatformTicketException();
    } else {
      CacheHandler.of(GeneralCacheableObject.class).put(
          Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET, new GeneralCacheableObject(ticket));
    }

    handler.doInfoOfComponentVerifyTicket(builder, notification, info.getTicket());
  }

  private void processInfoWhenSuccessed(OpenInfo info, NotificationResponseBuilder builder,
      Notification notification) {
    final boolean autoLoad = Registry.setting().isAutoLoadAuthorizedWeixinData();

    LicensingInformation licensingInformation = null;
    LicensorInfromation licensorInfromation = null;
    AccessToken accessToken = null;

    if (autoLoad) {
      OpenPlatformAuthGetLicenseInformationResponse licensingInformationResponse = fetchLicensingInformation(
          info.getLicense());
      OpenPlatformAuthGetLicenseInformationResponse licensorInformationResponse = fetchLicensorInformation(
          info.getLicensorAppId());

      accessToken = licensingInformationResponse.getLicensedAccessToken();
      licensingInformation = licensingInformationResponse.getLicensingInformation();
      licensorInfromation = licensorInformationResponse.getLicensorInfromation();
    }

    WeixinAccount account = handler.doInfoOfAuthorizeSucceeded(builder, notification,
        info.getLicensorAppId(), info.getLicense(), info.getLicenseExpireAt(), accessToken,
        licensingInformation, licensorInfromation);

    Registry.update(account);
    CacheHandler.of(AccessToken.class).put(account.getKey(), accessToken);
  }

  private void processInfoWhenCanceled(OpenInfo info, NotificationResponseBuilder builder,
      Notification notification) {
    String licensorAppId = notification.getOpenInfo().getLicensorAppId();
    if (Strings.isNullOrEmpty(licensorAppId)) {
      logger.warn("无法获取到开放平台授权者appid");
      throw new CanNotFetchOpenPlatformLicenseException();
    } else {
      Registry.remove(licensorAppId);
    }

    handler.doInfoOfAuthorizeCanceled(builder, notification, info.getLicensorAppId());
  }

  private void processInfoWhenUpdated(OpenInfo info, NotificationResponseBuilder builder,
      Notification notification) {
    final boolean autoLoad = Registry.setting().isAutoLoadAuthorizedWeixinData();

    LicensingInformation licensingInformation = null;
    LicensorInfromation licensorInfromation = null;
    AccessToken accessToken = null;

    if (autoLoad) {
      OpenPlatformAuthGetLicenseInformationResponse licensingInformationResponse = fetchLicensingInformation(
          info.getLicense());
      OpenPlatformAuthGetLicenseInformationResponse licensorInformationResponse = fetchLicensorInformation(
          info.getLicensorAppId());

      accessToken = licensingInformationResponse.getLicensedAccessToken();
      licensingInformation = licensingInformationResponse.getLicensingInformation();
      licensorInfromation = licensorInformationResponse.getLicensorInfromation();
    }

    WeixinAccount account = handler.doInfoOfAuthorizeUpdated(builder, notification,
        info.getLicensorAppId(), info.getLicense(), info.getLicenseExpireAt(), accessToken,
        licensingInformation, licensorInfromation);

    Registry.update(account);
    CacheHandler.of(AccessToken.class).put(account.getKey(), accessToken);
  }

  /**
   * 自动换取公众号或小程序的接口调用凭据和授权信息
   * 
   * @param license
   *          授权码
   */
  private OpenPlatformAuthGetLicenseInformationResponse fetchLicensingInformation(String license) {
    Preconditions.checkNotNull(license);

    OpenPlatformAuthGetLicenseInformationResponse response = OpenPlatform.certificate()
        .getLicensingInformation(license);

    if (response == null) {
      throw new CanNotFetchOpenPlatformLiceningInformationException();
    }
    if (response.getLicensedAccessToken() == null) {
      throw new CanNotFetchOpenPlatformLicensorAccessTokenException();
    }
    if (response.getLicensingInformation() == null) {
      throw new CanNotFetchOpenPlatformLicenseException();
    }

    return response;
  }

  /**
   * 自动获取授权方的帐号基本信息
   */
  private OpenPlatformAuthGetLicenseInformationResponse fetchLicensorInformation(
      String licensorAppId) {
    Preconditions.checkNotNull(licensorAppId);

    OpenPlatformAuthGetLicenseInformationResponse response = OpenPlatform.certificate()
        .getLicensorInformation(licensorAppId);
    if (response == null || response.getLicensorInfromation() == null) {
      logger.warn("无法获取到开放平台授权方的基本信息，但不影响主要功能");
      throw new CanNotFetchOpenPlatformLicenseException();
    }

    return response;
  }

}
