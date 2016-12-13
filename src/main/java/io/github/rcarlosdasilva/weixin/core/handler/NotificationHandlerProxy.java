package io.github.rcarlosdasilva.weixin.core.handler;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationEvent;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationType;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.encryption.Encryptor;
import io.github.rcarlosdasilva.weixin.core.parser.NotificationParser;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.builder.NotificationResponseBuilder;
import io.github.rcarlosdasilva.weixin.model.notification.Event;
import io.github.rcarlosdasilva.weixin.model.notification.Message;
import io.github.rcarlosdasilva.weixin.model.notification.Notification;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponse;

/**
 * 微信推送通知代理类
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class NotificationHandlerProxy {

  private static final Logger logger = LoggerFactory.getLogger(NotificationHandlerProxy.class);

  private NotificationHandler handler;

  private NotificationHandlerProxy(NotificationHandler handler) {
    Preconditions.checkNotNull(handler);
    this.handler = handler;
  }

  /**
   * 设置微信推送处理器，并生成代理.
   */
  public static NotificationHandlerProxy proxy(NotificationHandler handler) {
    return new NotificationHandlerProxy(handler);
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
   *          签名串，对应URL参数的msg_signature
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

    Account account = WeixinRegistry.lookup(notification.getToUser());
    if (account == null) {
      logger.warn("找不到对应的公众号配置(Account): {}", notification.getToUser());
      return null;
    }

    boolean isSafeMode = account.isSafeMode();

    if (isSafeMode) {
      logger.debug("正在解密..");
      notification = Encryptor.decrypt(account.getToken(), account.getAesKey(),
          notification.getCiphertext(), signature, timestamp, nonce);
      if (notification == null) {
        logger.warn("无法解密: {}", account);
        return null;
      }
      logger.debug("解密成功");
    }

    notification.setAccount(account);
    NotificationType type = notification.getType();

    NotificationResponseBuilder builder = Builder.buildNotificationResponse().with(notification);
    switch (type) {
      case EVENT: {
        logger.debug("开始处理事件推送..");
        processEvent(builder, notification);
        break;
      }
      default: {
        logger.debug("开始处理消息推送..");
        processMessage(builder, notification);
      }
    }
    NotificationResponse response = builder.build();
    logger.debug("处理完毕，已生成返回数据");

    if (response == null) {
      return Convention.WEIXIN_NOTIFICATION_RESPONSE_NOTHING;
    } else {
      String reply = NotificationParser.toXml(response);
      if (isSafeMode) {
        logger.debug("正在加密..");
        reply = Encryptor.encrypt(account.getAppId(), account.getToken(), account.getAesKey(),
            reply);
        if (Strings.isNullOrEmpty(reply)) {
          logger.warn("无法加密：{}", account);
          return null;
        }
        logger.debug("加密成功");
      }
      return reply;
    }
  }

  /**
   * 处理事件类通知.
   */
  private void processEvent(NotificationResponseBuilder builder, Notification notification) {
    Event event = notification.getEvent();
    NotificationEvent type = event.getType();

    switch (type) {
      case CLICK: {
        handler.doEventOfMenuForClick(builder, notification, event.getKey());
        break;
      }
      case VIEW: {
        handler.doEventOfMenuForView(builder, notification, event.getKey(), event.getMenuId());
        break;
      }
      case SCAN_QR_PUSH: {
        handler.doEventOfMenuForScanQrPush(builder, notification, event.getKey(),
            event.getScanCodeInfo());
        break;
      }
      case SCAN_QR_WAIT_MSG: {
        handler.doEventOfMenuForScanQrWait(builder, notification, event.getKey(),
            event.getScanCodeInfo());
        break;
      }
      case PIC_PHOTO: {
        handler.doEventOfMenuForPicPhoto(builder, notification, event.getKey(),
            event.getPicsInfo());
        break;
      }
      case PIC_PHOTO_OR_ALBUM: {
        handler.doEventOfMenuForPicPhotoOrAlbum(builder, notification, event.getKey(),
            event.getPicsInfo());
        break;
      }
      case PIC_WX_ALBUM: {
        handler.doEventOfMenuForPicWxAlbum(builder, notification, event.getKey(),
            event.getPicsInfo());
        break;
      }
      case LOCATION: {
        handler.doEventOfMenuForLocation(builder, notification, event.getKey(),
            event.getLocationInfo());
        break;
      }
      case MASS_SEND_FINISH: {
        handler.doEventOfMessageForMass(builder, notification, event.getMessageId(),
            event.getStatus(), event.getTotalCount(), event.getFilterCount(), event.getSentCount(),
            event.getErrorCount());
        break;
      }
      case TEMPLATE_SEND_FINISH: {
        handler.doEventOfMessageForTemplate(builder, notification, event.getMessageId(),
            event.getStatus());
        break;
      }
      case SUBSCRIBE: {
        handler.doEventOfCommonForSubscribe(builder, notification, event.getKey(),
            event.getTicket());
        break;
      }
      case UNSUBSCRIBE: {
        handler.doEventOfCommonForUnsubscribe(builder, notification);
        break;
      }
      case SCAN: {
        handler.doEventOfCommonForScanQr(builder, notification, event.getKey(), event.getTicket());
        break;
      }
      case REPORT_LOCATION: {
        handler.doEventOfCommonForLocation(builder, notification, event.getLatitude(),
            event.getLongitude(), event.getPrecision());
        break;
      }
      case VERIFY_QUALIFICATION_SUCCESS: {
        handler.doEventOfVerifyForQualificationSuccess(builder, notification,
            event.getExpiredTime());
        break;
      }
      case VERIFY_QUALIFICATION_FAIL: {
        handler.doEventOfVerifyForQualificationFail(builder, notification, event.getFailTime(),
            event.getFailReason());
        break;
      }
      case VERIFY_NAMING_SUCCESS: {
        handler.doEventOfVerifyForNamingSuccess(builder, notification, event.getExpiredTime());
        break;
      }
      case VERIFY_NAMING_FAIL: {
        handler.doEventOfVerifyForNamingFail(builder, notification, event.getFailTime(),
            event.getFailReason());
        break;
      }
      case VERIFY_ANNUAL: {
        handler.doEventOfVerifyForAnnual(builder, notification, event.getExpiredTime());
        break;
      }
      case VERIFY_EXPIRED: {
        handler.doEventOfVerifyForExpired(builder, notification, event.getExpiredTime());
        break;
      }
      default:
    }
  }

  /**
   * 处理消息类通知.
   */
  private void processMessage(NotificationResponseBuilder builder, Notification notification) {
    NotificationType type = notification.getType();
    Message message = notification.getMessage();

    switch (type) {
      case TEXT: {
        handler.doMessageForText(builder, notification, message.getMessageId(),
            message.getContent());
        break;
      }
      case IMAGE: {
        handler.doMessageForImage(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getPicUrl());
        break;
      }
      case VOICE: {
        handler.doMessageForVoice(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getFormat(), message.getRecognition());
        break;
      }
      case VIDEO: {
        handler.doMessageForVideo(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getMediaThumbId());
        break;
      }
      case SHORT_VIDEO: {
        handler.doMessageForShortVideo(builder, notification, message.getMessageId(),
            message.getMediaId(), message.getMediaThumbId());
        break;
      }
      case LOCATION: {
        handler.doMessageForLocation(builder, notification, message.getMessageId(),
            message.getLocationX(), message.getLocationY(), message.getScale(),
            message.getAddress());
        break;
      }
      case LINK: {
        handler.doMessageForLink(builder, notification, message.getMessageId(), message.getTitle(),
            message.getDescription(), message.getUrl());
        break;
      }
      default:
    }
  }

}
