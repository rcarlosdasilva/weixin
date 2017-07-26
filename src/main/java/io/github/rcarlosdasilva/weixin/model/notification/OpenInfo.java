package io.github.rcarlosdasilva.weixin.model.notification;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 开放平台通知内容
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
@XStreamAlias("info")
public class OpenInfo {

  @XStreamAlias("ComponentVerifyTicket")
  private String ticket;
  @XStreamAlias("AuthorizerAppid")
  private String licensorAppId;
  @XStreamAlias("AuthorizationCode")
  private String license;
  @XStreamAlias("AuthorizationCodeExpiredTime")
  private Long licenseExpireAt;

  /**
   * Ticket内容
   * 
   * @return ticket
   */
  public String getTicket() {
    return ticket;
  }

  /**
   * 公众号或小程序的appid.
   * 
   * @return appid
   */
  public String getLicensorAppId() {
    return licensorAppId;
  }

  /**
   * 授权码，可用于换取公众号的接口调用凭据，详细见上面的说明.
   * 
   * @return authorization code
   */
  public String getLicense() {
    return license;
  }

  /**
   * 授权码过期时间.
   * 
   * @return expired time
   */
  public Date getLicenseExpireAt() {
    return new Date(licenseExpireAt);
  }

}
