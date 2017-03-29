package io.github.rcarlosdasilva.weixin.core.config;

public class Configuration {

  private boolean throwException = true;
  private boolean redisCache;
  private RedisConfiguration redisConfiguration;

  public boolean isThrowException() {
    return throwException;
  }

  public void setThrowException(boolean throwException) {
    this.throwException = throwException;
  }

  public boolean isRedisCache() {
    return redisCache;
  }

  public void setRedisCache(boolean redisCache) {
    this.redisCache = redisCache;
  }

  public RedisConfiguration getRedisConfiguration() {
    return redisConfiguration;
  }

  public void setRedisConfiguration(RedisConfiguration redisConfiguration) {
    this.redisConfiguration = redisConfiguration;
  }

}
