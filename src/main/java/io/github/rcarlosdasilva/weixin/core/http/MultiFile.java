package io.github.rcarlosdasilva.weixin.core.http;

import java.io.File;

/**
 * HTTP请求文件信息
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MultiFile {

  private ContentType contentType;
  private String fileKey;
  private String fileName;
  private File file;

  /**
   * 构造函数,使用通用文件类型.
   */
  public MultiFile(String fileKey, String fileName, File file) {
    this.contentType = ContentType.ANY;
    this.fileKey = fileKey;
    this.fileName = fileName;
    this.file = file;
  }

  /**
   * 构造函数,指定文件类型.
   */
  public MultiFile(ContentType contentType, String fileKey, String fileName, File file) {
    this.contentType = contentType;
    this.fileKey = fileKey;
    this.fileName = fileName;
    this.file = file;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public void setContentType(ContentType contentType) {
    this.contentType = contentType;
  }

  public String getFileKey() {
    return fileKey;
  }

  public void setFileKey(String fileKey) {
    this.fileKey = fileKey;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

}
