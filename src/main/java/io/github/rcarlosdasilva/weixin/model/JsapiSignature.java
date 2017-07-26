package io.github.rcarlosdasilva.weixin.model;

/**
 * JS SDK签名模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class JsapiSignature {

  private String appId;
  private String timestamp;
  private String nonce;
  private String signature;
  private String url;
  private String ticket;

  public JsapiSignature() {
  }

  /**
   * .
   * 
   * @param appId
   *          AppId
   * @param ticket
   *          Ticket
   * @param signature
   *          签名
   * @param url
   *          签名地址
   * @param timestamp
   *          时间戳
   * @param nonce
   *          随机值
   */
  public JsapiSignature(String appId, String ticket, String signature, String url, String timestamp,
      String nonce) {
    this.appId = appId;
    this.ticket = ticket;
    this.signature = signature;
    this.url = url;
    this.timestamp = timestamp;
    this.nonce = nonce;
  }

  public String getAppId() {
    return appId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getNonce() {
    return nonce;
  }

  public String getSignature() {
    return signature;
  }

  public String getUrl() {
    return url;
  }

  public String getTicket() {
    return ticket;
  }

}
