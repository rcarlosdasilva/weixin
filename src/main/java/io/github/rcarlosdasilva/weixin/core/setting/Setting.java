package io.github.rcarlosdasilva.weixin.core.setting;

import io.github.rcarlosdasilva.weixin.core.cache.CacheType;
import io.github.rcarlosdasilva.weixin.core.inspect.InspectDispatcher;

public class Setting {

  private static final long DEFAULT_INSPECT_DISPATCH_INTERVAL_TIME = 10000;

  private int retries = 2;
  private boolean strictUseOpenPlatform = false;
  private boolean throwException = true;
  private CacheType cacheType = CacheType.SMART;
  private boolean autoLoadAuthorizedWeixinData = true;
  private long inspectDispatchIntervalInMill = DEFAULT_INSPECT_DISPATCH_INTERVAL_TIME;
  private RedisSetting redisSetting = null;

  /**
   * 接口请求失败后的重试次数.
   * 
   * @param retryTimes
   *          次数
   */
  public int getRetries() {
    return retries;
  }

  public void setRetries(int retries) {
    this.retries = retries;
  }

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

  public CacheType getCacheType() {
    return cacheType;
  }

  /**
   * 缓存方式
   * 
   * @param cacheType
   *          type
   */
  public void setCacheType(CacheType cacheType) {
    this.cacheType = cacheType;
  }

  public boolean isAutoLoadAuthorizedWeixinData() {
    return autoLoadAuthorizedWeixinData;
  }

  /**
   * （开放平台第三方平台下）是否在公众号管理员授权后，自动加载公众号的授权内容与公众号基本信息.
   * <p>
   * <b>建议开启</b>
   * 
   * @param autoLoadAuthorizedWeixinData
   *          boolean
   */
  public void setAutoLoadAuthorizedWeixinData(boolean autoLoadAuthorizedWeixinData) {
    this.autoLoadAuthorizedWeixinData = autoLoadAuthorizedWeixinData;
  }

  public long getInspectDispatchIntervalInMill() {
    return inspectDispatchIntervalInMill;
  }

  /**
   * 设置轮询access_token是否过期（重新获取）的间隔时间，默认10秒.
   * <p>
   * <b>注意：需要先设置，然后再调用 {@link InspectDispatcher#startup()}开启</b>
   * 
   * @param inspectDispatchIntervalInMill
   *          单位毫秒
   */
  public void setInspectDispatchIntervalInMill(long inspectDispatchIntervalInMill) {
    this.inspectDispatchIntervalInMill = inspectDispatchIntervalInMill;
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
    this.cacheType = CacheType.SIMPLE_REDIS;
    this.redisSetting = redisSetting;
  }

}
