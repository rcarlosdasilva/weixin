package io.github.rcarlosdasilva.weixin.model.request.message.bean;

/**
 * 模板内容参数
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@SuppressWarnings("unused")
public class Template {

  private String value;
  private String color;

  public Template(String value, String color) {
    this.value = value;
    this.color = color;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setColor(String color) {
    this.color = color;
  }

}
