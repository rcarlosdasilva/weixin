package io.github.rcarlosdasilva.weixin.model.notification

import com.google.common.base.Ascii
import com.thoughtworks.xstream.converters.Converter
import com.thoughtworks.xstream.converters.MarshallingContext
import com.thoughtworks.xstream.converters.UnmarshallingContext
import com.thoughtworks.xstream.converters.enums.EnumSingleValueConverter
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider
import com.thoughtworks.xstream.io.HierarchicalStreamReader
import com.thoughtworks.xstream.io.HierarchicalStreamWriter
import com.thoughtworks.xstream.mapper.Mapper
import io.github.rcarlosdasilva.weixin.terms.*
import io.github.rcarlosdasilva.weixin.terms.data.MessageType
import io.github.rcarlosdasilva.weixin.terms.data.MessageType.*

/**
 * 简单获取枚举toString()内容转换器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class EnumStringConverter(type: Class<out Enum<*>>) : EnumSingleValueConverter(type) {

  override fun toString(obj: Any?): String {
    return obj!!.toString()
  }

}


/**
 * 发送图片xml结构转换器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class SendPicsConverter : Converter {

  override fun canConvert(type: Class<*>): Boolean = type is List<*>

  override fun marshal(source: Any, writer: HierarchicalStreamWriter, context: MarshallingContext) {}

  override fun unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): Any? {
    if (WEIXIN_NOTIFICATION_KEY_EVENT_PIC_LIST.equals(reader.nodeName, true)) {
      val pics = mutableListOf<String>()
      while (reader.hasMoreChildren()) {
        reader.moveDown()
        if (WEIXIN_NOTIFICATION_KEY_ITEM.equals(reader.nodeName, true)) {
          reader.moveDown()
          pics.add(reader.value)
          reader.moveUp()
        }
        reader.moveUp()
      }
      return pics
    }
    return null
  }

}


/**
 * 明文响应结构转换器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ResponseConverter(mapper: Mapper, reflectionProvider: ReflectionProvider) :
  ReflectionConverter(mapper, reflectionProvider) {

  override fun canConvert(type: Class<*>): Boolean = type == PlaintextNotificationResponse::class.java

  override fun marshal(source: Any, writer: HierarchicalStreamWriter, context: MarshallingContext) {
    doMarshal(source, writer, context)

    val notification = source as PlaintextNotificationResponse
    val type = notification.type
    val isPlain = type === MessageType.TEXT
    val tag: String
    tag = if (type === MessageType.NEWS_EXTERNAL) {
      writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_COUNT)
      context.convertAnother(notification.infos.size)
      writer.endNode()
      WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_LIST
    } else {
      Ascii.toUpperCase(type.text.first()) + type.text.substring(1)
    }

    if (!isPlain) {
      writer.startNode(tag)
    }

    val info = notification.info
    when (type) {
      TEXT -> {
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_CONTENT)
        writer.setValue(info.content)
        writer.endNode()
      }
      IMAGE, VOICE -> {
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_ID)
        writer.setValue(info.mediaId)
        writer.endNode()
      }
      VIDEO -> {
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_ID)
        writer.setValue(info.mediaId)
        writer.endNode()
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE)
        writer.setValue(info.title)
        writer.endNode()
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION)
        writer.setValue(info.description)
        writer.endNode()
      }
      MUSIC -> {
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE)
        writer.setValue(info.title)
        writer.endNode()
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION)
        writer.setValue(info.description)
        writer.endNode()
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL)
        writer.setValue(info.url)
        writer.endNode()
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL_HQ)
        writer.setValue(info.otherUrl)
        writer.endNode()
        writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_THUMB_ID)
        writer.setValue(info.mediaThumbId)
        writer.endNode()
      }
      NEWS_EXTERNAL -> {
        for (infoTmp in notification.infos) {
          writer.startNode(WEIXIN_NOTIFICATION_KEY_ITEM)

          writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE)
          writer.setValue(infoTmp.title)
          writer.endNode()
          writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION)
          writer.setValue(infoTmp.description)
          writer.endNode()
          writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL)
          writer.setValue(infoTmp.url)
          writer.endNode()
          writer.startNode(WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL_PIC)
          writer.setValue(infoTmp.otherUrl)
          writer.endNode()

          writer.endNode()
        }
      }
      else -> {
      }
    }

    if (!isPlain) {
      writer.endNode()
    }
  }

  override fun unmarshal(reader: HierarchicalStreamReader, context: UnmarshallingContext): Any? {
    return null
  }

}
