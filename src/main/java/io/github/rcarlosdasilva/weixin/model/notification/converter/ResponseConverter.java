package io.github.rcarlosdasilva.weixin.model.notification.converter;

import com.google.common.base.Ascii;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponsePlaintext;
import io.github.rcarlosdasilva.weixin.model.notification.bean.ResponseAdditionalInfo;

public class ResponseConverter extends ReflectionConverter {

  public ResponseConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
    super(mapper, reflectionProvider);
  }

  @Override
  public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
    return type.equals(NotificationResponsePlaintext.class);
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    doMarshal(source, writer, context);

    NotificationResponsePlaintext notification = (NotificationResponsePlaintext) source;
    MessageType type = notification.getType();
    boolean isPlain = type == MessageType.TEXT;
    String tag;
    if (type == MessageType.NEWS_EXTERNAL) {
      writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_COUNT);
      context.convertAnother(notification.getInfos().size());
      writer.endNode();
      tag = Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_LIST;
    } else {
      tag = Ascii.toUpperCase(type.getText().charAt(0)) + type.getText().substring(1);
    }

    if (!isPlain) {
      writer.startNode(tag);
    }

    ResponseAdditionalInfo info = notification.getInfo();
    switch (type) {
      case TEXT: {
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_CONTENT);
        writer.setValue(info.getContent());
        writer.endNode();
        break;
      }
      case IMAGE:
      case VOICE: {
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_ID);
        writer.setValue(info.getMediaId());
        writer.endNode();
        break;
      }
      case VIDEO: {
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_ID);
        writer.setValue(info.getMediaId());
        writer.endNode();
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE);
        writer.setValue(info.getTitle());
        writer.endNode();
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION);
        writer.setValue(info.getDescription());
        writer.endNode();
        break;
      }
      case MUSIC: {
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE);
        writer.setValue(info.getTitle());
        writer.endNode();
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION);
        writer.setValue(info.getDescription());
        writer.endNode();
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL);
        writer.setValue(info.getUrl());
        writer.endNode();
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL_HQ);
        writer.setValue(info.getOtherUrl());
        writer.endNode();
        writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_THUMB_ID);
        writer.setValue(info.getMediaThumbId());
        writer.endNode();
        break;
      }
      case NEWS_EXTERNAL: {
        for (ResponseAdditionalInfo infoTmp : notification.getInfos()) {
          writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_ITEM);

          writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE);
          writer.setValue(infoTmp.getTitle());
          writer.endNode();
          writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION);
          writer.setValue(infoTmp.getDescription());
          writer.endNode();
          writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL);
          writer.setValue(infoTmp.getUrl());
          writer.endNode();
          writer.startNode(Convention.WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL_PIC);
          writer.setValue(infoTmp.getOtherUrl());
          writer.endNode();

          writer.endNode();
        }
        break;
      }
      default:
        break;
    }

    if (!isPlain) {
      writer.endNode();
    }
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return null;
  }

}
