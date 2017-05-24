package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.MapHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.RedisTemplateHandler;
import io.github.rcarlosdasilva.weixin.core.cache.holder.SimpleRedisHandler;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import redis.clients.jedis.Jedis;

public class AbstractCacheHandler<V> implements CacheHandler<V> {

  private static final String REDIS_KEY_PREFIX = Convention.DEFAULT_REDIS_KEY_PREFIX
      + Convention.DEFAULT_REDIS_KEY_SEPARATOR;
  private static final Splitter SPLITTER = Splitter.on(Convention.DEFAULT_REDIS_KEY_SEPARATOR);

  protected String mark;

  private boolean isSimpleRedis() {
    return Registration.getInstance().getConfiguration().isUseRedisCache()
        && !Registration.getInstance().getConfiguration().isUseSpringRedis();
  }

  private boolean isSpringRedis() {
    return Registration.getInstance().getConfiguration().isUseSpringRedis();
  }

  private static String key(final String module, final String resource) {
    return new StringBuilder(REDIS_KEY_PREFIX).append(module)
        .append(Convention.DEFAULT_REDIS_KEY_SEPARATOR).append(resource).toString();
  }

  private String realRedisKey(final String key) {
    return key(mark, key);
  }

  private String realRedisKeyPattern() {
    return realRedisKey(Convention.DEFAULT_REDIS_KEY_PATTERN);
  }

  private String realKey(final String redisKey) {
    if (!Strings.isNullOrEmpty(redisKey)) {
      List<String> parts = SPLITTER.splitToList(redisKey);
      if (parts != null && parts.size() > 0) {
        return parts.get(parts.size() - 1);
      }
    }
    return "";
  }

  private Collection<String> realKeys(final Set<String> redisKeys) {
    if (redisKeys == null) {
      return null;
    }

    return Collections2.transform(redisKeys, new Function<String, String>() {

      @Override
      public String apply(String input) {
        return realKey(input);
      }
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<String> keys() {
    if (isSimpleRedis()) {
      return realKeys(SimpleRedisHandler.getRedis().keys(new String(realRedisKeyPattern())));
    } else if (isSpringRedis()) {
      return realKeys(RedisTemplateHandler.redisTemplate.keys(realRedisKeyPattern()));
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
      Collection<String> keys = keys();
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
      try {
        return MapHandler.<V>getObject(mark).get(key);
      } catch (Exception e) {
        return null;
      }
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
      try {
        MapHandler.<V>getObject(mark).put(key, object);
      } catch (Exception e) {
        return null;
      }
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
      try {
        return MapHandler.<V>getObject(mark).remove(key);
      } catch (Exception e) {
        return null;
      }
    }
  }

  @Override
  public String lookup(final V value) {
    return null;
  }

}
