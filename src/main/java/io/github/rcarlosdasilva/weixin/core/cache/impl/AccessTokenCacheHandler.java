package io.github.rcarlosdasilva.weixin.core.cache.impl;

import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.model.response.certificate.AccessTokenResponse;

/**
 * access_token缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AccessTokenCacheHandler extends AbstractCacheHandler<AccessTokenResponse> {

  private static final String DEFAULT_MARK = "access_token";
  private static final CacheHandler<AccessTokenResponse> instance = new AccessTokenCacheHandler();

  private AccessTokenCacheHandler() {
    this.mark = DEFAULT_MARK;
  }

  public static CacheHandler<AccessTokenResponse> getInstance() {
    return instance;
  }

}
