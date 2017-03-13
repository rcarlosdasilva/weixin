package io.github.rcarlosdasilva.weixin.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.rcarlosdasilva.weixin.api.internal.CertificateApi;
import io.github.rcarlosdasilva.weixin.api.internal.CommonApi;
import io.github.rcarlosdasilva.weixin.api.internal.CustomApi;
import io.github.rcarlosdasilva.weixin.api.internal.HelperApi;
import io.github.rcarlosdasilva.weixin.api.internal.MediaApi;
import io.github.rcarlosdasilva.weixin.api.internal.MenuApi;
import io.github.rcarlosdasilva.weixin.api.internal.MessageApi;
import io.github.rcarlosdasilva.weixin.api.internal.StatisticsApi;
import io.github.rcarlosdasilva.weixin.api.internal.TemplateApi;
import io.github.rcarlosdasilva.weixin.api.internal.UserApi;
import io.github.rcarlosdasilva.weixin.api.internal.UserGroupApi;
import io.github.rcarlosdasilva.weixin.api.internal.UserTagApi;
import io.github.rcarlosdasilva.weixin.api.internal.impl.CertificateApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.CommonApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.CustomApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.HelperApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.MediaApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.MenuApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.MessageApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.StatisticsApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.TemplateApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.UserApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.UserGroupApiImpl;
import io.github.rcarlosdasilva.weixin.api.internal.impl.UserTagApiImpl;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCache;
import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 微信API使用统一入口.
 * 
 * <p>
 * 调用接口前需使用 {@code WeixinRegistry.registry("key", ...)} 注册公众号信息。<br>
 * 示例：<br>
 * 使用认证相关API： {@code Weixin.with(key).certificate()}<br>
 * 使用公共API： {@code Weixin.with(key).common()}<br>
 * 使用用户组相关API： {@code Weixin.with(key).group()}<br>
 * 使用用户相关API： {@code Weixin.with(key).user()}<br>
 * 使用自定义菜单相关API： {@code Weixin.with(key).menu()}<br>
 * 
 * <p>
 * 使用相关工具： {@code Weixin.with(key).helper()}<br>
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Weixin {

  private static final Map<String, Weixin> weixinMap = new ConcurrentHashMap<String, Weixin>();

  private String accountKey;

  private final CertificateApi certificate;
  private final CommonApi common;
  private final CustomApi custom;
  private final HelperApi helper;
  private final UserApi user;
  private final UserGroupApi userGroup;
  private final UserTagApi userTag;
  private final MediaApi media;
  private final MenuApi menu;
  private final MessageApi message;
  private final StatisticsApi statistics;
  private final TemplateApi template;

  private Weixin(String accountKey) {
    this.accountKey = accountKey;

    this.certificate = new CertificateApiImpl(accountKey);
    this.common = new CommonApiImpl(accountKey);
    this.custom = new CustomApiImpl(accountKey);
    this.helper = new HelperApiImpl(accountKey);
    this.user = new UserApiImpl(accountKey);
    this.userGroup = new UserGroupApiImpl(accountKey);
    this.userTag = new UserTagApiImpl(accountKey);
    this.media = new MediaApiImpl(accountKey);
    this.menu = new MenuApiImpl(accountKey);
    this.message = new MessageApiImpl(accountKey);
    this.statistics = new StatisticsApiImpl(accountKey);
    this.template = new TemplateApiImpl(accountKey);
  }

  /**
   * 指定使用哪一个已注册的公众号配置.
   * 
   * @param key
   *          公众号配置注册键
   * @return API入口
   */
  public static Weixin with(String key) {
    Weixin weixin = weixinMap.get(key);
    if (weixin == null) {
      synchronized (weixinMap) {
        if (weixin == null) {
          weixinMap.put(key, new Weixin(key));
        }
      }
    }
    return weixinMap.get(key);
  }

  /**
   * 获取唯一的公众号配置.
   * 
   * @return API入口
   */
  public static Weixin withUnique() {
    return with(Convention.DEFAULT_UNIQUE_WEIXIN_KEY);
  }

  /**
   * 获取公众号信息.
   * 
   * @return {@link Account}
   */
  public Account info() {
    return AccountCache.getInstance().get(this.accountKey);
  }

  /**
   * 认证相关API功能.
   * 
   * @return 认证入口
   */
  public CertificateApi certificate() {
    return certificate;
  }

  /**
   * 公共API功能.
   * 
   * @return 公共入口
   */
  public CommonApi common() {
    return common;
  }

  /**
   * 客服相关API功能.
   * 
   * @return 客服入口
   */
  public CustomApi custom() {
    return custom;
  }

  /**
   * 非API，帮助工具.
   * 
   * @return 非API，帮助工具
   */
  public HelperApi helper() {
    return helper;
  }

  /**
   * 用户相关API功能.
   * 
   * @return 用户入口
   */
  public UserApi user() {
    return user;
  }

  /**
   * 用户组相关API功能，微信文档中已无该功能.
   * 
   * @return 用户组入口
   */
  @Deprecated
  public UserGroupApi userGroup() {
    return userGroup;
  }

  /**
   * 用户标签相关API功能.
   * 
   * @return 用户标签入口
   */
  public UserTagApi userTag() {
    return userTag;
  }

  /**
   * 素材管理相关API功能.
   * 
   * @return 素材管理入口
   */
  public MediaApi media() {
    return media;
  }

  /**
   * 自定义菜单相关API功能.
   * 
   * @return 自定义菜单入口
   */
  public MenuApi menu() {
    return menu;
  }

  /**
   * 消息推送相关API功能.
   * 
   * @return 消息推送入口
   */
  public MessageApi message() {
    return message;
  }

  /**
   * 数据统计相关API功能.
   * 
   * @return 数据统计入口
   */
  public StatisticsApi statistics() {
    return statistics;
  }

  /**
   * 消息模板相关API功能.
   * 
   * @return 消息模板入口
   */
  public TemplateApi template() {
    return template;
  }

}
