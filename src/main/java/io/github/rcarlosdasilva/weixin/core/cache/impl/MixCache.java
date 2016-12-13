package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.rcarlosdasilva.weixin.core.cache.AbstractCache;
import io.github.rcarlosdasilva.weixin.core.cache.Cache;

/**
 * 存储混杂属性
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MixCache extends AbstractCache<Object> {

  private static Cache<Object> instance = new MixCache();

  private final Map<String, Object> cache;

  private MixCache() {
    cache = new ConcurrentHashMap<String, Object>();
  }

  public static Cache<Object> instance() {
    return instance;
  }

  @Override
  public Object get(String key) {
    return cache.get(key);
  }

  @Override
  public Object put(String key, Object object) {
    return cache.put(key, object);
  }

  @Override
  public Object remove(String key) {
    return cache.remove(key);
  }

}
