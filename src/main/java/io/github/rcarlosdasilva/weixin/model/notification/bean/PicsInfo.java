package io.github.rcarlosdasilva.weixin.model.notification.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import io.github.rcarlosdasilva.weixin.model.notification.converter.SendPicsConverter;

/**
 * 发送的图片信息
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class PicsInfo {

  @XStreamAlias("Count")
  private int count;
  @XStreamAlias("PicList")
  @XStreamConverter(SendPicsConverter.class)
  private List<String> pics;

  /**
   * 发送的图片数量 (Count).
   * 
   * @return count
   */
  public int getCount() {
    return count;
  }

  /**
   * 图片的MD5值，开发者若需要，可用于验证接收到图片.
   * 
   * @return 图片md5列表
   */
  public List<String> getPics() {
    return pics;
  }

}
