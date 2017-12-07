package io.github.rcarlosdasilva.weixin.core.cache.storage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.core.cache.CacheStorage;
import io.github.rcarlosdasilva.weixin.core.cache.Cacheable;
import io.github.rcarlosdasilva.weixin.core.cache.Lookup;
import io.github.rcarlosdasilva.weixin.core.cache.storage.redis.RedisHandler;
import io.github.rcarlosdasilva.weixin.core.cache.storage.redis.RedisKey;
import redis.clients.jedis.Jedis;

public class SimpleRedisStorage<V extends Cacheable> implements CacheStorage<V> {

  private String group;
  private String keyPattern;

  public SimpleRedisStorage(String group) {
    this.group = group;
    keyPattern = RedisKey.fullKey(group, Convention.DEFAULT_REDIS_KEY_PATTERN);
  }

  @Override
  public Collection<String> keys() {
    Jedis jedis = RedisHandler.getJedis();
    Set<String> keys = jedis.keys(keyPattern);
    jedis.close();
    return RedisKey.shortKeys(keys);
  }

  @Override
  public int size() {
    return keys().size();
  }

  @Override
  public void clear() {
    Jedis jedis = RedisHandler.getJedis();
    Set<String> keys = jedis.keys(keyPattern);
    if (keys != null && !keys.isEmpty()) {
      jedis.del(keys.toArray(new String[keys.size()]));
    }
    jedis.close();
  }

  @Override
  public boolean exists(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return false;
    }

    String fullKey = RedisKey.fullKey(group, key);
    Jedis jedis = RedisHandler.getJedis();
    boolean exists = jedis.exists(fullKey.getBytes());
    jedis.close();
    return exists;
  }

  @Override
  public V get(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return null;
    }

    String fullKey = RedisKey.fullKey(group, key);
    Jedis jedis = RedisHandler.getJedis();
    byte[] value = jedis.get(fullKey.getBytes());
    jedis.close();
    if (value == null) {
      return null;
    }
    return Utils.unserialize(value);
  }

  @Override
  public V put(String key, V object) {
    Preconditions.checkNotNull(key);

    Jedis jedis = RedisHandler.getJedis();
    String fullKey = RedisKey.fullKey(group, key);
    String code = jedis.set(fullKey.getBytes(), Utils.serialize(object));
    jedis.close();
    if ("OK".equalsIgnoreCase(code)) {
      return object;
    }
    return null;
  }

  @Override
  public V put(String key, V object, int timeout) {
    Preconditions.checkNotNull(key);

    Jedis jedis = RedisHandler.getJedis();
    String fullKey = RedisKey.fullKey(group, key);
    String code = jedis.setex(fullKey.getBytes(), timeout, Utils.serialize(object));
    jedis.close();
    if ("OK".equalsIgnoreCase(code)) {
      return object;
    }
    return null;
  }

  @Override
  public boolean remove(String key) {
    if (Strings.isNullOrEmpty(key)) {
      return false;
    }

    String fullKey = RedisKey.fullKey(group, key);
    Jedis jedis = RedisHandler.getJedis();
    long result = jedis.del(fullKey);
    jedis.close();
    return result > 0;
  }

  @Override
  public V lookup(Lookup<V> lookup) {
    Collection<String> keys = keys();

    Jedis jedis = RedisHandler.getJedis();
    for (String key : keys) {
      byte[] value = jedis.get(key.getBytes());
      if (value == null) {
        continue;
      }
      V obj = Utils.unserialize(value);
      if (lookup.isYou(obj)) {
        jedis.close();
        return obj;
      }
    }
    jedis.close();
    return null;
  }

  @Override
  public List<V> lookupAll(Lookup<V> lookup) {
    Collection<String> keys = keys();

    List<V> result = Lists.newArrayList();
    Jedis jedis = RedisHandler.getJedis();
    for (String key : keys) {
      byte[] value = jedis.get(key.getBytes());
      if (value == null) {
        continue;
      }
      V obj = Utils.unserialize(value);
      if (lookup.isYou(obj)) {
        result.add(obj);
      }
    }
    jedis.close();
    return result;
  }

}
