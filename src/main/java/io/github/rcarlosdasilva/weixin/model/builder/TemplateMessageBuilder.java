package io.github.rcarlosdasilva.weixin.model.builder;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Template;

/**
 * 模板内容构造器
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class TemplateMessageBuilder {

  private Map<String, Template> data;
  private String defaultColor = Convention.COLOR_BLACK;

  public TemplateMessageBuilder() {
    data = Maps.newHashMap();
  }

  /**
   * 使用统一颜色.
   * 
   * @param color
   *          颜色
   * @return 模板构造器
   */
  public TemplateMessageBuilder withColor(String color) {
    this.defaultColor = color;
    return this;
  }

  /**
   * 设置模板头内容.
   * 
   * @param value
   *          内容
   * @return 模板构造器
   */
  public TemplateMessageBuilder begin(String value) {
    data.put(Convention.TEMPLATE_DATA_BEGIN_KEY, new Template(value, this.defaultColor));
    return this;
  }

  /**
   * 设置模板头内容.
   * 
   * @param value
   *          内容
   * @param color
   *          颜色
   * @return 模板构造器
   */
  public TemplateMessageBuilder begin(String value, String color) {
    data.put(Convention.TEMPLATE_DATA_BEGIN_KEY, new Template(value, color));
    return this;
  }

  /**
   * 设置模板尾部内容.
   * 
   * @param value
   *          内容
   * @return 模板构造器
   */
  public TemplateMessageBuilder end(String value) {
    data.put(Convention.TEMPLATE_DATA_END_KEY, new Template(value, this.defaultColor));
    return this;
  }

  /**
   * 设置模板尾部内容.
   * 
   * @param value
   *          内容
   * @param color
   *          颜色
   * @return 模板构造器
   */
  public TemplateMessageBuilder end(String value, String color) {
    data.put(Convention.TEMPLATE_DATA_END_KEY, new Template(value, color));
    return this;
  }

  /**
   * 添加关键字信息.
   * 
   * @param key
   *          关键字
   * @param value
   *          内容
   * @return 模板构造器
   */
  public TemplateMessageBuilder keyword(String key, String value) {
    data.put(key, new Template(value, this.defaultColor));
    return this;
  }

  /**
   * 添加关键字信息.
   * 
   * @param key
   *          关键字
   * @param value
   *          内容
   * @param color
   *          颜色
   * @return 模板构造器
   */
  public TemplateMessageBuilder keyword(String key, String value, String color) {
    data.put(key, new Template(value, color));
    return this;
  }

  /**
   * 获取模板内容.
   * 
   * @return 模板内容
   */
  public Map<String, Template> build() {
    return this.data;
  }

}
