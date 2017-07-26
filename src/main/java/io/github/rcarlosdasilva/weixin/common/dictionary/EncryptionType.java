package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 消息加解密模式
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
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
