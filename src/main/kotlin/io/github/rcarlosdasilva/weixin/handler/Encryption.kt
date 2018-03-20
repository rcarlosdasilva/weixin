package io.github.rcarlosdasilva.weixin.handler

import com.google.common.base.Preconditions
import io.github.rcarlosdasilva.weixin.Notification
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.DigestUtils
import org.slf4j.LoggerFactory
import java.nio.charset.Charset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

internal const val ENCODING = "UTF-8"
internal val CHARSET = Charset.forName(ENCODING)

/**
 * 微信加解密工具
 *
 * 提供接收和推送给公众平台消息的加解密接口(UTF8编码的字符串)
 *
 *  1. 第三方回复加密消息给公众平台
 *  1. 第三方收到公众平台发送的消息，验证消息的安全性，并对消息进行解密
 *
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 *
 *  1. 在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html，
 * JDK8的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html）
 *  1. 下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt
 *  1. 如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件
 *  1. 如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object Encryptor {

  private val LOGGER = LoggerFactory.getLogger(Encryptor::class.java)

  private const val RANDOM_START = '0'.toInt() - 1
  private const val RANDOM_END = 'z'.toInt() + 1
  private const val RANDOM_GAP = RANDOM_END - RANDOM_START

  private val RANDOM = Random()
  private val BASE64 = Base64()

  /**
   * 将公众平台回复用户的消息加密打包
   *
   *  1. 对要发送的消息进行AES-CBC加密
   *  1. 生成安全签名
   *  1. 将消息密文和安全签名打包成xml格式
   *
   * @param appid 公众平台appid
   * @param token 公众平台上，开发者设置的token
   * @param key 公众平台上，开发者设置的EncodingAESKey
   * @param originalContent 公众平台待回复用户的消息，xml格式的字符串
   * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的xml格式的字符串
   */
  fun encrypt(appid: String, token: String, key: String, originalContent: String): String? {
    Preconditions.checkNotNull(appid)
    Preconditions.checkNotNull(token)
    Preconditions.checkNotNull(key)
    Preconditions.checkNotNull(originalContent)

    var ciphertext: String
    val annex = random(16)
    val aesKey = Base64.decodeBase64("$key=")
    val byteCollector = ByteGroup()
    val annexBytes = annex.toByteArray(CHARSET)
    val contentBytes = originalContent.toByteArray(CHARSET)
    val networkBytesOrder = getNetworkBytesOrder(contentBytes.size)
    val appidBytes = appid.toByteArray(CHARSET)

    // annex + networkBytesOrder + originalContent + appid
    byteCollector.addBytes(annexBytes)
    byteCollector.addBytes(networkBytesOrder)
    byteCollector.addBytes(contentBytes)
    byteCollector.addBytes(appidBytes)

    // ... + pad: 使用自定义的填充方式对明文进行补位填充
    val padBytes = Pkcs7Encoder.encode(byteCollector.size())
    byteCollector.addBytes(padBytes)

    // 获得最终的字节流, 未加密
    val unencrypted = byteCollector.toBytes()

    try {
      // 设置加密模式为AES的CBC模式
      val cipher = Cipher.getInstance("AES/CBC/NoPadding")
      val keySpec = SecretKeySpec(aesKey, "AES")
      val iv = IvParameterSpec(aesKey, 0, 16)
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)

      // 加密
      val encrypted = cipher.doFinal(unencrypted)

      // 使用BASE64对加密后的字符串进行编码
      ciphertext = BASE64.encodeToString(encrypted)
    } catch (ex: Exception) {
      LOGGER.error("weixin encryptor", ex)
      return null
    }

    val timestamp = Calendar.getInstance().timeInMillis
    val nonce = random(16)
    val signature = sha1(tidy(token, timestamp, nonce, ciphertext))
//    val responseEncrypted = NotificationResponseEncrypted(ciphertext, signature, timestamp, nonce)
//    return NotificationParser.toXml(responseEncrypted)
    return null
  }

  /**
   * 检验消息的真实性，并且获取解密后的明文
   *
   *  1. 利用收到的密文生成安全签名，进行签名验证
   *  1. 若验证通过，则提取xml中的加密消息
   *  1. 对消息进行解密
   *
   *
   * @param token
   * 公众平台上，开发者设置的token
   * @param key
   * 公众平台上，开发者设置的EncodingAESKey
   * @param ciphertext
   * 密文，对应POST请求的数据
   * @param signature
   * 签名串，对应URL参数的msg_signature
   * @param timestamp
   * 时间戳，对应URL参数的timestamp
   * @param nonce
   * 随机串，对应URL参数的nonce
   * @return 解密后的原文模型
   */
  fun decrypt(
    token: String, key: String, ciphertext: String, signature: String,
    timestamp: Long, nonce: String
  ): Notification? {
    Preconditions.checkNotNull(token)
    Preconditions.checkNotNull(key)
    Preconditions.checkNotNull(ciphertext)
    Preconditions.checkNotNull(signature)
    Preconditions.checkNotNull(nonce)

    val resignature = sha1(tidy(token, timestamp, nonce, ciphertext))
    if (signature != resignature) {
      return null
    }

    val original: ByteArray
    val aesKey = Base64.decodeBase64("$key=")
    try {
      // 设置解密模式为AES的CBC模式
      val cipher = Cipher.getInstance("AES/CBC/NoPadding")
      val keySpec = SecretKeySpec(aesKey, "AES")
      val iv = IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16))
      cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)

      // 使用BASE64对密文进行解码
      val encrypted = Base64.decodeBase64(ciphertext)

      // 解密
      original = cipher.doFinal(encrypted)
    } catch (ex: Exception) {
      LOGGER.error("weixin encryptor", ex)
      return null
    }

    var notification: Notification? = null
    var originalContent: String? = null
    var appId: String? = null
    try {
      // 去除补位字符
      val bytes = Pkcs7Encoder.decode(original)

      // 分离16位随机字符串,网络字节序和AppId
      val networkOrder = Arrays.copyOfRange(bytes, 16, 20)

      val xmlLength = recoverNetworkBytesOrder(networkOrder)

      originalContent = String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET)
      appId = String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.size), CHARSET)
