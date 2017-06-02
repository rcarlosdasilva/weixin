package io.github.rcarlosdasilva.weixin.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Formatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单工具
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Utils {

  private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

  private Utils() {
  }

  /**
   * 对URL编码.
   * 
   * @param url
   *          url
   * @return 编码后的url
   */
  public static String urlEncode(String url) {
    try {
      return URLEncoder.encode(url, Convention.DEFAULT_ENCODING);
    } catch (UnsupportedEncodingException ex) {
      LOGGER.error("weixin utils", ex);
    }
    return null;
  }

  /**
   * 将byte转换成16进制字符串.
   *
   * @param bytes
   *          字节流
   * @return 16进制字符串
   */
  public static String byteToHex(final byte[] bytes) {
    Formatter formatter = new Formatter();
    for (byte b : bytes) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

  public static <T> byte[] serialize(T object) {
    if (object == null) {
      return null;
    }

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(object);
      byte[] result = baos.toByteArray();
      oos.close();
      baos.close();
      return result;
    } catch (Exception ex) {
      LOGGER.error("weixin utils", ex);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static <T> T unserialize(byte[] bytes) {
    if (bytes == null || bytes.length <= 0) {
      return null;
    }

    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bais);
      Object result = ois.readObject();
      ois.close();
      bais.close();
      return (T) result;
    } catch (Exception ex) {
      LOGGER.error("weixin utils", ex);
    }
    return null;
  }

}
