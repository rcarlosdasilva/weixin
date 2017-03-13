package io.github.rcarlosdasilva.weixin.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Formatter;

/**
 * 简单工具
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Utils {

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
      ex.printStackTrace();
      return null;
    }
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

  public static byte[] serialize(Object object) {
    try {
      return serialize_(object);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private static byte[] serialize_(Object object) throws IOException {
    if (object == null) {
      return null;
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(object);
    byte[] result = baos.toByteArray();
    oos.close();
    baos.close();
    return result;
  }

  // public static Object unserialize(byte[] bytes) {
  // try {
  // return unserialize_(bytes);
  // } catch (Exception e) {
  // e.printStackTrace();
  // return null;
  // }
  // }

  @SuppressWarnings("unchecked")
  public static <T> T unserialize(byte[] bytes, Class<T> clazz) {
    try {
      return (T) unserialize_(bytes);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T unserialize(byte[] bytes) {
    try {
      return (T) unserialize_(bytes);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private static Object unserialize_(byte[] bytes) throws ClassNotFoundException, IOException {
    if (bytes == null || bytes.length <= 0) {
      return null;
    }

    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    ObjectInputStream ois = new ObjectInputStream(bais);
    Object result = ois.readObject();
    ois.close();
    bais.close();
    return result;
  }

}
