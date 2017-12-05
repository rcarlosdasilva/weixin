package io.github.rcarlosdasilva.weixin.core.setting;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.core.listener.AccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.JsTicketUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.OpenPlatformAccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.OpenPlatformLisensorAccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.WeixinListener;

public class Setting {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private boolean strictUseOpenPlatform = false;
  private boolean throwException = true;
  private boolean useRedisCache = false;
  private boolean useSpringRedis = false;
  private boolean autoLoadAuthorizedWeixinData = true;
  private Map<String, WeixinListener> listeners = Maps.newHashMap();
  private RedisSetting redisSetting = null;

  /**
   * 是否强制使用开放平台代理公众号的api调用.
   * 
   * @return boolean
   * @deprecated 暂不支持这项配置
   */
  @Deprecated
  public boolean isStrictUseOpenPlatform() {
    return strictUseOpenPlatform;
  }

  /**
   * 设置是否强制使用开放平台代理公众号的api调用.
   * <p>
   * 只有当设置了开放平台的信息，该配置才起作用<br>
   * 设置为强制：代码注册的公众号信息，将不会起作用，公众号能否使用，完全依赖于开放平台是否获取到授权<br>
   * 不强制：优先使用开放平台对公众号进行api调用，但如果开放平台未受到公众号的授权，
   * 并且代码中注册了公众号的信息（appid与appsecret），则尝试直接调用公众号平台的api
   * 
   * @param strictUseOpenPlatform
   *          是否强制，默认否
   * @deprecated 暂不支持这项配置
   */
  @Deprecated
  public void setStrictUseOpenPlatform(boolean strictUseOpenPlatform) {
    this.strictUseOpenPlatform = strictUseOpenPlatform;
  }

  public boolean isThrowException() {
    return throwException;
  }

  /**
   * 当调用api接口出错时，是否抛出异常.
   * 
   * @param throwException
   *          boolean
   */
  public void setThrowException(boolean throwException) {
    this.throwException = throwException;
  }

  public boolean isUseRedisCache() {
    return useRedisCache;
  }

  /**
   * 是否使用Redis做缓存.
   * 
   * @param useRedisCache
   *          boolean
   */
  public void setUseRedisCache(boolean useRedisCache) {
    this.useRedisCache = useRedisCache;
  }

  public boolean isUseSpringRedis() {
    return useSpringRedis;
  }

  /**
   * 是否使用Spring中配置的Redis做缓存.
   * 
   * @param useSpringRedis
   *          boolean
   */
  public void setUseSpringRedis(boolean useSpringRedis) {
    this.useSpringRedis = useSpringRedis;
  }

  public boolean isAutoLoadAuthorizedWeixinData() {
    return autoLoadAuthorizedWeixinData;
  }

  /**
   * （开放平台第三方平台下）是否在公众号管理员授权后，自动加载公众号的授权内容与公众号基本信息
   * 
   * @param autoLoadAuthorizedWeixinData
   *          boolean
   */
  public void setAutoLoadAuthorizedWeixinData(boolean autoLoadAuthorizedWeixinData) {
    this.autoLoadAuthorizedWeixinData = autoLoadAuthorizedWeixinData;
  }

  public void addListener(WeixinListener listener) {
    Preconditions.checkNotNull(listener);

    if (listener instanceof AccessTokenUpdatedListener) {
      listeners.put(AccessTokenUpdatedListener.class.getName(), listener);
    } else if (listener instanceof JsTicketUpdatedListener) {
      listeners.put(JsTicketUpdatedListener.class.getName(), listener);
    } else if (listener instanceof OpenPlatformAccessTokenUpdatedListener) {
      listeners.put(OpenPlatformAccessTokenUpdatedListener.class.getName(), listener);
    } else if (listener instanceof OpenPlatformLisensorAccessTokenUpdatedListener) {
      listeners.put(OpenPlatformLisensorAccessTokenUpdatedListener.class.getName(), listener);
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends WeixinListener> T getListener(Class<T> classType) {
    try {
      return (T) listeners.get(classType.getName());
    } catch (Exception ex) {
      logger.error("weixin config listener", ex);
      return null;
    }
  }

  public RedisSetting getRedisSetting() {
    return redisSetting;
  }

  /**
   * 设置Redis缓存服务器的配置（当使用Spring的Redis时，无需设置）.
   * 
   * @param redisSetting
   *          config
   */
  public void setRedisSetting(RedisSetting redisSetting) {
    this.useRedisCache = true;
    this.useSpringRedis = false;
    this.redisSetting = redisSetting;
  }

}
