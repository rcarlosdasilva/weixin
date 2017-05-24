package io.github.rcarlosdasilva.weixin.core.parser;

import java.io.Writer;

import com.google.common.base.Preconditions;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationInfoType;
import io.github.rcarlosdasilva.weixin.common.dictionary.NotificationMessageType;
import io.github.rcarlosdasilva.weixin.model.notification.Event;
import io.github.rcarlosdasilva.weixin.model.notification.Message;
import io.github.rcarlosdasilva.weixin.model.notification.Notification;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponse;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponseEncrypted;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponsePlaintext;
import io.github.rcarlosdasilva.weixin.model.notification.OpenInfo;

/**
 * 微信推送通知，消息解析器.
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class NotificationParser {

  private static final XStream xs;

  static {
    xs = new XStream(new Xpp3Driver() {
      @Override
      public HierarchicalStreamWriter createWriter(Writer out) {

        final String prefixCdata = "<![CDATA[";
        final String suffixCdata = "]]>";

        return new PrettyPrintWriter(out) {
          private boolean needCdata;

          @Override
          public void setValue(String text) {
            needCdata = !text.matches("^[0-9]+\\.?[0-9]{0,6}$");
            super.setValue(text);
          }

          @Override
          protected void writeText(QuickWriter writer, String text) {
            if (needCdata && !(text.startsWith(prefixCdata) || text.endsWith(suffixCdata))) {
              writer.write(prefixCdata + text + suffixCdata);
            } else {
              writer.write(text);
            }
          }

        };
      }
    });
    xs.ignoreUnknownElements();
    xs.processAnnotations(new Class[] { Notification.class, Event.class, Message.class,
        NotificationResponsePlaintext.class, NotificationResponseEncrypted.class });
  }

  /**
   * 解析微信推送过来的XML.
   *
   * @param xmlString
   *          规范的XML格式字符串
   * @return 微信推送消息封装
   */
  public static Notification parse(String xmlString) {
    Preconditions.checkNotNull(xmlString, "Notification content is empty from weixin.");

    xmlString = xmlString.replaceAll(Convention.WEIXIN_NOTIFICATION_XML_TAG_ORIGIN,
        Convention.WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION);
    Notification notification = (Notification) xs.fromXML(xmlString);

    NotificationMessageType messagetype = notification.getMessageType();
    if (messagetype != null) {
      if (messagetype == NotificationMessageType.EVENT) {
        xmlString = xmlString.replaceAll(Convention.WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION,
            Convention.WEIXIN_NOTIFICATION_XML_TAG_EVENT);
        Event event = (Event) xs.fromXML(xmlString);
        notification.setEvent(event);
      } else {
        xmlString = xmlString.replaceAll(Convention.WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION,
            Convention.WEIXIN_NOTIFICATION_XML_TAG_MESSAGE);
        Message message = (Message) xs.fromXML(xmlString);
        notification.setMessage(message);
      }
    }

    NotificationInfoType infoType = notification.getInfoType();
    if (infoType != null) {
      xmlString = xmlString.replaceAll(Convention.WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION,
          Convention.WEIXIN_NOTIFICATION_XML_TAG_INFO);
      OpenInfo openInfo = (OpenInfo) xs.fromXML(xmlString);
      notification.setOpenInfo(openInfo);
    }

    return notification;
  }

  public static String toXml(NotificationResponse response) {
    return xs.toXML(response);
  }

}
