package io.github.rcarlosdasilva.weixin.model.response.open.auth.bean;

import java.io.Serializable;

import io.github.rcarlosdasilva.weixin.common.dictionary.AccountType;
import io.github.rcarlosdasilva.weixin.common.dictionary.AccountVerifiedType;

/**
 * 授权者信息
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class LicensorInfromation implements Serializable {

  private static final long serialVersionUID = 4147462423543629448L;

  private String nickName;
  private String logo;
  private int accountType;
  private int accountVerifiedType;
  private String mpId;
  private String principalName;
  private String accountName;
  private String qrCodeUrl;
  private boolean businessStoreOpened;
  private boolean businessScanOpened;
  private boolean businessPayOpened;
  private boolean businessCardOpened;
  private boolean businessShakeOpened;

  /**
   * 授权方昵称(nick_name).
   * 
   * @return nick name
   */
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * 授权方头像(head_img).
   * 
   * @return logo
   */
  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  /**
   * 授权方公众号类型(service_type_info).
   * <p>
   * 0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号.
   * 
   * @return account type
   */
  public AccountType getAccountType() {
    return AccountType.byCode(accountType);
  }

  public void setAccountType(int accountType) {
    this.accountType = accountType;
  }

  /**
   * 授权方认证类型(verify_type_info).
   * <p>
   * -1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，
   * 3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，
   * 但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
   * 
   * @return account verify type
   */
  public AccountVerifiedType getAccountVerifiedType() {
    return AccountVerifiedType.byCode(accountVerifiedType);
  }

  public void setAccountVerifiedType(int accountVerifiedType) {
    this.accountVerifiedType = accountVerifiedType;
  }

  /**
   * 授权方公众号的原始ID(user_name).
   * 
   * @return mp id
   */
  public String getMpId() {
    return mpId;
  }

  public void setMpId(String mpId) {
    this.mpId = mpId;
  }

  /**
   * 公众号的主体名称(principal_name).
   * 
   * @return principal name
   */
  public String getPrincipalName() {
    return principalName;
  }

  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
  }

  /**
   * 授权方公众号所设置的微信号，可能为空(alias).
   * 
   * @return account name
   */
  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  /**
   * 二维码图片的URL，开发者最好自行也进行保存(qrcode_url).
   * 
   * @return qr code url
   */
  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }

  /**
   * open_store:是否开通微信门店功能.
   * 
   * @return boolean
   */
  public boolean isBusinessStoreOpened() {
    return businessStoreOpened;
  }

  public void setBusinessStoreOpened(boolean businessStoreOpened) {
    this.businessStoreOpened = businessStoreOpened;
  }

  /**
   * open_scan:是否开通微信扫商品功能.
   * 
   * @return boolean
   */
  public boolean isBusinessScanOpened() {
    return businessScanOpened;
  }

  public void setBusinessScanOpened(boolean businessScanOpened) {
    this.businessScanOpened = businessScanOpened;
  }

  /**
   * open_pay:是否开通微信支付功能.
   * 
   * @return boolean
   */
  public boolean isBusinessPayOpened() {
    return businessPayOpened;
  }

  public void setBusinessPayOpened(boolean businessPayOpened) {
    this.businessPayOpened = businessPayOpened;
  }

  /**
   * open_card:是否开通微信卡券功能.
   * 
   * @return boolean
   */
  public boolean isBusinessCardOpened() {
    return businessCardOpened;
  }

  public void setBusinessCardOpened(boolean businessCardOpened) {
    this.businessCardOpened = businessCardOpened;
  }

  /**
   * open_shake:是否开通微信摇一摇功能.
   * 
   * @return boolean
   */
  public boolean isBusinessShakeOpened() {
    return businessShakeOpened;
  }

  public void setBusinessShakeOpened(boolean businessShakeOpened) {
    this.businessShakeOpened = businessShakeOpened;
  }

}
