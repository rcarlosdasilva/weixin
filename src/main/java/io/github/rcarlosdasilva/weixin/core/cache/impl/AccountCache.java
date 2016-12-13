package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Preconditions;

import io.github.rcarlosdasilva.weixin.core.cache.AbstractCache;
import io.github.rcarlosdasilva.weixin.core.cache.Cache;
import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 公众号配置缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AccountCache extends AbstractCache<Account> {

  private static Cache<Account> instance = new AccountCache();

  private final Map<String, Account> cache;

  private AccountCache() {
    cache = new ConcurrentHashMap<String, Account>();
  }

  public static Cache<Account> instance() {
    return instance;
  }

  @Override
  public Account get(String key) {
    return cache.get(key);
  }

  @Override
  public Account put(String key, Account value) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(value);
    value.setKey(key);
    return cache.put(key, value);
  }

  @Override
  public Account remove(String key) {
    return cache.remove(key);
  }

  @Override
  public Account lookup(Object value) {
    Preconditions.checkNotNull(value);

    String id = value.toString();
    Collection<Account> accounts = cache.values();
    for (Account account : accounts) {
      if (id.equals(account.getAppId()) || id.equals(account.getMpId())) {
        return account;
      }
    }
    return null;
  }

}
