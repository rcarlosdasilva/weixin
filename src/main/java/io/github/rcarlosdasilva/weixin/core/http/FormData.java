package io.github.rcarlosdasilva.weixin.core.http;

/**
 * HTTP请求Form表单内容
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class FormData {

  private String key;
  private String value;

  public FormData(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
