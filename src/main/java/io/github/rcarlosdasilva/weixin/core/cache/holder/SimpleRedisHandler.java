package io.github.rcarlosdasilva.weixin.core.cache.holder;

import io.github.rcarlosdasilva.weixin.core.exception.RedisCacheNotInitializeException;
import io.github.rcarlosdasilva.weixin.core.registry.RedisConfiguration;
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

  public static synchronized void init(RedisConfiguration redisConfiguration) {
    if (pool == null) {
      pool = new JedisPool(redisConfiguration.getConfig(), redisConfiguration.getHost(),
          redisConfiguration.getPort(), redisConfiguration.getTimeout(),
          redisConfiguration.getPassword(), redisConfiguration.getDatabase(),
          redisConfiguration.isUseSsl());
    }
  }

  public static Jedis getRedis() {
    if (pool == null) {
      throw new RedisCacheNotInitializeException("Redis缓存未配置");
    }

    return pool.getResource();
  }

}
