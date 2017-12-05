package io.github.rcarlosdasilva.weixin.core;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.api.weixin.CertificateApi;
import io.github.rcarlosdasilva.weixin.api.weixin.CommentApi;
import io.github.rcarlosdasilva.weixin.api.weixin.CommonApi;
import io.github.rcarlosdasilva.weixin.api.weixin.CustomerServiceApi;
import io.github.rcarlosdasilva.weixin.api.weixin.HelperApi;
import io.github.rcarlosdasilva.weixin.api.weixin.MaterialApi;
import io.github.rcarlosdasilva.weixin.api.weixin.MenuApi;
import io.github.rcarlosdasilva.weixin.api.weixin.MessageApi;
import io.github.rcarlosdasilva.weixin.api.weixin.StatisticsApi;
import io.github.rcarlosdasilva.weixin.api.weixin.TemplateApi;
import io.github.rcarlosdasilva.weixin.api.weixin.UserApi;
import io.github.rcarlosdasilva.weixin.api.weixin.UserTagApi;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.CertificateApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.CommentApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.CommonApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.CustomerServiceApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.HelperApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.MaterialApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.MenuApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.MessageApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.StatisticsApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.TemplateApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.UserApiImpl;
import io.github.rcarlosdasilva.weixin.api.weixin.impl.UserTagApiImpl;
import io.github.rcarlosdasilva.weixin.common.Convention;

/**
 * 微信公众号API使用统一入口
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
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class Weixin {

  private static final Map<String, Weixin> HOLDER = Maps.newHashMap();

  private final CertificateApi certificate;
  private final CommonApi common;
  private final CustomerServiceApi customerService;
  private final HelperApi helper;
  private final UserApi user;
  private final UserTagApi userTag;
  private final MaterialApi material;
  private final CommentApi comment;
  private final MenuApi menu;
  private final MessageApi message;
  private final StatisticsApi statistics;
  private final TemplateApi template;

  private Weixin(String accountKey) {
    this.certificate = new CertificateApiImpl(accountKey);
    this.common = new CommonApiImpl(accountKey);
    this.customerService = new CustomerServiceApiImpl(accountKey);
    this.helper = new HelperApiImpl(accountKey);
    this.user = new UserApiImpl(accountKey);
    this.userTag = new UserTagApiImpl(accountKey);
    this.material = new MaterialApiImpl(accountKey);
    this.comment = new CommentApiImpl(accountKey);
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
    Weixin weixin = HOLDER.get(key);
    if (weixin == null) {
      synchronized (HOLDER) {
        HOLDER.put(key, new Weixin(key));
      }
    }
    return HOLDER.get(key);
  }

  /**
   * 获取唯一的公众号配置.
   * 
   * @return API入口
   */
  public static Weixin unique() {
    return with(Convention.DEFAULT_UNIQUE_WEIXIN_KEY);
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
  public CustomerServiceApi customerService() {
    return customerService;
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
  public MaterialApi material() {
    return material;
  }

  /**
   * 图文消息留言管理相关API功能
   * 
   * @return 图文消息留言管理入口
   */
  public CommentApi comment() {
    return comment;
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
