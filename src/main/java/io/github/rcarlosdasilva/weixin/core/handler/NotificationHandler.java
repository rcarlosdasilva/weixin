package io.github.rcarlosdasilva.weixin.core.handler;

import java.util.Date;

import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import io.github.rcarlosdasilva.weixin.model.builder.NotificationResponseBuilder;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationMeta;
import io.github.rcarlosdasilva.weixin.model.notification.bean.LocationInfo;
import io.github.rcarlosdasilva.weixin.model.notification.bean.PicsInfo;
import io.github.rcarlosdasilva.weixin.model.notification.bean.ScanCodeInfo;

/**
 * 统一处理器接口，自动根据微信推送通知内容，执行响应代码.
 * 
 * <p>
 * 实现该接口，使用 {@link NotificationHandlerProxy#proxy(NotificationHandler)} 生成一个代理
 * proxy，需要在系统中保持改代理 proxy，当微信推送内容到服务器时，使用 {@code proxy#process()} 处理接收到的数据，并按照
 * {@link NotificationHandler} 事先写好的代码执行
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface NotificationHandler {

  /**
   * 接收普通消息，文本消息.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用 builder
   *          指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id，64位整型
   * @param content
   *          文本消息内容
   */
  void doMessageForText(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, String content);

  /**
   * 接收普通消息，图片消息.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id，64位整型
   * @param mediaId
   *          图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param picUrl
   *          图片链接（由系统生成）
   */
  void doMessageForImage(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, String mediaId, String picUrl);

  /**
   * 接收普通消息，语音消息.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id，64位整型
   * @param mediaId
   *          语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param format
   *          语音格式，如amr，speex等
   * @param recognition
   *          语音识别结果，UTF8编码。（开通语音识别后有值，具体查看微信文档）
   */
  void doMessageForVoice(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, String mediaId, String format, String recognition);

  /**
   * 接收普通消息，视频消息.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id，64位整型
   * @param mediaId
   *          视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param mediaThumbId
   *          视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
   */
  void doMessageForVideo(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, String mediaId, String mediaThumbId);

  /**
   * 接收普通消息，小视频消息.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id，64位整型
   * @param mediaId
   *          视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
   * @param mediaThumbId
   *          视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
   */
  void doMessageForShortVideo(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, String mediaId, String mediaThumbId);

  /**
   * 接收普通消息，地理位置消息.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id，64位整型
   * @param locationX
   *          地理位置维度
   * @param locationY
   *          地理位置经度
   * @param scale
   *          地图缩放大小
   * @param address
   *          地理位置信息
   */
  void doMessageForLocation(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, double locationX, double locationY, int scale, String address);

  /**
   * 接收普通消息，链接消息.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id，64位整型
   * @param title
   *          消息标题
   * @param description
   *          消息描述
   * @param url
   *          消息链接
   */
  void doMessageForLink(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, String title, String description, String url);

  /**
   * 自定义菜单事件推送，点击菜单拉取消息时的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，与自定义菜单接口中KEY值对应
   */
  void doEventOfMenuForClick(NotificationResponseBuilder builder, NotificationMeta notification,
      String key);

  /**
   * 自定义菜单事件推送，点击菜单跳转链接时的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，设置的跳转URL
   * @param menuId
   *          指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。
   */
  void doEventOfMenuForView(NotificationResponseBuilder builder, NotificationMeta notification,
      String key, String menuId);

  /**
   * 自定义菜单事件推送，扫码推事件的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，由开发者在创建菜单时设定
   * @param scanCodeInfo
   *          扫描信息, see {@link ScanCodeInfo}
   */
  void doEventOfMenuForScanQrPush(NotificationResponseBuilder builder,
      NotificationMeta notification, String key, ScanCodeInfo scanCodeInfo);

  /**
   * 自定义菜单事件推送，扫码推事件且弹出“消息接收中”提示框的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，由开发者在创建菜单时设定
   * @param scanCodeInfo
   *          扫描信息, see {@link ScanCodeInfo}
   */
  void doEventOfMenuForScanQrWait(NotificationResponseBuilder builder,
      NotificationMeta notification, String key, ScanCodeInfo scanCodeInfo);

  /**
   * 自定义菜单事件推送，弹出系统拍照发图的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，由开发者在创建菜单时设定
   * @param picsInfo
   *          发送的图片信息, see {@link PicsInfo}
   */
  void doEventOfMenuForPicPhoto(NotificationResponseBuilder builder, NotificationMeta notification,
      String key, PicsInfo picsInfo);

  /**
   * 自定义菜单事件推送，弹出拍照或者相册发图的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，由开发者在创建菜单时设定
   * @param picsInfo
   *          发送的图片信息, see {@link PicsInfo}
   */
  void doEventOfMenuForPicPhotoOrAlbum(NotificationResponseBuilder builder,
      NotificationMeta notification, String key, PicsInfo picsInfo);

  /**
   * 自定义菜单事件推送，弹出微信相册发图器的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，由开发者在创建菜单时设定
   * @param picsInfo
   *          发送的图片信息, see {@link PicsInfo}
   */
  void doEventOfMenuForPicWxAlbum(NotificationResponseBuilder builder,
      NotificationMeta notification, String key, PicsInfo picsInfo);

  /**
   * 自定义菜单事件推送，弹出地理位置选择器的事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，由开发者在创建菜单时设定
   * @param locationInfo
   *          发送的位置信息, see {@link LocationInfo}
   */
  void doEventOfMenuForLocation(NotificationResponseBuilder builder, NotificationMeta notification,
      String key, LocationInfo locationInfo);

  /**
   * 高级群发接口，事件推送群发结果.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          群发的消息ID
   * @param status
   *          群发的结构，为“send success”或“send fail”或“err(num)”。但send
   *          success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，可能的情况如下：
   *          err(10001), //涉嫌广告 err(20001), //涉嫌政治 err(20004), //涉嫌社会
   *          err(20002), //涉嫌色情 err(20006), //涉嫌违法犯罪 err(20008), //涉嫌欺诈
   *          err(20013), //涉嫌版权 err(22000), //涉嫌互推(互相宣传) err(21000), //涉嫌其他
   * @param totalCount
   *          tag_id下粉丝数；或者openid_list中的粉丝数
   * @param filterCount
   *          过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount
   *          = SentCount + ErrorCount
   * @param sentCount
   *          发送成功的粉丝数
   * @param errorCount
   *          发送失败的粉丝数
   */
  void doEventOfMessageForMass(NotificationResponseBuilder builder, NotificationMeta notification,
      long messageId, String status, int totalCount, int filterCount, int sentCount,
      int errorCount);

  /**
   * 模板消息接口，事件推送.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param messageId
   *          消息id
   * @param status
   *          成功为success，由于用户拒收（用户设置拒绝接收公众号消息）而失败时为failed:user
   *          block，由于其他原因失败时为failed: system failed
   */
  void doEventOfMessageForTemplate(NotificationResponseBuilder builder,
      NotificationMeta notification, long messageId, String status);

  /**
   * 关注事件.
   * 
   * <p>
   * 有两种情况，1. 用户关注 2. 用户扫码关注（查看 扫描带参数二维码事件，用户未关注时，进行关注后的事件推送）<br>
   * <b>key和ticket</b>的值在第一种情况下为空
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，qrscene_为前缀，后面为二维码的参数值
   * @param ticket
   *          二维码的ticket，可用来换取二维码图片
   */
  void doEventOfCommonForSubscribe(NotificationResponseBuilder builder,
      NotificationMeta notification, String key, String ticket);

  /**
   * 取消关注事件.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   */
  void doEventOfCommonForUnsubscribe(NotificationResponseBuilder builder,
      NotificationMeta notification);

  /**
   * 扫描带参数二维码事件，用户已关注时的事件推送.
   * 
   * <p>
   * 有两种情况，1. 用户关注 2. 用户扫码关注（查看 扫描带参数二维码事件）<br>
   * <b>key和ticket</b>的值在第一种情况下为空
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param key
   *          事件KEY值，qrscene_为前缀，后面为二维码的参数值
   * @param ticket
   *          二维码的ticket，可用来换取二维码图片
   */
  void doEventOfCommonForScanQr(NotificationResponseBuilder builder, NotificationMeta notification,
      String key, String ticket);

  /**
   * 上报地理位置事件.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param latitude
   *          地理位置纬度
   * @param longitude
   *          地理位置经度
   * @param precision
   *          地理位置精度
   */
  void doEventOfCommonForLocation(NotificationResponseBuilder builder,
      NotificationMeta notification, double latitude, double longitude, double precision);

  /**
   * 微信认证事件推送，资质认证成功（此时立即获得接口权限）.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param expiredTime
   *          有效期 (整形)，指的是时间戳，将于该时间戳认证过期
   */
  void doEventOfVerifyForQualificationSuccess(NotificationResponseBuilder builder,
      NotificationMeta notification, Date expiredTime);

  /**
   * 微信认证事件推送，资质认证失败.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param failTime
   *          失败发生时间 (整形)，时间戳
   * @param failReason
   *          认证失败的原因
   */
  void doEventOfVerifyForQualificationFail(NotificationResponseBuilder builder,
      NotificationMeta notification, Date failTime, String failReason);

  /**
   * 微信认证事件推送，名称认证成功（即命名成功）.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param expiredTime
   *          有效期 (整形)，指的是时间戳，将于该时间戳认证过期
   */
  void doEventOfVerifyForNamingSuccess(NotificationResponseBuilder builder,
      NotificationMeta notification, Date expiredTime);

  /**
   * 微信认证事件推送，名称认证失败（这时虽然客户端不打勾，但仍有接口权限）.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param failTime
   *          失败发生时间 (整形)，时间戳
   * @param failReason
   *          认证失败的原因
   */
  void doEventOfVerifyForNamingFail(NotificationResponseBuilder builder,
      NotificationMeta notification, Date failTime, String failReason);

  /**
   * 微信认证事件推送，年审通知.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param expiredTime
   *          有效期 (整形)，指的是时间戳，将于该时间戳认证过期，需尽快年审
   */
  void doEventOfVerifyForAnnual(NotificationResponseBuilder builder, NotificationMeta notification,
      Date expiredTime);

  /**
   * 微信认证事件推送，认证过期失效通知.
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param expiredTime
   *          有效期 (整形)，指的是时间戳，表示已于该时间戳认证过期，需要重新发起微信认证
   */
  void doEventOfVerifyForExpired(NotificationResponseBuilder builder, NotificationMeta notification,
      Date expiredTime);

  /**
   * 开放平台时间推送，推送component_verify_ticket协议.
   * <p>
   * 在第三方平台创建审核通过后，微信服务器会向其“授权事件接收URL”每隔10分钟定时推送component_verify_ticket
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param ticket
   *          ticket
   */
  void doInfoOfComponentVerifyTicket(NotificationResponseBuilder builder,
      NotificationMeta notification, String ticket);

  /**
   * 推送授权相关通知：授权成功通知.
   * <p>
   * 当公众号对第三方平台进行授权、取消授权、更新授权后，微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
   * <p>
   * <b>1. 开发者应该在这里将授权的公众号信息保存起来<br>
   * 2. 公众号已经自动注册到缓存，默认用授权方的appid做key（如果之前开发者已经先行注册过公众号，
   * 则使用注册时的key），之后的操作就可以使用Weixin.with(key)<br>
   * 3. 在这方法中可以使用{@link Registration#lookup(String)}得到授权方信息，
   * 其中包含授权内容以及授权方基本信息</b>
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param appId
   *          公众号或小程序
   * @param license
   *          授权码，可用于换取公众号的接口调用凭据，详细见上面的说明
   * @param expireAt
   *          授权码过期时间
   */
  void doInfoOfAuthorizeSucceeded(NotificationResponseBuilder builder,
      NotificationMeta notification, String appId, String license, Date expireAt);

  /**
   * 推送授权相关通知：取消授权通知.
   * <p>
   * 当公众号对第三方平台进行授权、取消授权、更新授权后，微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param appId
   *          公众号或小程序
   */
  void doInfoOfAuthorizeCanceled(NotificationResponseBuilder builder, NotificationMeta notification,
      String appId);

  /**
   * 推送授权相关通知：授权更新通知.
   * <p>
   * 当公众号对第三方平台进行授权、取消授权、更新授权后，微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
   * 
   * @param builder
   *          微信推送响应构建器， see {@link NotificationResponseBuilder}，需使用
   *          builder指定回复什么信息
   * @param notification
   *          微信推送基本信息, see {@link NotificationMeta}
   * @param appId
   *          公众号或小程序
   * @param license
   *          授权码，可用于换取公众号的接口调用凭据，详细见上面的说明
   * @param expireAt
   *          授权码过期时间
   */
  void doInfoOfAuthorizeUpdated(NotificationResponseBuilder builder, NotificationMeta notification,
      String appId, String license, Date expireAt);

}
