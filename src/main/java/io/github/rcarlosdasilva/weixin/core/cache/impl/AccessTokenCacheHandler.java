package io.github.rcarlosdasilva.weixin.core.cache.impl;

import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.model.AccessToken;

/**
 * access_token缓存
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class AccessTokenCacheHandler extends AbstractCacheHandler<AccessToken> {

  private static final String DEFAULT_MARK = "access_token";
  private static final CacheHandler<AccessToken> instance = new AccessTokenCacheHandler();

  private AccessTokenCacheHandler() {
    this.mark = DEFAULT_MARK;
  }

  public static CacheHandler<AccessToken> getInstance() {
    return instance;
  }

}
