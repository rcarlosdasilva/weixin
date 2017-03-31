package io.github.rcarlosdasilva.weixin.core.config;

public class Configuration {

  public static final Configuration DEFAULT_CONFIG = new Configuration();

  private boolean throwException = true;
  private boolean useRedisCache = false;
  private boolean useSpringRedis = false;
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

  public void setUseRedisCache(boolean useRedisCache) {
    this.useRedisCache = useRedisCache;
  }

  public boolean isUseSpringRedis() {
    return useSpringRedis;
  }

  public void setUseSpringRedis(boolean useSpringRedis) {
    this.useSpringRedis = useSpringRedis;
  }

  public RedisConfiguration getRedisConfiguration() {
    return redisConfiguration;
  }

  public void setRedisConfiguration(RedisConfiguration redisConfiguration) {
    this.useRedisCache = true;
    this.useSpringRedis = false;
    this.redisConfiguration = redisConfiguration;
  }

}
