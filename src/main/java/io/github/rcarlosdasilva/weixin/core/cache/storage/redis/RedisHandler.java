package io.github.rcarlosdasilva.weixin.core.cache.storage.redis;

import org.springframework.data.redis.core.RedisTemplate;

import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.exception.RedisCacheNotInitializeException;
import io.github.rcarlosdasilva.weixin.core.setting.RedisSetting;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SuppressWarnings("rawtypes")
public class RedisHandler {

  private static RedisTemplate redisTemplate = null;
  private static JedisPool pool = null;

  private RedisHandler() {
    throw new IllegalStateException("RedisHandler class");
  }

  public static RedisTemplate getRedisTemplate() {
    return redisTemplate;
  }

  public static void setRedisTemplate(RedisTemplate redisTemplate) {
    RedisHandler.redisTemplate = redisTemplate;
  }

  private static synchronized void initSimpleRedis(RedisSetting redisSetting) {
    pool = new JedisPool(redisSetting.getConfig(), redisSetting.getHost(), redisSetting.getPort(),
        redisSetting.getTimeout(), redisSetting.getPassword(), redisSetting.getDatabase(),
        redisSetting.isUseSsl());
  }

  public static synchronized Jedis getJedis() {
    if (pool == null) {
      RedisSetting redisSetting = Registry.setting().getRedisSetting();
      if (redisSetting == null) {
        throw new RedisCacheNotInitializeException("Simple Redis缓存未配置");
      }
      initSimpleRedis(redisSetting);
    }

    return pool.getResource();
  }

}
