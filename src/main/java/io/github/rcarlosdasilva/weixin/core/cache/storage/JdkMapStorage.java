package io.github.rcarlosdasilva.weixin.core.cache.storage;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.core.cache.CacheStorage;
import io.github.rcarlosdasilva.weixin.core.cache.Cacheable;
import io.github.rcarlosdasilva.weixin.core.cache.Lookup;

public final class JdkMapStorage<V extends Cacheable> implements CacheStorage<V> {

  private final Map<String, V> cache;

  public JdkMapStorage() {
    cache = Maps.newConcurrentMap();
  }

  @Override
  public Collection<String> keys() {
    return cache.keySet();
  }

  @Override
  public int size() {
    return cache.size();
  }

  @Override
  public void clear() {
    cache.clear();
  }

  @Override
  public boolean exists(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return false;
    }
    return cache.containsKey(key);
  }

  @Override
  public V get(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return null;
    }
    return cache.get(key);
  }

  @Override
  public V put(String key, V object) {
    return cache.put(key, object);
  }

  @Override
  public V put(String key, V object, int timeout) {
    return put(key, object);
  }

  @Override
  public boolean remove(String key) {
    cache.remove(key);
    return true;
  }

  @Override
  public V lookup(Lookup<V> lookup) {
    Collection<String> keys = keys();
    for (String key : keys) {
      V obj = get(key);
      if (lookup.isYou(obj)) {
        return obj;
      }
    }
    return null;
  }

  @Override
  public List<V> lookupAll(Lookup<V> lookup) {
    Collection<String> keys = keys();
    List<V> result = Lists.newArrayList();
    for (String key : keys) {
      V obj = get(key);
      if (lookup.isYou(obj)) {
        result.add(obj);
      }
    }
    return result;
  }

}
