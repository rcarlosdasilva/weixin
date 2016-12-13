package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 二维码类型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum QrCodeAction {

  /** 临时二维码. */
  TEMPORARY("QR_SCENE"),
  /** 带id参数的永久二维码. */
  UNLIMITED_WITH_ID("QR_LIMIT_SCENE"),
  /** 带字符串参数的永久二维码. */
  UNLIMITED_WITH_STRING("QR_LIMIT_STR_SCENE");

  private String text;

  QrCodeAction(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return this.text;
  }

}
