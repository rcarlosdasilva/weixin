package io.github.rcarlosdasilva.weixin.core.handler;

import java.util.Date;

import io.github.rcarlosdasilva.weixin.model.AccessToken;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.builder.NotificationResponseBuilder;
import io.github.rcarlosdasilva.weixin.model.notification.Notification;
import io.github.rcarlosdasilva.weixin.model.notification.bean.LocationInfo;
import io.github.rcarlosdasilva.weixin.model.notification.bean.PicsInfo;
import io.github.rcarlosdasilva.weixin.model.notification.bean.ScanCodeInfo;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensingInformation;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensorInfromation;

/**
 * 如果大部分的通知都不需要处理，建议继承该类
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class DefaultNotificationHandler implements NotificationHandler {

  @Override
  public void doMessageForText(NotificationResponseBuilder builder, Notification notification,
      long messageId, String content) {
    builder.responseNothing();
  }

  @Override
  public void doMessageForImage(NotificationResponseBuilder builder, Notification notification,
      long messageId, String mediaId, String picUrl) {
    builder.responseNothing();
  }

  @Override
  public void doMessageForVoice(NotificationResponseBuilder builder, Notification notification,
      long messageId, String mediaId, String format, String recognition) {
    builder.responseNothing();
  }

  @Override
  public void doMessageForVideo(NotificationResponseBuilder builder, Notification notification,
      long messageId, String mediaId, String mediaThumbId) {
    builder.responseNothing();
  }

  @Override
  public void doMessageForShortVideo(NotificationResponseBuilder builder, Notification notification,
      long messageId, String mediaId, String mediaThumbId) {
    builder.responseNothing();
  }

  @Override
  public void doMessageForLocation(NotificationResponseBuilder builder, Notification notification,
      long messageId, double locationX, double locationY, int scale, String address) {
    builder.responseNothing();
  }

  @Override
  public void doMessageForLink(NotificationResponseBuilder builder, Notification notification,
      long messageId, String title, String description, String url) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForClick(NotificationResponseBuilder builder, Notification notification,
      String key) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForView(NotificationResponseBuilder builder, Notification notification,
      String key, String menuId) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForScanQrPush(NotificationResponseBuilder builder,
      Notification notification, String key, ScanCodeInfo scanCodeInfo) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForScanQrWait(NotificationResponseBuilder builder,
      Notification notification, String key, ScanCodeInfo scanCodeInfo) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForPicPhoto(NotificationResponseBuilder builder,
      Notification notification, String key, PicsInfo picsInfo) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForPicPhotoOrAlbum(NotificationResponseBuilder builder,
      Notification notification, String key, PicsInfo picsInfo) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForPicWxAlbum(NotificationResponseBuilder builder,
      Notification notification, String key, PicsInfo picsInfo) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMenuForLocation(NotificationResponseBuilder builder,
      Notification notification, String key, LocationInfo locationInfo) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMessageForMass(NotificationResponseBuilder builder,
      Notification notification, long messageId, String status, int totalCount, int filterCount,
      int sentCount, int errorCount) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfMessageForTemplate(NotificationResponseBuilder builder,
      Notification notification, long messageId, String status) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfCommonForSubscribe(NotificationResponseBuilder builder,
      Notification notification, String key, String ticket) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfCommonForUnsubscribe(NotificationResponseBuilder builder,
      Notification notification) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfCommonForScanQr(NotificationResponseBuilder builder,
      Notification notification, String key, String ticket) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfCommonForLocation(NotificationResponseBuilder builder,
      Notification notification, double latitude, double longitude, double precision) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfVerifyForQualificationSuccess(NotificationResponseBuilder builder,
      Notification notification, Date expiredTime) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfVerifyForQualificationFail(NotificationResponseBuilder builder,
      Notification notification, Date failTime, String failReason) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfVerifyForNamingSuccess(NotificationResponseBuilder builder,
      Notification notification, Date expiredTime) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfVerifyForNamingFail(NotificationResponseBuilder builder,
      Notification notification, Date failTime, String failReason) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfVerifyForAnnual(NotificationResponseBuilder builder,
      Notification notification, Date expiredTime) {
    builder.responseNothing();
  }

  @Override
  public void doEventOfVerifyForExpired(NotificationResponseBuilder builder,
      Notification notification, Date expiredTime) {
    builder.responseNothing();
  }

  @Override
  public void doInfoOfComponentVerifyTicket(NotificationResponseBuilder builder,
      Notification notification, String ticket) {
    builder.responseNothing();
  }

  @Override
  public Account doInfoOfAuthorizeSucceeded(NotificationResponseBuilder builder,
      Notification notification, String appId, String license, Date expireAt,
      AccessToken accessToken, LicensingInformation licensingInformation,
      LicensorInfromation licensorInfromation) {
    builder.responseNothing();
    return null;
  }

  @Override
  public void doInfoOfAuthorizeCanceled(NotificationResponseBuilder builder,
      Notification notification, String appId) {
    builder.responseNothing();
  }

  @Override
  public Account doInfoOfAuthorizeUpdated(NotificationResponseBuilder builder,
      Notification notification, String appId, String license, Date expireAt,
      AccessToken accessToken, LicensingInformation licensingInformation,
      LicensorInfromation licensorInfromation) {
    builder.responseNothing();
    return null;
  }

}
