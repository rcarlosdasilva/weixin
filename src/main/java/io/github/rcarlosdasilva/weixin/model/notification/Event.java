package io.github.rcarlosdasilva.weixin.model.notification;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationEventType;
import io.github.rcarlosdasilva.weixin.model.notification.bean.LocationInfo;
import io.github.rcarlosdasilva.weixin.model.notification.bean.PicsInfo;
import io.github.rcarlosdasilva.weixin.model.notification.bean.ScanCodeInfo;

/**
 * 微信通知消息中，事件相关内容
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@XStreamAlias("event")
public class Event {

  @XStreamAlias("Event")
  private String type;
  @XStreamAlias("EventKey")
  private String key;
  @XStreamAlias("MenuId")
  private String menuId;
  @XStreamAlias("Ticket")
  private String ticket;
  @XStreamAlias("Latitude")
  private Double latitude;
  @XStreamAlias("Longitude")
  private Double Longitude;
  @XStreamAlias("Precision")
  private Double precision;
  @XStreamAlias("MsgId")
  private Long messageId;
  @XStreamAlias("MsgID")
  private Long messageID;
  @XStreamAlias("Status")
  private String status;
  @XStreamAlias("TotalCount")
  private Integer totalCount;
  @XStreamAlias("FilterCount")
  private Integer filterCount;
  @XStreamAlias("SentCount")
  private Integer sentCount;
  @XStreamAlias("ErrorCount")
  private Integer errorCount;
  @XStreamAlias("ExpiredTime")
  private Long expiredTime;
  @XStreamAlias("FailTime")
  private Long failTime;
  @XStreamAlias("FailReason")
  private String failReason;
  @XStreamAlias("ScanCodeInfo")
  private ScanCodeInfo scanCodeInfo;
  @XStreamAlias("SendPicsInfo")
  private PicsInfo picsInfo;
  @XStreamAlias("SendLocationInfo")
  private LocationInfo locationInfo;
  @XStreamAlias("CardId")
  private String cardId;
  @XStreamAlias("UserCardCode")
  private String cardCode;
  @XStreamAlias("TransId")
  private String transactionId;
  @XStreamAlias("LocationId")
  private Integer storeId;
  @XStreamAlias("LocationName")
  private String storeName;
  @XStreamAlias("Fee")
  private String paidFee;
  @XStreamAlias("OriginalFee")
  private String originalFee;

  /**
   * 事件类型 (Event).
   * 
   * @return {@link NotificationEventType}
   */
  public NotificationEventType getType() {
    return NotificationEventType.byValue(type);
  }

  /**
   * 事件KEY值 (EventKey).
   * 
   * <p>
   * 1. 自定义菜单：与自定义菜单接口中KEY值对应<br>
   * 2. 扫描带参数二维码，用户未关注时，进行关注后的事件推送：事件KEY值，qrscene_为前缀，后面为二维码的参数值<br>
   * 3. 扫描带参数二维码，用户已关注时的事件推送：是一个32位无符号整数，即创建二维码时的二维码scene_id
   * 
   * @return key
   */
  public String getKey() {
    return key;
  }

  /**
   * 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了 (MenuID).
   * 
   * @return menu id
   */
  public String getMenuId() {
    return menuId;
  }

  /**
   * 二维码的ticket，可用来换取二维码图片.
   * 
   * @return ticket
   */
  public String getTicket() {
    return ticket;
  }

  /**
   * 地理位置纬度.
   * 
   * @return latitude
   */
  public Double getLatitude() {
    return latitude;
  }

  /**
   * 地理位置经度.
   * 
   * @return Longitude
   */
  public Double getLongitude() {
    return Longitude;
  }

  /**
   * 地理位置精度.
   * 
   * @return precision
   */
  public Double getPrecision() {
    return precision;
  }

  /**
   * 消息id（可能是模板消息，也可能是群发消息）.
   * <p>
   * 微信在两个地方分别给了msgId 和 msgID
   * 
   * @return message id
   */
  public Long getMessageId() {
    return messageId;
  }

  /**
   * 消息id（可能是模板消息，也可能是群发消息）.
   * <p>
   * 微信在两个地方分别给了msgId 和 msgID
   * 
   * @return message id
   */
  public Long getMessageID() {
    return messageID;
  }

  /**
   * 消息发送状态.
   * 
   * <p>
   * 1.模板消息时：成功为success，由于用户拒收（用户设置拒绝接收公众号消息）而失败时为failed:user
   * block，由于其他原因失败时为failed: system failed<br>
   * 2.群发的结构，为“send success”或“send fail”或“err(num)”。但send
   * success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，可能的情况如下：
   * err(10001), //涉嫌广告 err(20001), //涉嫌政治 err(20004), //涉嫌社会 err(20002), //涉嫌色情
   * err(20006), //涉嫌违法犯罪 err(20008), //涉嫌欺诈 err(20013), //涉嫌版权 err(22000),
   * //涉嫌互推(互相宣传) err(21000), //涉嫌其他
   * 
   * @return status
   */
  public String getStatus() {
    return status;
  }

  /**
   * tag_id下粉丝数；或者openid_list中的粉丝数.
   * 
   * @return 粉丝数
   */
  public Integer getTotalCount() {
    return totalCount;
  }

  /**
   * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount =
   * SentCount + ErrorCount.
   * 
   * @return 过滤后粉丝数
   */
  public Integer getFilterCount() {
    return filterCount;
  }

  /**
   * 发送成功的粉丝数.
   * 
   * @return 成功粉丝数
   */
  public Integer getSentCount() {
    return sentCount;
  }

  /**
   * 发送失败的粉丝数.
   * 
   * @return 失败粉丝数
   */
  public Integer getErrorCount() {
    return errorCount;
  }

  /**
   * 有效期 (整形)，指的是时间戳，将于该时间戳认证过期.
   * 
   * @return 过期时间
   */
  public Date getExpiredTime() {
    return new Date(expiredTime);
  }

  /**
   * 失败发生时间 (整形)，时间戳.
   * 
   * @return 失败时间
   */
  public Date getFailTime() {
    return new Date(failTime);
  }

  /**
   * 认证失败的原因.
   * 
   * @return 失败原因
   */
  public String getFailReason() {
    return failReason;
  }

  /**
   * 扫描信息.
   * 
   * @return 信息
   */
  public ScanCodeInfo getScanCodeInfo() {
    return scanCodeInfo;
  }

  /**
   * 发送的图片信息.
   * 
   * @return 信息
   */
  public PicsInfo getPicsInfo() {
    return picsInfo;
  }

  /**
   * 发送的位置信息.
   * 
   * @return 信息
   */
  public LocationInfo getLocationInfo() {
    return locationInfo;
  }

  /**
   * 卡券ID.
   * 
   * @return card id
   */
  public String getCardId() {
    return cardId;
  }

  /**
   * 卡券Code码.
   * 
   * @return code
   */
  public String getCardCode() {
    return cardCode;
  }

  /**
   * 微信支付交易订单号（只有使用买单功能核销的卡券才会出现）.
   * 
   * @return transaction id
   */
  public String getTransactionId() {
    return transactionId;
  }

  /**
   * 门店ID，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现）.
   * 
   * @return store id
   */
  public Integer getStoreId() {
    return storeId;
  }

  /**
   * 门店名称，当前卡券核销的门店名称（只有通过卡券商户助手和买单核销时才会出现）.
   * 
   * @return store name
   */
  public String getStoreName() {
    return storeName;
  }

  /**
   * 实付金额，单位为分.
   * 
   * @return 金额
   */
  public String getPaidFee() {
    return paidFee;
  }

  /**
   * 应付金额，单位为分.
   * 
   * @return 金额
   */
  public String getOriginalFee() {
    return originalFee;
  }

}
