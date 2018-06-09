package io.github.rcarlosdasilva.weixin.handler

import io.github.rcarlosdasilva.weixin.model.notification.EncryptedNotificationResponse
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponse
import mu.KotlinLogging
import org.junit.Assert
import org.junit.Test

class EncryptionTest {

  private val logger = KotlinLogging.logger {}

  private var encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG"
  private var token = "aes_token"
  private var appId = "wxb11529c136998cb6"
  private var replyMsg =
      "<xml><ToUserName><![CDATA[oia2Tj我是中文jewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1357290913</CreateTime><MsgType><![CDATA[shortvideo]]></MsgType><MediaId><![CDATA[aoijoin21909j3virne]]></MediaId><ThumbMediaId><![CDATA[fo3ininalksndvca]]></ThumbMediaId><MsgId>1234567890123456</MsgId></xml>"

  @Test
  fun test() {
    val response = encrypt()
    Assert.assertNotNull(response)

    val xml = NotificationParser.toXml(response!!)
    Assert.assertNotNull(xml)
    logger.info { xml }

    val notification = decrypt(response as EncryptedNotificationResponse)
    Assert.assertNotNull(notification)
    Assert.assertNotNull(notification!!.plaintext)
    Assert.assertEquals(replyMsg, notification.plaintext)

    logger.info { notification.plaintext }
  }

  private fun encrypt(): NotificationResponse? =
      Encryptor.encrypt(appId, token, encodingAesKey, replyMsg)

  private fun decrypt(mock: EncryptedNotificationResponse) =
      Encryptor.decrypt(token, encodingAesKey, mock.ciphertext, mock.signature, mock.timestamp, mock.nonce)

}
