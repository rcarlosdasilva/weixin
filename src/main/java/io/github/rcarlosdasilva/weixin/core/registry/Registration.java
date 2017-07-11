package io.github.rcarlosdasilva.weixin.core.registry;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccessTokenCacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCacheHandler;
import io.github.rcarlosdasilva.weixin.core.exception.InvalidAccountException;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.OpenPlatform;

public class Registration implements Serializable {

  private static final long serialVersionUID = 5219550386903571660L;

  private static final Logger LOGGER = LoggerFactory.getLogger(WeixinRegistry.class);

  public static final Registration instance = new Registration();

  private Setting setting = new Setting();
  private OpenPlatform openPlatform;

  private Registration() {
  }

  public static Registration getInstance() {
    return instance;
  }

  public Setting getSetting() {
    return setting;
  }

  public void setSetting(Setting setting) {
    this.setting = setting;
  }

  public OpenPlatform getOpenPlatform() {
    return openPlatform;
  }

  public void setOpenPlatform(OpenPlatform openPlatform) {
    this.openPlatform = openPlatform;
  }

  public void addAccount(Account account) {
    if (Registration.accountExists(account.getKey())) {
      LOGGER.warn("尝试添加一个已经存在的公众号信息：[{}]，本方法无法覆盖已有公众号信息，请使用updateAccount", account.getKey());
      return;
    }

    if (verifyAccount(account)) {
      AccountCacheHandler.getInstance().put(account.getKey(), account);
    }
  }

  public void updateAccount(Account account) {
    if (verifyAccount(account)) {
      unregister(account.getKey());
      AccountCacheHandler.getInstance().put(account.getKey(), account);
    }
  }

  private boolean verifyAccount(Account account) {
    if (account == null || Strings.isNullOrEmpty(account.getAppId())) {
      throw new InvalidAccountException();
    }

    if (!account.isWithOpenPlatform() && Strings.isNullOrEmpty(account.getAppSecret())) {
      LOGGER.warn("未找到公众号的app_scret，该公众号将不被注册");
      return false;
    }
    if (account.isWithOpenPlatform() && Strings.isNullOrEmpty(account.getRefreshToken())) {
      LOGGER.warn("未找到开放平台授权方的刷新令牌authorizer_refresh_token，该公众号将不被注册");
      return false;
    }

    if (Strings.isNullOrEmpty(account.getMpId())) {
      LOGGER.warn("未设置Account.mpId，对公众号appid: [{}]来说，当微信通知回调或其他操作的时候，不设置可能会导致无法正确找到对应的公众号信息",
          account.getAppId());
    }
    if (Strings.isNullOrEmpty(account.getKey())) {
      LOGGER.warn("未设置Account.key，将使用公众号的appid：[{}]作为默认key", account.getAppId());
      account.withKey(account.getAppId());
    }

    return true;
  }

  /**
   * 取消注册的公众号信息.
   * 
   * @param key
   *          注册时的key，或是公众号appid
   */
  public void unregister(final String key) {
    if (Strings.isNullOrEmpty(key)) {
      return;
    }

    final Account account = lookup(key);

    if (account != null) {
      final String realKey = account.getKey();
      AccountCacheHandler.getInstance().remove(realKey);
      AccessTokenCacheHandler.getInstance().remove(realKey);
      LOGGER.info("已取消注册公众号信息：[{}]", key);
    } else {
      LOGGER.warn("未找到可取消注册的公众号信息：[{}]", key);
    }
  }

  public void unregisterUnique() {
    unregister(Convention.DEFAULT_UNIQUE_WEIXIN_KEY);
  }

  private static boolean accountExists(String key) {
    return lookup(key) != null;
  }

  /**
   * 通过appId或原始ID获取账号配置信息.
   * 
   * @param key
   *          可以为公众号appId或者公众号原始ID，也可以是注册时的key
   * @return {@link Account}
   */
  public static Account lookup(String key) {
    Account account = AccountCacheHandler.getInstance().get(key);
    if (account != null) {
      return account;
    }

    return AccountCacheHandler.getInstance().lookup(Account.create(key));
  }

}
