package io.github.rcarlosdasilva.weixin.core.cache.storage;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
import redis.clients.jedis.Transaction;

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
      if (lookup.isYou(key, obj)) {
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
      if (lookup.isYou(key, obj)) {
        result.add(obj);
      }
    }
    jedis.close();
    return result;
  }

  @Override
  public String lock(String key, long timeout, boolean noWait) {
    Preconditions.checkNotNull(key);
    Preconditions.checkArgument(timeout > 0);

    String fullKey = RedisKey.fullKey(group, key) + LOCKER_NAME_SUFFIX;
    String identifier = UUID.randomUUID().toString();
    long lastTtl = -1;
    Jedis jedis = RedisHandler.getJedis();

    while (true) {
      if (jedis.setnx(fullKey, identifier) == 1) {
        jedis.pexpire(fullKey, timeout);
        // 正常获取锁
        break;
      }

      long ttl = jedis.pttl(fullKey);
      if (ttl == -1) {
        // 没设置过期时间
        jedis.pexpire(fullKey, timeout);
      } else if (ttl == -2) {
        // 神奇，万一setnx之后到这里key过期了呢
        continue;
      } else {
        // 每次获取ttl肯定是越来越小，如果突然变大了，只能说明，有另一个贱人比你提前获取到锁，那就不惜当等了
        if (lastTtl > 0 && ttl > lastTtl) {
          jedis.close();
          return null;
        }
        lastTtl = ttl;
      }

      if (noWait) {
        jedis.close();
        return null;
      }

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    jedis.close();
    return identifier;
  }

  @Override
  public boolean unlock(String key, String identifier) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(identifier);

    String fullKey = RedisKey.fullKey(group, key) + LOCKER_NAME_SUFFIX;
    Jedis jedis = RedisHandler.getJedis();

    while (true) {
      // 监视lock，准备开始事务
      jedis.watch(fullKey);
      // 看看是不是自己的锁
      if (identifier.equals(jedis.get(fullKey))) {
        Transaction transaction = jedis.multi();
        transaction.del(fullKey);
        List<Object> results = transaction.exec();
        if (results == null) {
          continue;
        }
        jedis.close();
        return true;
      }
      jedis.unwatch();
      break;
    }

    jedis.close();
    return false;
  }

}
