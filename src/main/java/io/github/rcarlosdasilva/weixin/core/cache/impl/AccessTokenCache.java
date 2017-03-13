package io.github.rcarlosdasilva.weixin.core.cache.impl;

import io.github.rcarlosdasilva.weixin.core.cache.Cache;
import io.github.rcarlosdasilva.weixin.model.response.certificate.AccessTokenResponse;

/**
 * access_token缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AccessTokenCache extends AbstractCache<AccessTokenResponse> {

  private static final String DEFAULT_MARK = "AccessTokenCache";
  private static final Cache<AccessTokenResponse> instance = new AccessTokenCache();

  private AccessTokenCache() {
    this.mark = DEFAULT_MARK;
  }

  public static Cache<AccessTokenResponse> getInstance() {
    return instance;
  }

}
