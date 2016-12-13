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

  /**
   * 取值.
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * 显示颜色.
   */
  public void setColor(String color) {
    this.color = color;
  }

}
