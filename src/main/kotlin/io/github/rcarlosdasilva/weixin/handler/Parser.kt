package io.github.rcarlosdasilva.weixin.handler

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.core.util.QuickWriter
import com.thoughtworks.xstream.io.HierarchicalStreamWriter
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter
import com.thoughtworks.xstream.io.xml.Xpp3Driver
import io.github.rcarlosdasilva.weixin.core.ExecuteException
import io.github.rcarlosdasilva.weixin.core.MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException
import io.github.rcarlosdasilva.weixin.core.Weixin
import io.github.rcarlosdasilva.weixin.model.notification.*
import io.github.rcarlosdasilva.weixin.model.response.Response
import io.github.rcarlosdasilva.weixin.terms.*
import io.github.rcarlosdasilva.weixin.terms.data.NotificationMessage
import mu.KotlinLogging
import java.io.Writer


/**
 * 微信推送通知，消息解析器
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
   * 解析微信推送过来的XML
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


/**
 * 响应内容解析器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object ResponseParser {


  private val logger = KotlinLogging.logger { }

  /**
   * 解析并封装响应结果为一个指定类型
   *
   * @param <T> The Type of element
   * @param target 指定封装类型
   * @param json json响应字符串
   * @return 封装对象
  </T> */
  fun <T> parse(target: Class<T>, json: String): T? {
    if (Response.seemsLikeError(json)) {
      val errorResponse = JsonHandler.fromJson(json, Response::class.java)
      val success = errorResponse.errorCode == ResultCode.RESULT_0.code

      if (!success) {
        val resultCode = ResultCode.with(errorResponse.errorCode)

        if (errorResponse.isBadAccessToken) {
          logger.debug("微信说我access_token不大行，那我觉着是不是还可以再抢救一下，再试一遍来")
          throw MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException()
        }

        if (resultCode == ResultCode.RESULT_UNKNOWN) {
          logger.debug { "未收录的微信错误代码: code [${errorResponse.errorCode}]" }
        }
        logger.error { "微信请求错误：code [${errorResponse.errorCode}] -- message [${errorResponse.errorMessage}]" }
        if (Weixin.registry.setting.isThrowException) {
          throw ExecuteException(errorResponse, resultCode)
        }

        return if (target == Boolean::class.java) {
          @Suppress("UNCHECKED_CAST")
          false as T
        } else {
          null
        }
      }
    }

    return if (target == Boolean::class.java) {
      @Suppress("UNCHECKED_CAST")
      true as T
    } else {
      JsonHandler.fromJson(json, target)
    }
  }

}
