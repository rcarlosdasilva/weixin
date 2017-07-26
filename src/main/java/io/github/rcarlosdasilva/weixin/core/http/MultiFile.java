package io.github.rcarlosdasilva.weixin.core.http;

import java.io.File;

/**
 * HTTP请求文件信息
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class MultiFile {

  private ContentType contentType;
  private String fileKey;
  private String fileName;
  private File file;

  /**
   * 构造函数,使用通用文件类型.
   * 
   * @param fileKey
   *          文件key
   * @param fileName
   *          文件名
   * @param file
   *          文件
   */
  public MultiFile(String fileKey, String fileName, File file) {
    this.contentType = ContentType.ANY;
    this.fileKey = fileKey;
    this.fileName = fileName;
    this.file = file;
  }

  /**
   * 构造函数,指定文件类型.
   * 
   * @param contentType
   *          Content-Type
   * @param fileKey
   *          文件key
   * @param fileName
   *          文件名
   * @param file
   *          文件
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
