package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.cache.Cache;
import io.github.rcarlosdasilva.weixin.core.cache.impl.handler.MapHandler;
import io.github.rcarlosdasilva.weixin.core.cache.impl.handler.RedisHandler;

public class AbstractCache<V> implements Cache<V> {

  protected String mark;

  private boolean isRedis() {
    return WeixinRegistry.getConfiguration().isRedisCache();
  }

  @Override
  public Set<String> keys() {
    if (isRedis()) {
      return RedisHandler.getRedis().keys(RedisHandler.DEFAULT_REDIS_KEY_PATTERN);
    } else {
      return MapHandler.getObject(mark).keySet();
    }
  }

  @Override
  public Collection<V> values() {
    if (isRedis()) {
      Set<String> keys = keys();
      List<V> values = Lists.newArrayList();
      for (String key : keys) {
        values.add(get(key));
      }
      return values;
    } else {
      return MapHandler.<V>getObject(mark).values();
    }
  }

  @Override
  public int size() {
    if (isRedis()) {
      return keys().size();
    } else {
      return MapHandler.<V>getObject(mark).size();
    }
  }

  @Override
  public void clear() {
    if (isRedis()) {
      Set<String> keys = keys();
      for (String key : keys) {
        remove(key);
      }
    } else {
      MapHandler.<V>getObject(mark).clear();
    }
  }

  @Override
  public V get(String key) {
    if (isRedis()) {
      byte[] value = RedisHandler.getRedis().get(key.getBytes());
      if (value == null) {
        return null;
      }

      return Utils.<V>unserialize(value);
    } else {
      return MapHandler.<V>getObject(mark).get(key);
    }
  }

  @Override
  public V put(String key, V object) {
    if (isRedis()) {
      RedisHandler.getRedis().set(key.getBytes(), Utils.serialize(object));
      return object;
    } else {
      return MapHandler.<V>getObject(mark).put(key, object);
    }
  }

  @Override
  public V remove(String key) {
    if (isRedis()) {
      V object = get(key);
      if (object == null) {
        return null;
      }

      RedisHandler.getRedis().del(key.getBytes());
      return object;
    } else {
      return MapHandler.<V>getObject(mark).remove(key);
    }
  }

  @Override
  public String lookup(V value) {
    return null;
  }

}
