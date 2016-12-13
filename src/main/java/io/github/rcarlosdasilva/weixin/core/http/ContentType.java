package io.github.rcarlosdasilva.weixin.core.http;

/**
 * Content-Type
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum ContentType {

  /**
   * .*（ 二进制流，不知道下载文件类型）.
   */
  ANY("application/octet-stream"),
  /**
   * JSON.
   */
  JSON("application/json"),
  /**
   * XML.
   */
  XML("application/xml"),
  /**
   * TEXT.
   */
  TEXT("text/plain"),
  /**
   * video/avi.
   */
  AVI("video/avi"),
  /**
   * BMP.
   */
  BMP("application/x-bmp"),
  /**
   * DOC.
   */
  DOC("application/msword"),
  /**
   * GIF.
   */
  GIF("image/gif"),
  /**
   * HTML.
   */
  HTML("text/html"),
  /**
   * ICO.
   */
  ICO("image/x-icon"),
  /**
   * JPEG.
   */
  JPEG("image/jpeg"),
  /**
   * MDB.
   */
  MDB("application/msaccess"),
  /**
   * MP4.
   */
  MP4("video/mpeg4"),
  /**
   * PDF.
   */
  PDF("application/pdf"),
  /**
   * PNG.
   */
  PNG("image/png"),
  /**
   * PPT.
   */
  PPT("application/vnd.ms-powerpoint"),
  /**
   * SWF.
   */
  SWF("application/x-shockwave-flash"),
  /**
   * VCF.
   */
  VCF("text/x-vcard"),
  /**
   * WMA.
   */
  WMA("audio/x-ms-wma"),
  /**
   * WMV.
   */
  WMV("video/x-ms-wmv"),
  /**
   * XLS.
   */
  XLS("application/vnd.ms-excel");

  private String text;

  private ContentType(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

}
