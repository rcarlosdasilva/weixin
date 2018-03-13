package io.github.rcarlosdasilva.weixin.core.cache.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.CacheStorage;
import io.github.rcarlosdasilva.weixin.core.cache.Cacheable;
import io.github.rcarlosdasilva.weixin.core.cache.Lookup;
import io.github.rcarlosdasilva.weixin.core.cache.storage.redis.RedisHandler;
import io.github.rcarlosdasilva.weixin.core.cache.storage.redis.RedisKey;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

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
    return new ArrayList<>(keys);
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
      if (lookup.isYou(key, (V) obj)) {
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
      if (lookup.isYou(key, (V) obj)) {
        result.add((V) obj);
      }
    }
    return result;
  }

  @Override
  public String lock(String key, long timeout, boolean noWait) {
    Preconditions.checkNotNull(key);
    Preconditions.checkArgument(timeout > 0);

    String fullKey = RedisKey.fullKey(group, key) + LOCKER_NAME_SUFFIX;
    String identifier = UUID.randomUUID().toString();
    long lastTtl = -1;

    while (true) {
      if (RedisHandler.getRedisTemplate().opsForValue().setIfAbsent(fullKey, identifier)) {
        RedisHandler.getRedisTemplate().expire(fullKey, timeout, TimeUnit.MILLISECONDS);
        // 正常获取锁
        break;
      }

      long ttl = RedisHandler.getRedisTemplate().getExpire(fullKey, TimeUnit.MILLISECONDS);
      if (ttl == -1) {
        // 没设置过期时间
        RedisHandler.getRedisTemplate().expire(fullKey, timeout, TimeUnit.MILLISECONDS);
      } else if (ttl == -2) {
        // 神奇，万一setnx之后到这里key过期了呢
        continue;
      } else {
        // 每次获取ttl肯定是越来越小，如果突然变大了，只能说明，有另一个贱人比你提前获取到锁，那就不惜当等了
        if (lastTtl > 0 && ttl > lastTtl) {
          return null;
        }
        lastTtl = ttl;
      }

      if (noWait) {
        return null;
      }

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    return identifier;
  }

  @Override
  public boolean unlock(String key, String identifier) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(identifier);

    final String fullKey = RedisKey.fullKey(group, key) + LOCKER_NAME_SUFFIX;

    while (true) {
      // 监视lock，准备开始事务
      RedisHandler.getRedisTemplate().watch(fullKey);
      // 看看是不是自己的锁
      Object obj = RedisHandler.getRedisTemplate().opsForValue().get(fullKey);
      if (obj != null && identifier.equals(obj)) {
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
          @Override
          public Object execute(RedisOperations operations) throws DataAccessException {
            operations.multi();
            operations.delete(fullKey);
            return operations.exec();
          }
        };
        Object results = RedisHandler.getRedisTemplate().execute(sessionCallback);

        if (results == null) {
          continue;
        }
        return true;
      }
      RedisHandler.getRedisTemplate().unwatch();
      break;
    }

    return false;
  }

}
