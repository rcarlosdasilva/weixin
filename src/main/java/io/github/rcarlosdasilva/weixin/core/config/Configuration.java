package io.github.rcarlosdasilva.weixin.core.config;

import io.github.rcarlosdasilva.weixin.core.listener.AccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.JsTicketUpdatedListener;

public class Configuration {

  public static final Configuration DEFAULT_CONFIG = new Configuration();

  private boolean throwException = true;
  private boolean useRedisCache = false;
  private boolean useSpringRedis = false;
  private AccessTokenUpdatedListener accessTokenUpdatListener = null;
  private JsTicketUpdatedListener jsTicketUpdatedListener = null;
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

  public AccessTokenUpdatedListener getAccessTokenUpdatListener() {
    return accessTokenUpdatListener;
  }

  public void setAccessTokenUpdatListener(AccessTokenUpdatedListener accessTokenUpdatListener) {
    this.accessTokenUpdatListener = accessTokenUpdatListener;
  }

  public JsTicketUpdatedListener getJsTicketUpdatedListener() {
    return jsTicketUpdatedListener;
  }

  public void setJsTicketUpdatedListener(JsTicketUpdatedListener jsTicketUpdatedListener) {
    this.jsTicketUpdatedListener = jsTicketUpdatedListener;
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
