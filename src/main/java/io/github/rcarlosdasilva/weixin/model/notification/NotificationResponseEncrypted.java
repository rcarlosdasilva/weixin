package io.github.rcarlosdasilva.weixin.model.notification;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 加密的通知模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@XStreamAlias("xml")
public class NotificationResponseEncrypted implements NotificationResponse {

  @XStreamAlias("Encrypt")
  private String ciphertext;
  @XStreamAlias("MsgSignature")
  private String signature;
  @XStreamAlias("TimeStamp")
  private long timestamp;
  @XStreamAlias("Nonce")
  private String nonce;

  /**
   * 构造函数.
   * 
   * @param ciphertext
   *          加密后的公众号待回复用户的消息
   * @param signature
   *          签名串
   * @param timestamp
   *          时间戳，可以自己生成，也可以用URL参数的timestamp
   * @param nonce
   *          随机串，可以自己生成，也可以用URL参数的nonce
   */
  public NotificationResponseEncrypted(String ciphertext, String signature, long timestamp,
      String nonce) {
    this.ciphertext = ciphertext;
    this.signature = signature;
    this.timestamp = timestamp;
    this.nonce = nonce;
  }

}
