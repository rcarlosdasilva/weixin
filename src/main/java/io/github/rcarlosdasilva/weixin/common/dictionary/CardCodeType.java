package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 卡券的Code码类型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum CardCodeType {

  /** 文本. */
  CODE_TYPE_TEXT,
  /** 一维码. */
  CODE_TYPE_BARCODE,
  /** 二维码. */
  CODE_TYPE_QRCODE,
  /** 二维码无code显示. */
  CODE_TYPE_ONLY_QRCODE,
  /** 一维码无code显示. */
  CODE_TYPE_ONLY_BARCODE,
  /** 不显示code和条形码类型. */
  CODE_TYPE_NONE;

}
