package io.github.rcarlosdasilva.weixin.core;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.Lookup;
import io.github.rcarlosdasilva.weixin.core.exception.InvalidAccountException;
import io.github.rcarlosdasilva.weixin.core.listener.AccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.JsTicketUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.OpenPlatformAccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.OpenPlatformLisensorAccessTokenUpdatedListener;
import io.github.rcarlosdasilva.weixin.core.listener.WeixinListener;
import io.github.rcarlosdasilva.weixin.core.setting.Setting;
import io.github.rcarlosdasilva.weixin.model.AccessToken;
import io.github.rcarlosdasilva.weixin.model.OpAccount;
import io.github.rcarlosdasilva.weixin.model.WeixinAccount;

/**
 * 微信公众号（开放平台三方平台）注册器，支持多公众号
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class Registry {

  private static RegistryHandler registryHandler = new RegistryHandler();

  private Registry() {
    throw new IllegalStateException("Registry class");
  }

  public static RegistryHandler handler() {
    return registryHandler;
  }

  /**
   * 设置全局配置.
   * 
   * @param setting
   *          {@link Setting}
   */
  public static void withSetting(Setting setting) {
    Preconditions.checkNotNull(setting);

    registryHandler.setSetting(setting);
  }

  public static Setting setting() {
    return registryHandler.getSetting();
  }

  /**
   * 注册开放平台信息.
   * <p>
   * 注册后，使用{@link #weixin(WeixinAccount)}方法注册的公众号信息：
   * <ol>
   * <li>{@link WeixinAccount#isWithOpenPlatform()}为true，并且
   * {@link WeixinAccount#getRefreshToken()}有值，则该公众号将由开放平台代理调用API
   * <li>否则， {@link WeixinAccount}必须提供appid与appsecret，调用API时，将只使用公众平台
   * </ol>
   * 
   * @param componentAppId
   *          componentAppId
   * @param componentAppSecret
   *          componentAppSecret
   * @param token
   *          加密token
   * @param aesKey
   *          加密aesKey
   */
  public static void openPlatform(String componentAppId, String componentAppSecret, String token,
      String aesKey) {
    registryHandler.setOpAccount(new OpAccount(componentAppId, componentAppSecret, token, aesKey));
  }

  public static OpAccount openPlatform() {
    return registryHandler.getOpAccount();
  }

  /**
   * 注册一个公众号配置.
   * <p>
   * <b>建议尽量使用这个方法注册</b><br>
   * <b>建议使用开放平台授权方式管理公众号</b>.
   * 
   * <p>
   * <b>注意：在{@link WeixinAccount}中指明withOpenPlatform变量，如果为true（默认）代表公众号是通过开放平台授权过来（必须包含authorizer_refresh_token），
   * 否则会直接使用appid和appsecret（可兼容使用）管理公众号API调用凭证</b>
   * 
   * <p>
   * 当key未指定时，将使用默认key，调用API时使用 {@link Weixin#unique()}获取API入口
   * 
   * @param account
   *          公众号信息
   */
  public static void checkin(WeixinAccount account) {
    registryHandler.add(account);
  }

  public static void update(WeixinAccount account) {
    registryHandler.set(account);
  }

  /**
   * 注销公众号信息.
   * 
   * @param key
   *          注册时的key，或是公众号appid
   */
  public static void remove(String key) {
    registryHandler.del(key);
  }

  /**
   * 通过appId或原始ID获取账号配置信息.
   * 
   * @param key
   *          可以为公众号appId或者公众号原始ID，也可以是注册时的key
   * @return {@link WeixinAccount}
   */
  public static WeixinAccount lookup(String key) {
    return registryHandler.get(key);
  }

  /**
   * 通过appId或原始ID查看公众号信息是否存在.
   * 
   * @param key
   *          可以为公众号appId或者公众号原始ID，也可以是注册时的key
   * @return boolean
   */
  public static boolean exists(String key) {
    return registryHandler.get(key) != null;
  }

  /**
   * 添加监听器
   */
  public static void listener(WeixinListener listener) {
    registryHandler.addListener(listener);
  }

  public static <T extends WeixinListener> T listener(Class<T> classType) {
    return registryHandler.getListener(classType);
  }

  /**
   * 注册信息处理器
   * 
   * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
   */
  public static class RegistryHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Setting setting = new Setting();
    private OpAccount opAccount;
    private Map<String, WeixinListener> listeners = Maps.newHashMap();

    RegistryHandler() {
    }

    Setting getSetting() {
      return setting;
    }

    void setSetting(Setting setting) {
      this.setting = setting;
    }

    OpAccount getOpAccount() {
      return opAccount;
    }

    void setOpAccount(OpAccount opAccount) {
      this.opAccount = opAccount;
    }

    void addListener(WeixinListener listener) {
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
    <T extends WeixinListener> T getListener(Class<T> classType) {
      try {
        return (T) listeners.get(classType.getName());
      } catch (Exception ex) {
        logger.error("weixin config listener", ex);
        return null;
      }
    }

    void add(WeixinAccount account) {
      if (get(account.getKey()) != null) {
        logger.warn("尝试添加一个已经存在的公众号信息：[{}]，本方法无法覆盖已有公众号信息，请使用update方法", account.getKey());
        return;
      }

      if (verify(account)) {
        if (Strings.isNullOrEmpty(account.getKey())) {
          account.setKey(Convention.DEFAULT_UNIQUE_WEIXIN_KEY);
        }
        CacheHandler.of(WeixinAccount.class).put(account.getKey(), account);
        logger.info("注册公众号：[KEY: {}, APPID: {}]", account.getKey(), account.getAppId());
      }
    }

    void set(WeixinAccount account) {
      if (verify(account)) {
        del(account.getKey());
        CacheHandler.of(WeixinAccount.class).put(account.getKey(), account);
      }
    }

    void del(String key) {
      if (Strings.isNullOrEmpty(key)) {
        key = Convention.DEFAULT_UNIQUE_WEIXIN_KEY;
      }

      final WeixinAccount account = get(key);
      if (account != null) {
        final String realKey = account.getKey();
        CacheHandler.of(WeixinAccount.class).remove(realKey);
        CacheHandler.of(AccessToken.class).remove(realKey);
        logger.info("已注销公众号信息：[{}]", key);
      } else {
        logger.warn("未找到可取消注册的公众号信息：[{}]", key);
      }
    }

    private boolean verify(WeixinAccount account) {
      if (account == null || Strings.isNullOrEmpty(account.getAppId())) {
        throw new InvalidAccountException();
      }

      if (!account.isWithOpenPlatform() && Strings.isNullOrEmpty(account.getAppSecret())) {
        logger.warn("未找到公众号的app_scret，该公众号将不被注册");
        return false;
      }
      if (account.isWithOpenPlatform() && opAccount == null) {
        logger.warn("未找到开放平台信息，但该公众号配置为使用开放平台，将不被注册");
        return false;
      }
      if (account.isWithOpenPlatform() && Strings.isNullOrEmpty(account.getRefreshToken())) {
        logger.warn("未找到开放平台授权方的刷新令牌authorizer_refresh_token，该公众号将不被注册");
        return false;
      }

      if (Strings.isNullOrEmpty(account.getMpId())) {
        logger.warn("未设置Account.mpId，对公众号appid: [{}]来说，当微信通知回调或其他操作的时候，不设置可能会导致无法正确找到对应的公众号信息",
            account.getAppId());
      }
      if (Strings.isNullOrEmpty(account.getKey())) {
        logger.warn("未设置Account.key，将使用公众号的appid：[{}]作为默认key", account.getAppId());
        account.withKey(account.getAppId());
      }

      return true;
    }

    WeixinAccount get(final String key) {
      if (Strings.isNullOrEmpty(key)) {
        return null;
      }

      WeixinAccount account = CacheHandler.of(WeixinAccount.class).get(key);
      if (account != null) {
        return account;
      }

      final String shrotKey = key;
      return CacheHandler.of(WeixinAccount.class).lookup(new Lookup<WeixinAccount>() {

        @Override
        public boolean isYou(String key, WeixinAccount obj) {
          return shrotKey.equals(obj.getAppId()) || shrotKey.equals(obj.getMpId());
        }
      });
    }

  }

}
