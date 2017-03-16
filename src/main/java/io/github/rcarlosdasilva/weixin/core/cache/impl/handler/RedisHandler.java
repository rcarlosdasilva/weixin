package io.github.rcarlosdasilva.weixin.core.cache.impl.handler;

import io.github.rcarlosdasilva.weixin.core.config.RedisConfiguration;
import io.github.rcarlosdasilva.weixin.core.exception.RedisCacheNotInitializeException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 要使用Redis缓存，需要先初始化RedisHandler
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class RedisHandler {

  public static final String DEFAULT_REDIS_KEY_PREFIX = "RWX_";
  public static final String DEFAULT_REDIS_KEY_PATTERN = "*";
  private static JedisPool pool = null;

  public static void init(RedisConfiguration redisConfiguration) {
    pool = new JedisPool(redisConfiguration.getConfig(), redisConfiguration.getHost(),
        redisConfiguration.getPort(), redisConfiguration.getTimeout(),
        redisConfiguration.getPassword(), redisConfiguration.getDatabase(),
        redisConfiguration.isUseSsl());
  }

  public static Jedis getRedis() {
    if (pool == null) {
      throw new RedisCacheNotInitializeException("Redis缓存未配置");
    }

    return pool.getResource();
  }

}
