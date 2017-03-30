package io.github.rcarlosdasilva.weixin.core.config;

public class Configuration {

  public static final Configuration DEFAULT_CONFIG = new Configuration();

  private boolean throwException = true;
  private boolean useRedisCache = false;
  private RedisConfiguration redisConfiguration = null;

  public boolean isThrowException() {
    return throwException;
  }

  public void setThrowException(boolean throwException) {
    this.throwException = throwException;
  }

  public boolean isUseRedisCache() {
    return useRedisCache;
  }

  public RedisConfiguration useRedisCache() {
    this.useRedisCache = true;
    this.redisConfiguration = new RedisConfiguration();
    return redisConfiguration;
  }

  public RedisConfiguration getRedisConfiguration() {
    return redisConfiguration;
  }

  public void setRedisConfiguration(RedisConfiguration redisConfiguration) {
    this.redisConfiguration = redisConfiguration;
  }

}
