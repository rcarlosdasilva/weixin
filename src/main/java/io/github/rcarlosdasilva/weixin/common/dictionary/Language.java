package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 语言
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum Language {

  /** 简体中文. */
  ZH_CN("zh_CN"),
  /** 繁体中文TW. */
  ZH_TW("zh_TW"),
  /** 繁体中文HK. */
  ZH_HK("zh_HK"),
  /** 英文. */
  EN_US("en"),
  /** 印尼语. */
  ID("id"),
  /** 马来语. */
  MS("ms"),
  /** 西班牙语. */
  ES("es"),
  /** 韩语. */
  KO("ko"),
  /** 意大利语. */
  IT("it"),
  /** 日语. */
  JA("ja"),
  /** 波兰语. */
  PL("pl"),
  /** 葡萄牙语. */
  PT("pt"),
  /** 俄语. */
  RU("ru"),
  /** 泰文. */
  TH("th"),
  /** 越南语. */
  VI("vi"),
  /** 阿拉伯语. */
  AR("ar"),
  /** 北印度. */
  HI("hi"),
  /** 希伯来语. */
  HE("he"),
  /** 土耳其语. */
  TR("tr"),
  /** 德语. */
  DE("de"),
  /** 法语. */
  FR("fr");

  private String text;

  private Language(String text) {
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
