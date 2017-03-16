package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Set;

import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 公众号配置缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AccountCache extends AbstractCache<Account> {

  private static final String DEFAULT_MARK = "ac_";
  private static final AccountCache instance = new AccountCache();

  private AccountCache() {
    this.mark = DEFAULT_MARK;
  }

  public static AccountCache getInstance() {
    return instance;
  }

  @Override
  public String lookup(Account value) {
    if (value == null) {
      return null;
    }
    String appid = value.getAppId();
    String mpid = value.getMpId();
    if (appid == null && mpid == null) {
      return null;
    }

    Set<String> keys = keys();
    for (String key : keys) {
      Account account = get(key);
      if (appid != null && appid.equals(account.getAppId())) {
        return key;
      }
      if (mpid != null && mpid.equals(account.getMpId())) {
        return key;
      }
    }
    return null;
  }

}
