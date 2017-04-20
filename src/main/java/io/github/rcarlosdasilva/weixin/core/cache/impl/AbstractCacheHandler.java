package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Set;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.MapHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.RedisTemplateHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.SimpleRedisHandler;
import redis.clients.jedis.Jedis;

public class AbstractCacheHandler<V> implements CacheHandler<V> {

  protected String mark;

  private boolean isSimpleRedis() {
    return WeixinRegistry.getConfiguration().isUseRedisCache()
        && !WeixinRegistry.getConfiguration().isUseSpringRedis();
  }

  private boolean isSpringRedis() {
    return WeixinRegistry.getConfiguration().isUseSpringRedis();
  }

  private static String key(final String module, final String resource) {
    return new StringBuilder(Convention.DEFAULT_REDIS_KEY_PREFIX)
        .append(Convention.DEFAULT_REDIS_KEY_SEPARATOR).append(module)
        .append(Convention.DEFAULT_REDIS_KEY_SEPARATOR).append(resource).toString();
  }

  private String realRedisKey(final String key) {
    return key(mark, key);
  }

  private String realRedisKeyPattern() {
    return realRedisKey(Convention.DEFAULT_REDIS_KEY_PATTERN);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Set<String> keys() {
    if (isSimpleRedis()) {
      return SimpleRedisHandler.getRedis().keys(new String(realRedisKeyPattern()));
    } else if (isSpringRedis()) {
      return RedisTemplateHandler.redisTemplate.keys(realRedisKeyPattern());
    } else {
      return MapHandler.getObject(mark).keySet();
    }
  }

  @Override
  public int size() {
    if (isSimpleRedis() || isSpringRedis()) {
      return keys().size();
    } else {
      return MapHandler.<V>getObject(mark).size();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void clear() {
    if (isSimpleRedis()) {
      Set<String> keys = keys();
      for (String key : keys) {
        remove(key);
      }
    } else if (isSpringRedis()) {
      RedisTemplateHandler.redisTemplate.delete(keys());
    } else {
      MapHandler.<V>getObject(mark).clear();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public V get(final String key) {
    if (isSimpleRedis()) {
      Jedis jedis = SimpleRedisHandler.getRedis();
      byte[] value = jedis.get(realRedisKey(key).getBytes());
      jedis.close();
      if (value == null) {
        return null;
      }
      return Utils.<V>unserialize(value);
    } else if (isSpringRedis()) {
      return (V) RedisTemplateHandler.redisTemplate.opsForValue().get(realRedisKey(key));
    } else {
      return MapHandler.<V>getObject(mark).get(key);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public V put(final String key, final V object) {
    if (isSimpleRedis()) {
      Jedis jedis = SimpleRedisHandler.getRedis();
      jedis.set(realRedisKey(key).getBytes(), Utils.serialize(object));
      jedis.close();
    } else if (isSpringRedis()) {
      RedisTemplateHandler.redisTemplate.opsForValue().set(realRedisKey(key), object);
    } else {
      MapHandler.<V>getObject(mark).put(key, object);
    }
    return object;
  }

  @SuppressWarnings("unchecked")
  @Override
  public V remove(final String key) {
    if (isSimpleRedis()) {
      V object = get(key);
      if (object == null) {
        return null;
      }

      Jedis jedis = SimpleRedisHandler.getRedis();
      jedis.del(realRedisKey(key));
      jedis.close();
      return object;
    } else if (isSpringRedis()) {
      V object = get(key);
      if (object == null) {
        return null;
      }

      RedisTemplateHandler.redisTemplate.delete(realRedisKey(key));
      return object;
    } else {
      return MapHandler.<V>getObject(mark).remove(key);
    }
  }

  @Override
  public String lookup(final V value) {
    return null;
  }

}
