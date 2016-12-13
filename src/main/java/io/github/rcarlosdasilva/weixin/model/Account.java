package io.github.rcarlosdasilva.weixin.model;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.common.dictionary.AccountType;
import io.github.rcarlosdasilva.weixin.common.dictionary.EncryptionType;

/**
 * 公众号账号模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Account {

  private String key;
  private String appId;
  private String appSecret;
  private String mpId;
  private String nickname;
  private AccountType accountType;
  private boolean certified;
  private String token;
  private String aesKey;
  private EncryptionType encryptionType = EncryptionType.PLAIN_TEXT;

  /**
   * 指定必须的AppId与AppSecret.
   */
  public Account(String appId, String appSecret) {
    this.appId = appId;
    this.appSecret = appSecret;
  }

  /**
   * 设置服务器配置，用于消息加解密，推荐使用安全模式.
   * 
   * @param token
   *          Token(令牌)
   * @param aesKey
   *          EncodingAESKey(消息加解密密钥)
   * @param encryptionType
   *          消息加解密方式
   * @see <a href=
   *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318479&token=&lang=zh_CN"
   *      >消息加解密接入指引</a>
   */
  public Account setServerSecurity(String token, String aesKey, EncryptionType encryptionType) {
    this.token = token;
    this.aesKey = aesKey;
    this.encryptionType = encryptionType;
    return this;
  }

  /**
   * 设置公众号基本信息.
   * 
   * @param accountType
   *          公众号类型
   * @param certified
   *          是否认证
   */
  public Account setBasic(AccountType accountType, boolean certified) {
    this.accountType = accountType;
    this.certified = certified;
    return this;
  }

  /**
   * 设置公众号基本信息.
   * 
   * @param accountType
   *          公众号类型
   * @param certified
   *          是否认证
   * @param mpId
   *          公众号原始ID
   * @param nickname
   *          公众号昵称
   */
  public Account setBasic(AccountType accountType, boolean certified, String mpId,
      String nickname) {
    this.accountType = accountType;
    this.certified = certified;
    this.mpId = mpId;
    this.nickname = nickname;
    return this;
  }

  /**
   * 注册时用的key键.
   */
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getAppId() {
    return appId;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public String getMpId() {
    return mpId;
  }

  /**
   * 公众号原始ID.
   */
  public Account setMpId(String mpId) {
    this.mpId = mpId;
    return this;
  }

  public String getNickname() {
    return nickname;
  }

  /**
   * 公众号昵称.
   */
  public Account setNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  /**
   * 公众号类型.
   */
  public Account setAccountType(AccountType accountType) {
    this.accountType = accountType;
    return this;
  }

  public boolean isCertified() {
    return certified;
  }

  /**
   * 是否认证.
   */
  public Account setCertified(boolean certified) {
    this.certified = certified;
    return this;
  }

  public String getToken() {
    return token;
  }

  /**
   * 令牌.
   */
  public Account setToken(String token) {
    this.token = token;
    return this;
  }

  public String getAesKey() {
    return aesKey;
  }

  /**
   * AES安全加密密钥.
   */
  public Account setAesKey(String aesKey) {
    this.aesKey = aesKey;
    return this;
  }

  public EncryptionType getEncryptionType() {
    return encryptionType;
  }

  /**
   * 消息加解密方式.
   */
  public Account setEncryptionType(EncryptionType encryptionType) {
    this.encryptionType = encryptionType;
    return this;
  }

  /**
   * 使用安全模式（也可能是兼容模式，均使用安全模式处理）.
   */
  public boolean isSafeMode() {
    return this.encryptionType != null && this.encryptionType != EncryptionType.PLAIN_TEXT
        && !Strings.isNullOrEmpty(this.token) && !Strings.isNullOrEmpty(this.aesKey);
  }

  @Override
  public String toString() {
    return this.appId + "@" + this.appSecret;
  }

}
