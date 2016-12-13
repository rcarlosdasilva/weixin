package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 消息加解密模式
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum EncryptionType {

  /**
   * 明文模式.
   */
  PLAIN_TEXT,
  /**
   * 安全模式（推荐）.
   */
  SAFETY,
  /**
   * 兼容模式.
   */
  COMPATIBLE;

}
