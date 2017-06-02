package io.github.rcarlosdasilva.weixin.core.encryption;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.github.rcarlosdasilva.weixin.core.parser.NotificationParser;
import io.github.rcarlosdasilva.weixin.model.notification.Notification;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponseEncrypted;

/**
 * 微信加解密工具
 * <p>
 * 提供接收和推送给公众平台消息的加解密接口(UTF8编码的字符串)
 * <ol>
 * <li>第三方回复加密消息给公众平台</li>
 * <li>第三方收到公众平台发送的消息，验证消息的安全性，并对消息进行解密</li>
 * </ol>
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * <li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html，
 * JDK8的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html）</li>
 * <li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * <li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * <li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Encryptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(Encryptor.class);

  private static final int RANDOM_START = '0' - 1;
  private static final int RANDOM_END = 'z' + 1;
  private static final int RANDOM_GAP = RANDOM_END - RANDOM_START;
  private static final Random RANDOM = new Random();
  private static final Charset CHARSET = Charset.forName("utf-8");
  private static final Base64 BASE64 = new Base64();

  private Encryptor() {
  }

  /**
   * 将公众平台回复用户的消息加密打包.
   * <ol>
   * <li>对要发送的消息进行AES-CBC加密</li>
   * <li>生成安全签名</li>
   * <li>将消息密文和安全签名打包成xml格式</li>
   * </ol>
   * 
   * @param appid
   *          公众平台appid
   * @param token
   *          公众平台上，开发者设置的token
   * @param key
   *          公众平台上，开发者设置的EncodingAESKey
   * @param originalContent
   *          公众平台待回复用户的消息，xml格式的字符串
   * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce,
   *         encrypt的xml格式的字符串
   */
  public static String encrypt(String appid, String token, String key, String originalContent) {
    Preconditions.checkNotNull(appid);
    Preconditions.checkNotNull(token);
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(originalContent);

    String ciphertext = "";
    String annex = random(16);
    byte[] aesKey = Base64.decodeBase64(key + "=");
    ByteGroup byteCollector = new ByteGroup();
    byte[] annexBytes = annex.getBytes(CHARSET);
    byte[] contentBytes = originalContent.getBytes(CHARSET);
    byte[] networkBytesOrder = getNetworkBytesOrder(contentBytes.length);
    byte[] appidBytes = appid.getBytes(CHARSET);

    // annex + networkBytesOrder + originalContent + appid
    byteCollector.addBytes(annexBytes);
    byteCollector.addBytes(networkBytesOrder);
    byteCollector.addBytes(contentBytes);
    byteCollector.addBytes(appidBytes);

    // ... + pad: 使用自定义的填充方式对明文进行补位填充
    byte[] padBytes = Pkcs7Encoder.encode(byteCollector.size());
    byteCollector.addBytes(padBytes);

    // 获得最终的字节流, 未加密
    byte[] unencrypted = byteCollector.toBytes();

    try {
      // 设置加密模式为AES的CBC模式
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
      IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

      // 加密
      byte[] encrypted = cipher.doFinal(unencrypted);

      // 使用BASE64对加密后的字符串进行编码
      ciphertext = BASE64.encodeToString(encrypted);
    } catch (Exception ex) {
      LOGGER.error("weixin encryptor", ex);
      return null;
    }

    long timestamp = Calendar.getInstance().getTimeInMillis();
    String nonce = random(16);
    String signature = sha1(tidy(token, timestamp, nonce, ciphertext));
    NotificationResponseEncrypted responseEncrypted = new NotificationResponseEncrypted(ciphertext,
        signature, timestamp, nonce);
    return NotificationParser.toXml(responseEncrypted);
  }

  /**
   * 检验消息的真实性，并且获取解密后的明文.
   * <ol>
   * <li>利用收到的密文生成安全签名，进行签名验证</li>
   * <li>若验证通过，则提取xml中的加密消息</li>
   * <li>对消息进行解密</li>
   * </ol>
   * 
   * @param token
   *          公众平台上，开发者设置的token
   * @param key
   *          公众平台上，开发者设置的EncodingAESKey
   * @param ciphertext
   *          密文，对应POST请求的数据
   * @param signature
   *          签名串，对应URL参数的msg_signature
   * @param timestamp
   *          时间戳，对应URL参数的timestamp
   * @param nonce
   *          随机串，对应URL参数的nonce
   * @return 解密后的原文模型
   */
  public static Notification decrypt(String token, String key, String ciphertext, String signature,
      long timestamp, String nonce) {
    Preconditions.checkNotNull(token);
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(ciphertext);
    Preconditions.checkNotNull(signature);
    Preconditions.checkNotNull(nonce);

    String resignature = sha1(tidy(token, timestamp, nonce, ciphertext));
    if (!signature.equals(resignature)) {
      return null;
    }

    byte[] original;
    byte[] aesKey = Base64.decodeBase64(key + "=");
    try {
      // 设置解密模式为AES的CBC模式
      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
      IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
      cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

      // 使用BASE64对密文进行解码
      byte[] encrypted = Base64.decodeBase64(ciphertext);

      // 解密
      original = cipher.doFinal(encrypted);
    } catch (Exception ex) {
      LOGGER.error("weixin encryptor", ex);
      return null;
    }

    Notification notification = null;
    String originalContent = null;
    String appId = null;
    try {
      // 去除补位字符
      byte[] bytes = Pkcs7Encoder.decode(original);

      // 分离16位随机字符串,网络字节序和AppId
      byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

      int xmlLength = recoverNetworkBytesOrder(networkOrder);

      originalContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
      appId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
      notification = NotificationParser.parse(originalContent);
      notification.setAppId(appId);
    } catch (Exception ex) {
      LOGGER.debug("weixin encryptor", ex);

      notification = new Notification();
      notification.setAppId(appId);
      notification.setPlaintext(originalContent);
    }

    return notification;
  }

  /**
   * 生成4个字节的网络字节序.
   */
  private static byte[] getNetworkBytesOrder(int sourceNumber) {
    byte[] orderBytes = new byte[4];
    orderBytes[3] = (byte) (sourceNumber & 0xFF);
    orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
    orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
    orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
    return orderBytes;
  }

  // 还原4个字节的网络字节序
  private static int recoverNetworkBytesOrder(byte[] orderBytes) {
    int sourceNumber = 0;
    for (int i = 0; i < 4; i++) {
      sourceNumber <<= 8;
      sourceNumber |= orderBytes[i] & 0xff;
    }
    return sourceNumber;
  }

  private static String random(int count) {
    Preconditions.checkArgument(count > 0);

    final char[] buffer = new char[count];

    while (count-- != 0) {
      char ch = (char) (RANDOM.nextInt(RANDOM_GAP) + RANDOM_START);

      if (ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z') {
        buffer[count] = ch;
      } else {
        count++;
      }
    }
    return new String(buffer);
  }

  private static String sha1(String raw) {
    return DigestUtils.sha1Hex(raw);
  }

  private static String tidy(String token, long timestamp, String nonce, String ciphertext) {
    String[] params = new String[] { token, String.valueOf(timestamp), nonce, ciphertext };
    StringBuffer raw = new StringBuffer();
    Arrays.sort(params);
    for (int i = 0; i < 4; i++) {
      raw.append(params[i]);
    }
    return raw.toString();
  }

}
