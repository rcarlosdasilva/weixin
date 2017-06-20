package io.github.rcarlosdasilva.weixin.core.registry;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccessTokenCacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCacheHandler;
import io.github.rcarlosdasilva.weixin.core.exception.UnmakableAccountKeyException;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.OpenPlatform;

public class Registration implements Serializable {

  private static final long serialVersionUID = 5219550386903571660L;

  public static final Registration instance = new Registration();

  private Setting setting;
  private OpenPlatform openPlatform;
  private List<Account> pending = Lists.newArrayList();

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
    this.pending.add(account);
  }

  public List<Account> getPending() {
    return pending;
  }

  public void process() {
    for (Account account : pending) {
      cacheAccount(account);
    }
    pending.clear();
  }

  private void cacheAccount(Account account) {
    if (!Registration.accountExists(account.getKey())) {
      AccountCacheHandler.getInstance().put(account.getKey(), account);
    }
  }

  private static boolean accountExists(String key) {
    return AccountCacheHandler.getInstance().get(key) != null;
  }

  public static void updateAccount(String key, Account account) {
    if (accountExists(key)) {
      unregister(key);
    }
    account.setKey(key);
    AccountCacheHandler.getInstance().put(key, account);
  }

  /**
   * 取消注册的公众号信息.
   * 
   * @param key
   *          注册时的key，或是公众号appid
   */
  public static void unregister(final String key) {
    if (Strings.isNullOrEmpty(key)) {
      throw new UnmakableAccountKeyException();
    }

    final Account account = lookup(key);
    final String realKey = account.getKey();

    if (account != null) {
      AccountCacheHandler.getInstance().remove(realKey);
      AccessTokenCacheHandler.getInstance().remove(realKey);
    }
  }

  public static void unregisterUnique() {
    unregister(Convention.DEFAULT_UNIQUE_WEIXIN_KEY);
  }

  /**
   * 获取公众号信息
   * 
   * @param key
   *          注册时的key（如果是使用开放平台，则默认是appid）
   * @return 公众号信息
   */
  public static Account account(String key) {
    return AccountCacheHandler.getInstance().get(key);
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

    account = new Account(key, null);
    return AccountCacheHandler.getInstance().lookup(account);
  }

}
