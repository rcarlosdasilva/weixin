package io.github.rcarlosdasilva.weixin.model.notification.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 二维码扫描信息
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class ScanCodeInfo {

  @XStreamAlias("ScanType")
  private String type;
  @XStreamAlias("ScanResult")
  private String result;

  /**
   * 扫描类型，一般是qrcode (ScanType).
   * 
   * @return type
   */
  public String getType() {
    return type;
  }

  /**
   * 扫描结果，即二维码对应的字符串信息 (ScanResult).
   * 
   * @return result
   */
  public String getResult() {
    return result;
  }

}
