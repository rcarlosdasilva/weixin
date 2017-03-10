package io.github.rcarlosdasilva.weixin.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {

  private static final Properties properties = new Properties();

  static {
    InputStream in = ClassLoader.getSystemResourceAsStream("sample.properties");
    try {
      properties.load(in);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static String get(String key) {
    return properties.getProperty(key);
  }

}