//      notification = NotificationParser.parse(originalContent)
//      notification!!.setAppId(appId)
    } catch (ex: Exception) {
      LOGGER.debug("weixin encryptor", ex)

      notification = Notification()
//      notification!!.setAppId(appId)
//      notification!!.setPlaintext(originalContent)
    }

    return notification
  }

  /**
   * 生成4个字节的网络字节序
   */
  private fun getNetworkBytesOrder(sourceNumber: Int): ByteArray {
    val orderBytes = ByteArray(4)
    orderBytes[3] = (sourceNumber and 0xFF).toByte()
    orderBytes[2] = (sourceNumber shr 8 and 0xFF).toByte()
    orderBytes[1] = (sourceNumber shr 16 and 0xFF).toByte()
    orderBytes[0] = (sourceNumber shr 24 and 0xFF).toByte()
    return orderBytes
  }

  // 还原4个字节的网络字节序
  private fun recoverNetworkBytesOrder(orderBytes: ByteArray): Int {
    var sourceNumber = 0
    for (i in 0..3) {
      sourceNumber = sourceNumber shl 8
      sourceNumber = sourceNumber or (orderBytes[i].toInt() and 0xff)
    }
    return sourceNumber
  }

  private fun random(count: Int): String {
    var count = count
    Preconditions.checkArgument(count > 0)

    val buffer = CharArray(count)

    while (count-- != 0) {
      val ch = (RANDOM.nextInt(RANDOM_GAP) + RANDOM_START).toChar()

      if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z') {
        buffer[count] = ch
      } else {
        count++
      }
    }
    return String(buffer)
  }

  private fun sha1(raw: String): String {
    return DigestUtils.sha1Hex(raw)
  }

  private fun tidy(token: String, timestamp: Long, nonce: String, ciphertext: String): String {
    val params = arrayOf(token, timestamp.toString(), nonce, ciphertext)
    val raw = StringBuffer()
    Arrays.sort(params)
    for (i in 0..3) {
      raw.append(params[i])
    }
    return raw.toString()
  }

}

internal class ByteGroup {

  var byteContainer = ArrayList<Byte>()

  fun toBytes(): ByteArray {
    val bytes = ByteArray(byteContainer.size)
    for (i in byteContainer.indices) {
      bytes[i] = byteContainer[i]
    }
    return bytes
  }

  fun addBytes(bytes: ByteArray): ByteGroup {
    for (b in bytes) {
      byteContainer.add(b)
    }
    return this
  }

  fun size(): Int {
    return byteContainer.size
  }

}

/**
 * 提供基于PKCS7算法的加解密接口
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object Pkcs7Encoder {

  private const val BLOCK_SIZE = 32

  /**
   * 获得对明文进行补位填充的字节
   *
   * @param count
   * 需要进行填充补位操作的明文字节个数
   * @return 补齐用的字节数组
   */
  fun encode(count: Int): ByteArray {
    // 计算需要填充的位数
    var amountToPad = BLOCK_SIZE - count % BLOCK_SIZE
    if (amountToPad == 0) {
      amountToPad = BLOCK_SIZE
    }

    // 获得补位所用的字符
    val padChr = charOf(amountToPad)
    val tmp = StringBuilder()
    for (index in 0 until amountToPad) {
      tmp.append(padChr)
    }
    return tmp.toString().toByteArray(CHARSET)
  }

  /**
   * 删除解密后明文的补位字符
   *
   * @param decrypted
   * 解密后的明文
   * @return 删除补位字符后的明文
   */
  fun decode(decrypted: ByteArray): ByteArray {
    var pad = decrypted[decrypted.size - 1].toInt()
    if (pad < 1 || pad > 32) {
      pad = 0
    }
    return Arrays.copyOfRange(decrypted, 0, decrypted.size - pad)
  }

  /**
   * 将数字转化成ASCII码对应的字符，用于对明文进行补码
   *
   * @param number
   * 需要转化的数字
   * @return 转化得到的字符
   */
  fun charOf(number: Int): Char {
    val target = (number and 0xFF).toByte()
    return target.toChar()
  }

}