package io.github.rcarlosdasilva.weixin.handler

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.core.util.QuickWriter
import com.thoughtworks.xstream.io.HierarchicalStreamWriter
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter
import com.thoughtworks.xstream.io.xml.Xpp3Driver
import io.github.rcarlosdasilva.weixin.model.notification.*
import io.github.rcarlosdasilva.weixin.terms.*
import io.github.rcarlosdasilva.weixin.terms.data.NotificationMessage
import java.io.Writer


/**
 * 微信推送通知，消息解析器.
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object NotificationParser {

  private var xs: XStream

  init {
    xs = XStream(object : Xpp3Driver() {
      override fun createWriter(out: Writer): HierarchicalStreamWriter {
        val prefixCdata = "<![CDATA["
        val suffixCdata = "]]>"

        return object : PrettyPrintWriter(out) {
          private var needCdata: Boolean = false

          override fun setValue(text: String) {
            needCdata = text.matches("^[0-9]+\\.?[0-9]{0,6}$".toRegex())
            super.setValue(text)
          }

          override fun writeText(writer: QuickWriter, text: String) {
            if (needCdata && !(text.startsWith(prefixCdata) || text.endsWith(suffixCdata))) {
              writer.write("$prefixCdata$text$suffixCdata")
            } else {
              writer.write(text)
            }
          }

        }
      }
    })

    xs.ignoreUnknownElements()
    xs.processAnnotations(
      arrayOf(
        Notification::class.java,
        Event::class.java,
        Message::class.java,
        PlaintextNotificationResponse::class.java,
        EncryptedNotificationResponse::class.java
      )
    )
  }

  /**
   * 解析微信推送过来的XML.
   *
   * @param xmlString 规范的XML格式字符串
   * @return 微信推送消息封装
   */
  fun parse(xmlString: String): Notification {
    var content = xmlString.replace(
      WEIXIN_NOTIFICATION_XML_TAG_ORIGIN,
      WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION
    )
    val notification = xs.fromXML(content) as Notification

    val messageType = notification.messageType
    if (messageType != null) {
      if (messageType === NotificationMessage.EVENT) {
        content = content.replace(
          WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION,
          WEIXIN_NOTIFICATION_XML_TAG_EVENT
        )
        val event = xs.fromXML(content) as Event
        notification.event = event
      } else {
        content = content.replace(
          WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION,
          WEIXIN_NOTIFICATION_XML_TAG_MESSAGE
        )
        val message = xs.fromXML(content) as Message
        notification.message = message
      }
    }

    val infoType = notification.infoType
    if (infoType != null) {
      content = content.replace(
        WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION,
        WEIXIN_NOTIFICATION_XML_TAG_INFO
      )
      val opInfo = xs.fromXML(content) as OpInfo
      notification.opInfo = opInfo
    }

    return notification
  }

  fun toXml(response: NotificationResponse): String = xs.toXML(response)

}


