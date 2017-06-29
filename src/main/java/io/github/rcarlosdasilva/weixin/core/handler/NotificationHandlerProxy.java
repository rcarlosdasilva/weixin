package io.github.rcarlosdasilva.weixin.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationEventType;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationInfoType;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationMessageType;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccessTokenCacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.MixCacheHandler;
import io.github.rcarlosdasilva.weixin.core.encryption.Encryptor;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformLicenseException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformLicensorAccessTokenException;
import io.github.rcarlosdasilva.weixin.core.exception.CanNotFetchOpenPlatformTicketException;
import io.github.rcarlosdasilva.weixin.core.exception.WeirdWeixinNotificationException;
import io.github.rcarlosdasilva.weixin.core.parser.NotificationParser;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import io.github.rcarlosdasilva.weixin.model.AccessToken;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.OpenPlatform;
import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.builder.NotificationResponseBuilder;
import io.github.rcarlosdasilva.weixin.model.notification.Event;
import io.github.rcarlosdasilva.weixin.model.notification.Message;
import io.github.rcarlosdasilva.weixin.model.notification.Notification;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponse;
import io.github.rcarlosdasilva.weixin.model.notification.OpenInfo;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;

/**
 * 微信推送通知代理类.
 * <p>
 * 微信的加密解密使用AES，默认会引起JRE的一个java.security.InvalidKeyException: Illegal key
 * size异常，请参考{@link Encryptor}解决
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
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
   * 处理微信推送来的内容，（处理明文模式）.
   * 
   * @param content
   *          推送内容，对应POST请求的数据
   * @return 回复微信的内容
   */
  public String process(String content) {
    return process(content, null, -1L, null);
  }

  /**
   * 处理微信推送来的内容，（可处理安全模式）.
   * 
   * @param content
   *          推送内容，对应POST请求的数据
   * @param signature
   *          签名串，对应URL参数的signature，开放平台的消息签名，对应msg_signature参数
   * @param timestamp
   *          时间戳，对应URL参数的timestamp
   * @param nonce
   *          随机串，对应URL参数的nonce
   * @return 回复微信的内容
   */
  public String process(String content, String signature, long timestamp, String nonce) {
    logger.debug("正在处理微信通知: at {} --- {} || 签名[{}] || 随机码[{}]", timestamp, content, signature,
        nonce);

    Notification notification = NotificationParser.parse(content);
    if (notification == null) {
      logger.warn("无法解析推送内容: {}", content);
      return null;
    }

    // final boolean openPlatformActived = isOpenPlatformActive(notification);
    final String recipient = Strings.isNullOrEmpty(notification.getAppId())
        ? notification.getToUser() : notification.getAppId();

    if (Strings.isNullOrEmpty(recipient)) {
      logger.warn("获取不到appid或tousername：{}", content);
      if (Registration.getInstance().getSetting().isThrowException()) {
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

  private Notification decryptNotification(Notification notification, String recipient,
      String signature, long timestamp, String nonce) {
    final OpenPlatform openPlatform = Registration.getInstance().getOpenPlatform();
    Account account = Registration.lookup(recipient);
    if (account == null && openPlatform == null) {
      logger.warn("没有配置开放平台，并且找不到对应的公众号配置(Account): {}", recipient);
      return null;
    }

    if (account == null) {
      logger.debug("未找到对应的公众号配置(Account): {}，正在使用开放平台配置解密..", recipient);
      notification = Encryptor.decrypt(openPlatform.getAesToken(), openPlatform.getAesKey(),
          notification.getCiphertext(), signature, timestamp, nonce);
    } else {
      if (account.isWithOpenPlatform()) {
        if (openPlatform == null) {
          logger.warn("没有配置开放平台，但公众号配置(Account): {} 为开放平台授权", recipient);
          return null;
        }

        logger.debug("正在使用开放平台配置对公众号(Account): {} 解密..", recipient);
        notification = Encryptor.decrypt(openPlatform.getAesToken(), openPlatform.getAesKey(),
            notification.getCiphertext(), signature, timestamp, nonce);
      } else {
        if (account.isSafeMode()) {
          logger.debug("正在使用公众号配置(Account): {} 解密..", recipient);
          notification = Encryptor.decrypt(account.getAesToken(), account.getAesKey(),
              notification.getCiphertext(), signature, timestamp, nonce);
        } else {
          logger.debug("检测到公众号配置(Account): {} 为明文模式，不需要解密", recipient);
          return notification;
        }
      }
    }

    if (notification == null) {
      logger.warn("无法解密: {}", recipient);
      return null;
    }
    logger.debug("解密成功");

    notification.setAccount(account);

    return notification;
  }

  private String encryptNotification(NotificationResponseBuilder builder, Account account) {
    NotificationResponse response = builder.build();
    logger.debug("处理完毕，已生成返回数据");

    if (response == null) {
      return Convention.WEIXIN_NOTIFICATION_RESPONSE_NOTHING;
    } else {
      final OpenPlatform openPlatform = Registration.getInstance().getOpenPlatform();

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
    return Registration.getInstance().getOpenPlatform() != null
        && !Strings.isNullOrEmpty(notification.getAppId());
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
      case VERIFY_TICKET: {
        String ticket = notification.getOpenInfo().getTicket();
        if (Strings.isNullOrEmpty(ticket)) {
          logger.warn("无法获取到开放平台发放的Ticket");
          throw new CanNotFetchOpenPlatformTicketException();
        } else {
          MixCacheHandler.getInstance().put(Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET,
              ticket);
        }

        handler.doInfoOfComponentVerifyTicket(builder, notification, info.getTicket());
        break;
      }
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

  private void processInfoWhenSuccessed(OpenInfo info, NotificationResponseBuilder builder,
      Notification notification) {
    OpenPlatformAuthGetLicenseInformationResponse licensingInformationResponse = fetchLicensingInformation(
        info.getLicense());
    OpenPlatformAuthGetLicenseInformationResponse licensorInformationResponse = fetchLicensorInformation(
        info.getLicensorAppId());

    AccessToken accessToken = licensingInformationResponse.getLicensedAccessToken();
    Account account = handler.doInfoOfAuthorizeSucceeded(builder, notification,
        info.getLicensorAppId(), info.getLicense(), info.getLicenseExpireAt(), accessToken,
        licensingInformationResponse.getLicensingInformation(),
        licensorInformationResponse.getLicensorInfromation());

    Registration.getInstance().updateAccount(account);
    AccessTokenCacheHandler.getInstance().put(account.getKey(), accessToken);
  }

  private void processInfoWhenCanceled(OpenInfo info, NotificationResponseBuilder builder,
      Notification notification) {
    String licensorAppId = notification.getOpenInfo().getLicensorAppId();
    if (Strings.isNullOrEmpty(licensorAppId)) {
      logger.warn("无法获取到开放平台授权者appid");
      throw new CanNotFetchOpenPlatformLicenseException();
    } else {
      Registration.getInstance().unregister(licensorAppId);
    }

    handler.doInfoOfAuthorizeCanceled(builder, notification, info.getLicensorAppId());
  }

  private void processInfoWhenUpdated(OpenInfo info, NotificationResponseBuilder builder,
      Notification notification) {
    OpenPlatformAuthGetLicenseInformationResponse licensingInformationResponse = fetchLicensingInformation(
        info.getLicense());
    OpenPlatformAuthGetLicenseInformationResponse licensorInformationResponse = fetchLicensorInformation(
        info.getLicensorAppId());

    AccessToken accessToken = licensingInformationResponse.getLicensedAccessToken();
    Account account = handler.doInfoOfAuthorizeUpdated(builder, notification,
        info.getLicensorAppId(), info.getLicense(), info.getLicenseExpireAt(), accessToken,
        licensingInformationResponse.getLicensingInformation(),
        licensorInformationResponse.getLicensorInfromation());

    Registration.getInstance().updateAccount(account);
    AccessTokenCacheHandler.getInstance().put(account.getKey(), accessToken);
  }

  /**
   * 自动换取公众号或小程序的接口调用凭据和授权信息
   * 
   * @param notification
   *          notification
   */
  private OpenPlatformAuthGetLicenseInformationResponse fetchLicensingInformation(String license) {
    Preconditions.checkNotNull(license);

    OpenPlatformAuthGetLicenseInformationResponse response = Weixin.withOpenPlatform().openAuth()
        .getLicenseInformation(license);
    if (response == null || response.getLicensedAccessToken() == null) {
      throw new CanNotFetchOpenPlatformLicensorAccessTokenException();
    }
    if (response == null || response.getLicensingInformation() == null) {
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

    OpenPlatformAuthGetLicenseInformationResponse response = Weixin.withOpenPlatform().openAuth()
        .getLicensorInformation(licensorAppId);
    if (response == null || response.getLicensorInfromation() == null) {
      logger.warn("无法获取到开放平台授权方的基本信息，但不影响主要功能");
      throw new CanNotFetchOpenPlatformLicenseException();
    }

    return response;
  }

}
