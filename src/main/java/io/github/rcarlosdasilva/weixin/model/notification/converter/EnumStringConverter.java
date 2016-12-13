package io.github.rcarlosdasilva.weixin.model.notification.converter;

import com.thoughtworks.xstream.converters.enums.EnumSingleValueConverter;

/**
 * 简单获取枚举toString()内容转换器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class EnumStringConverter extends EnumSingleValueConverter {

  // private Class<? extends Enum<?>> enumType;

  public EnumStringConverter(Class<? extends Enum<?>> type) {
    super(type);
    // this.enumType = type;
  }

  @Override
  public String toString(Object obj) {
    return obj.toString();
  }

}
