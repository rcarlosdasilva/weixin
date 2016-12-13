package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.rcarlosdasilva.weixin.core.cache.AbstractCache;
import io.github.rcarlosdasilva.weixin.core.cache.Cache;
import io.github.rcarlosdasilva.weixin.model.response.certificate.AccessTokenResponse;

/**
 * access_token缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AccessTokenCache extends AbstractCache<AccessTokenResponse> {

  private static Cache<AccessTokenResponse> instance = new AccessTokenCache();

  private final Map<String, AccessTokenResponse> cache;

  private AccessTokenCache() {
    cache = new ConcurrentHashMap<String, AccessTokenResponse>();
  }

  public static Cache<AccessTokenResponse> instance() {
    return instance;
  }

  @Override
  public AccessTokenResponse get(String key) {
    return cache.get(key);
  }

  @Override
  public AccessTokenResponse put(String key, AccessTokenResponse value) {
    return cache.put(key, value);
  }

  @Override
  public AccessTokenResponse remove(String key) {
    return cache.remove(key);
  }

}
