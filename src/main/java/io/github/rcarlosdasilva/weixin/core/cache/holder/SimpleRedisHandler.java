package io.github.rcarlosdasilva.weixin.core.cache.holder;

import io.github.rcarlosdasilva.weixin.core.exception.RedisCacheNotInitializeException;
import io.github.rcarlosdasilva.weixin.core.registry.RedisSetting;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 要使用Redis缓存，需要先初始化RedisHandler
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class SimpleRedisHandler {

  public static final String DEFAULT_REDIS_KEY_PREFIX = "RWX_";
  public static final String DEFAULT_REDIS_KEY_PATTERN = "*";
  private static JedisPool pool = null;

  private SimpleRedisHandler() {
    throw new IllegalStateException("SimpleRedisHandler class");
  }

  public static synchronized void init(RedisSetting redisSetting) {
    if (pool == null) {
      pool = new JedisPool(redisSetting.getConfig(), redisSetting.getHost(), redisSetting.getPort(),
          redisSetting.getTimeout(), redisSetting.getPassword(), redisSetting.getDatabase(),
          redisSetting.isUseSsl());
    }
  }

  public static synchronized Jedis getRedis() {
    if (pool == null) {
      throw new RedisCacheNotInitializeException("Redis缓存未配置");
    }

    return pool.getResource();
  }

  public static synchronized void returnRedis(Jedis jedis) {
    if (jedis != null && pool != null) {
      jedis.close();
    }
  }

}
