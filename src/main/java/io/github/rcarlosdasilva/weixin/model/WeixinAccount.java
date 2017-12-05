package io.github.rcarlosdasilva.weixin.model;

import java.io.Serializable;
import java.util.Map;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.common.dictionary.AccountType;
import io.github.rcarlosdasilva.weixin.common.dictionary.EncryptionType;
import io.github.rcarlosdasilva.weixin.common.dictionary.WeixinCertificationType;

/**
 * 公众号账号模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class WeixinAccount implements Serializable {

  private static final long serialVersionUID = 9204192729241765932L;

  private String key;
  private String appId;
  private String mpId;
  private AccountType accountType;
  private WeixinCertificationType certificationType;
  private String nickname;

  private String appSecret;
  private EncryptionType encryptionType = EncryptionType.PLAIN_TEXT;
  private String aesToken;
  private String aesKey;

  private String refreshToken;
  private boolean withOpenPlatform = true;

  private Map<String, Object> extension;

  private WeixinAccount() {
  }

  /**
   * 指定必须的AppId（开发平台授权方）.
   * 
   * @param appId
   *          appid
   * @return {@link WeixinAccount}
   */
  public static WeixinAccount create(String appId) {
    WeixinAccount account = new WeixinAccount();
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
   * @return {@link WeixinAccount}
   */
  public static WeixinAccount create(String appId, String appSecret) {
    WeixinAccount account = new WeixinAccount();
    account.appId = appId;
    account.appSecret = appSecret;
    account.withOpenPlatform = false;
    return account;
  }

  /**
   * 设置key.
   * 
   * @param key
   *          key
   * @return {@link WeixinAccount}
   */
  public WeixinAccount withKey(String key) {
    this.key = key;
    return this;
  }

  /**
   * 设置服务器配置，用于消息加解密，推荐使用安全模式.
   * <p>
   * <b>建议使用开放平台，开放平台的通知均为加密</b>
   * 
   * @param aesToken
   *          Token(令牌)
   * @param aesKey
   *          EncodingAESKey(消息加解密密钥)
   * @param encryptionType
   *          消息加解密方式
   * @return {@link WeixinAccount}
   * @see <a href=
   *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318479&token=&lang=zh_CN"
   *      >消息加解密接入指引</a>
   */
  public WeixinAccount setServerSecurity(String aesToken, String aesKey,
      EncryptionType encryptionType) {
    this.aesToken = aesToken;
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
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setBasic(AccountType accountType,
      WeixinCertificationType certificationType) {
    this.accountType = accountType;
    this.certificationType = certificationType;
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
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setBasic(AccountType accountType, WeixinCertificationType certificationType,
      String mpId, String nickname) {
    this.accountType = accountType;
    this.certificationType = certificationType;
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
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setMpId(String mpId) {
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
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setNickname(String nickname) {
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
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setAccountType(AccountType accountType) {
    this.accountType = accountType;
    return this;
  }

  public WeixinCertificationType getCertificationType() {
    return certificationType;
  }

  /**
   * 认证类型.
   * 
   * @param certificationType
   *          认证类型
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setCertificationType(WeixinCertificationType certificationType) {
    this.certificationType = certificationType;
    return this;
  }

  public String getAesToken() {
    return aesToken;
  }

  /**
   * 令牌.
   * 
   * @param aesToken
   *          令牌
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setAesToken(String aesToken) {
    this.aesToken = aesToken;
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
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setAesKey(String aesKey) {
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
   * @return {@link WeixinAccount}
   */
  public WeixinAccount setEncryptionType(EncryptionType encryptionType) {
    this.encryptionType = encryptionType;
    return this;
  }

  public Object getExtension() {
    return extension;
  }

  /**
   * 可用来设置与业务层面相关的扩展参数，当做传递载体使用.
   * <p>
   * 开在代码中借用{@link Registration#get(String)}获取公众号信息
   * 
   * @param extension
   *          扩展信息
   */
  public void setExtension(Map<String, Object> extension) {
    this.extension = extension;
  }

  /**
   * 使用安全模式（也可能是兼容模式，均使用安全模式处理）.
   * 
   * @return 是否是安全模式
   */
  public boolean isSafeMode() {
    return this.encryptionType != null && this.encryptionType != EncryptionType.PLAIN_TEXT
        && !Strings.isNullOrEmpty(this.aesToken) && !Strings.isNullOrEmpty(this.aesKey);
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
