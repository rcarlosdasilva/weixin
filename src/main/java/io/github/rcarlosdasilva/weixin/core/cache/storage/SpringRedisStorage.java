package io.github.rcarlosdasilva.weixin.core.cache.storage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.CacheStorage;
import io.github.rcarlosdasilva.weixin.core.cache.Cacheable;
import io.github.rcarlosdasilva.weixin.core.cache.Lookup;
import io.github.rcarlosdasilva.weixin.core.cache.storage.redis.RedisHandler;
import io.github.rcarlosdasilva.weixin.core.cache.storage.redis.RedisKey;

@SuppressWarnings("unchecked")
public class SpringRedisStorage<V extends Cacheable> implements CacheStorage<V> {

  private String group;
  private String keyPattern;

  public SpringRedisStorage(String group) {
    this.group = group;
    keyPattern = RedisKey.fullKey(group, Convention.DEFAULT_REDIS_KEY_PATTERN);
  }

  @Override
  public Collection<String> keys() {
    Set<String> keys = RedisHandler.getRedisTemplate().keys(keyPattern);
    return RedisKey.shortKeys(keys);
  }

  @Override
  public int size() {
    return keys().size();
  }

  @Override
  public void clear() {
    RedisHandler.getRedisTemplate().delete(keys());
  }

  @Override
  public boolean exists(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return false;
    }

    String fullKey = RedisKey.fullKey(group, key);
    return RedisHandler.getRedisTemplate().hasKey(fullKey);
  }

  @Override
  public V get(String key) {
    String fullKey = RedisKey.fullKey(group, key);
    Object obj = RedisHandler.getRedisTemplate().opsForValue().get(fullKey);
    if (obj == null) {
      return null;
    }
    return (V) obj;
  }

  @Override
  public V put(String key, V object) {
    String fullKey = RedisKey.fullKey(group, key);
    RedisHandler.getRedisTemplate().opsForValue().set(fullKey, object);
    return object;
  }

  @Override
  public V put(String key, V object, int timeout) {
    String fullKey = RedisKey.fullKey(group, key);
    RedisHandler.getRedisTemplate().opsForValue().set(fullKey, object, timeout, TimeUnit.SECONDS);
    return object;
  }

  @Override
  public boolean remove(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return false;
    }

    String fullKey = RedisKey.fullKey(group, key);
    RedisHandler.getRedisTemplate().delete(fullKey);
    return true;
  }

  @Override
  public V lookup(Lookup<V> lookup) {
    Collection<String> keys = keys();

    for (String key : keys) {
      Object obj = RedisHandler.getRedisTemplate().opsForValue().get(key);
      if (obj == null) {
        continue;
      }
      if (lookup.isYou((V) obj)) {
        return (V) obj;
      }
    }
    return null;
  }

  @Override
  public List<V> lookupAll(Lookup<V> lookup) {
    Collection<String> keys = keys();

    List<V> result = Lists.newArrayList();
    for (String key : keys) {
      Object obj = RedisHandler.getRedisTemplate().opsForValue().get(key);
      if (obj == null) {
        continue;
      }
      if (lookup.isYou((V) obj)) {
        result.add((V) obj);
      }
    }
    return result;
  }
}
