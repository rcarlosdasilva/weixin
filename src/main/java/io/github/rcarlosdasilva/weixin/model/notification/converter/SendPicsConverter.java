package io.github.rcarlosdasilva.weixin.model.notification.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import io.github.rcarlosdasilva.weixin.common.Convention;

/**
 * 发送图片xml结构转换器
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class SendPicsConverter implements Converter {

  @Override
  public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
    return type.equals(ArrayList.class);
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (Convention.WEIXIN_NOTIFICATION_KEY_EVENT_PIC_LIST.equalsIgnoreCase(reader.getNodeName())) {
      List<String> pics = Lists.newArrayList();
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        if (Convention.WEIXIN_NOTIFICATION_KEY_ITEM.equalsIgnoreCase(reader.getNodeName())) {
          reader.moveDown();
          pics.add(reader.getValue());
          reader.moveUp();
        }
        reader.moveUp();
      }
      return pics;
    }
    return null;
  }

}
