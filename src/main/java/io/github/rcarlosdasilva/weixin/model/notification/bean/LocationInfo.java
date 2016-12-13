package io.github.rcarlosdasilva.weixin.model.notification.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 位置信息
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class LocationInfo {

  @XStreamAlias("Location_X")
  private double locationX;
  @XStreamAlias("Location_Y")
  private double locationY;
  @XStreamAlias("Scale")
  private int scale;
  @XStreamAlias("Label")
  private String address;
  @XStreamAlias("Poiname")
  private String poiName;

  /**
   * X坐标信息 (Location_X).
   */
  public double getLocationX() {
    return locationX;
  }

  /**
   * Y坐标信息 (Location_Y).
   */
  public double getLocationY() {
    return locationY;
  }

  /**
   * 精度，可理解为精度或者比例尺、越精细的话 scale越高 (Scale).
   */
  public int getScale() {
    return scale;
  }

  /**
   * 地理位置的字符串信息 (Label).
   */
  public String getAddress() {
    return address;
  }

  /**
   * 朋友圈POI的名字，可能为空 (Poiname).
   */
  public String getPoiName() {
    return poiName;
  }

}
