package io.github.rcarlosdasilva.weixin.core.cache;

import java.lang.reflect.Field;
import java.util.Map;

import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.cache.storage.JdkMapStorage;
import io.github.rcarlosdasilva.weixin.core.cache.storage.SimpleRedisStorage;
import io.github.rcarlosdasilva.weixin.core.cache.storage.SpringRedisStorage;
import io.github.rcarlosdasilva.weixin.core.cache.storage.redis.RedisHandler;

public class CacheHandler {

  public static final String CACHEABLE_CLASS_GROUP_MARK_FIELD_NAME = "GROUP_NAME";
  private static final Map<Class<? extends Cacheable>, CacheStorage<? extends Cacheable>> STORAGES = Maps
      .newHashMap();

  private CacheHandler() {
    throw new IllegalStateException("CacheHandler class");
  }

  public static <V extends Cacheable> CacheStorage<V> of(Class<V> clazz) {
    @SuppressWarnings("unchecked")
    CacheStorage<V> storage = (CacheStorage<V>) STORAGES.get(clazz);

    if (storage == null) {
      synchronized (STORAGES) {
        storage = newStorage(clazz);
        STORAGES.put(clazz, storage);
      }
    }

    return storage;
  }

  private static <V extends Cacheable> CacheStorage<V> newStorage(Class<V> clazz) {
    String group = groupName(clazz);
    CacheType cacheType = Registry.setting().getCacheType();
    if (cacheType == CacheType.SMART) {
      cacheType = smartCacheType();
    }
    switch (cacheType) {
      case SPRING_REDIS:
        return new SpringRedisStorage<>(group);
      case SIMPLE_REDIS:
        return new SimpleRedisStorage<>(group);
      case JDK_MAP:
        return new JdkMapStorage<>();
      default:
        return null;
    }
  }

  private static CacheType smartCacheType() {
    if (RedisHandler.getRedisTemplate() != null) {
      return CacheType.SPRING_REDIS;
    }

    try {
      Class.forName("redis.clients.jedis.Jedis");
      return CacheType.SIMPLE_REDIS;
    } catch (Exception ex) {
      return CacheType.SIMPLE_REDIS;
    }
  }

  private static String groupName(Class<?> clazz) {
    try {
      Field field = clazz.getField(CACHEABLE_CLASS_GROUP_MARK_FIELD_NAME);
      return field.get(clazz).toString();
    } catch (Exception ex) {
      String clazzName = clazz.getSimpleName();
      int length = clazzName.length();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < length; i++) {
        char c = clazzName.charAt(i);
        if (c >= 'a' && c <= 'z') {
          sb.append(c);
        } else if (c >= 'A' && c <= 'Z') {
          if (i > 0) {
            sb.append('_');
          }
          c += 32;
          sb.append(c);
        }
      }
      return sb.toString();
    }
  }

}
