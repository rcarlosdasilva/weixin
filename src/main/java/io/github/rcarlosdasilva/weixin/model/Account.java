package io.github.rcarlosdasilva.weixin.model;

import java.io.Serializable;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.common.dictionary.AccountType;
import io.github.rcarlosdasilva.weixin.common.dictionary.EncryptionType;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;

/**
 * 公众号账号模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Account implements Serializable {

  private static final long serialVersionUID = 9204192729241765932L;

  private String key;
  private String appId;
  private String appSecret;
  private String refreshToken;
  private String mpId;
  private String nickname;
  private AccountType accountType;
  private boolean certified;
  private String token;
  private String aesKey;
  private EncryptionType encryptionType = EncryptionType.PLAIN_TEXT;
  private int retryTimes = 2;
  private Object extension;
  private boolean withOpenPlatform = true;

  private Account() {
  }

  /**
   * 指定必须的AppId（开发平台授权方）.
   * 
   * @param appId
   *          appid
   * @return {@link Account}
   */
  public static Account create(String appId) {
    Account account = new Account();
    account.appId = appId;
    return account;
  }

  /**
   * 指定必须的AppId与AppSecret.
   * <p>
   * <b>建议使用开放平台</b>
   * 
   * @param appId
   *          appid
   * @param appSecret
   *          appsecret
   * @return {@link Account}
   */
  public static Account create(String appId, String appSecret) {
    Account account = new Account();
    account.appId = appId;
    account.appSecret = appSecret;
    return account;
  }

  /**
   * 设置key.
   * 
   * @param key
   *          key
   * @return {@link Account}
   */
  public Account withKey(String key) {
    this.key = key;
    return this;
  }

  /**
   * 设置服务器配置，用于消息加解密，推荐使用安全模式.
   * <p>
   * <b>建议使用开放平台，开放平台的通知均为加密</b>
   * 
   * @param token
   *          Token(令牌)
   * @param aesKey
   *          EncodingAESKey(消息加解密密钥)
   * @param encryptionType
   *          消息加解密方式
   * @return {@link Account}
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
   * @return {@link Account}
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
   * @return {@link Account}
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
   * 
   * @return 公众号键
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

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getMpId() {
    return mpId;
  }

  /**
   * 公众号原始ID.
   * 
   * @param mpId
   *          原始ID
   * @return {@link Account}
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
   * 
   * @param nickname
   *          昵称
   * @return {@link Account}
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
   * 
   * @param accountType
   *          类型
   * @return {@link Account}
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
   * 
   * @param certified
   *          是否认证
   * @return {@link Account}
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
   * 
   * @param token
   *          令牌
   * @return {@link Account}
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
   * 
   * @param aesKey
   *          aesKey
   * @return {@link Account}
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
   * 
   * @param encryptionType
   *          加密方式
   * @return {@link Account}
   */
  public Account setEncryptionType(EncryptionType encryptionType) {
    this.encryptionType = encryptionType;
    return this;
  }

  public int getRetryTimes() {
    return retryTimes;
  }

  /**
   * 接口请求失败后的重试次数.
   * 
   * @param retryTimes
   *          次数
   */
  public void setRetryTimes(int retryTimes) {
    this.retryTimes = retryTimes;
  }

  public Object getExtension() {
    return extension;
  }

  /**
   * 可用来设置与业务层面相关的扩展参数，当做传递载体使用.
   * <p>
   * 开在代码中借用{@link Registration#lookup(String)}获取公众号信息
   * 
   * @param extension
   *          扩展信息
   */
  public void setExtension(Object extension) {
    this.extension = extension;
  }

  /**
   * 使用安全模式（也可能是兼容模式，均使用安全模式处理）.
   * 
   * @return 是否是安全模式
   */
  public boolean isSafeMode() {
    return this.encryptionType != null && this.encryptionType != EncryptionType.PLAIN_TEXT
        && !Strings.isNullOrEmpty(this.token) && !Strings.isNullOrEmpty(this.aesKey);
  }

  public boolean isWithOpenPlatform() {
    return withOpenPlatform;
  }

  /**
   * 公众号是否是通过开放平台授权获取.
   * 
   * @param withOpenPlatform
   *          boolean
   */
  public void setWithOpenPlatform(boolean withOpenPlatform) {
    this.withOpenPlatform = withOpenPlatform;
  }

  @Override
  public String toString() {
    return this.appId + "@" + this.appSecret;
  }

}
