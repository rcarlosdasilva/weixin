package io.github.rcarlosdasilva.weixin.common;

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

}
