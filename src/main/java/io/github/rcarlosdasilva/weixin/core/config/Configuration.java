package io.github.rcarlosdasilva.weixin.core.config;

public class Configuration {

  private boolean redisCache;

  public boolean isRedisCache() {
    return redisCache;
  }

  public void setRedisCache(boolean redisCache) {
    this.redisCache = redisCache;
  }

  private RedisConfiguration redisConfiguration;

  public RedisConfiguration getRedisConfiguration() {
    return redisConfiguration;
  }

  public void setRedisConfiguration(RedisConfiguration redisConfiguration) {
    this.redisConfiguration = redisConfiguration;
  }

}
