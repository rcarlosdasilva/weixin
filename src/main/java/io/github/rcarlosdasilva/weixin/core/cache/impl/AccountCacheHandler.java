package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.core.cache.holder.MapHandler;
import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 公众号配置缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AccountCacheHandler extends AbstractCacheHandler<Account> {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static final String DEFAULT_MARK = "account";
  private static final AccountCacheHandler instance = new AccountCacheHandler();

  private AccountCacheHandler() {
    this.mark = DEFAULT_MARK;
  }

  public static AccountCacheHandler getInstance() {
    return instance;
  }

  @Override
  public Account lookup(Account value) {
    if (value == null || Strings.isNullOrEmpty(value.getAppId())) {
      return null;
    }

    String identify = value.getAppId();

    Collection<String> keys = keys();
    for (String key : keys) {
      Account account = get(key);
      if (account.getAppId() != null && account.getAppId().equals(identify)) {
        return account;
      }
      if (account.getMpId() != null && account.getMpId().equals(identify)) {
        return account;
      }
    }
    return null;
  }

  /**
   * 将公众号信息从Map中迁移到Redis中
   */
  public void migrateAccount() {
    if (!isRedis()) {
      logger.warn("未配置Redis环境");
      return;
    }

    Map<String, Account> rejected = MapHandler.<Account>getObject(mark);
    for (Map.Entry<String, Account> entry : rejected.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
    rejected.clear();
  }

}
