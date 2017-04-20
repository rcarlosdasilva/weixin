package io.github.rcarlosdasilva.weixin.common.dictionary;

import com.google.gson.annotations.SerializedName;

/**
 * 语言
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public enum Language {

  /** 简体中文. */
  @SerializedName("zh_CN")
  ZH_CN("zh_CN"),
  /** 繁体中文TW. */
  @SerializedName("zh_TW")
  ZH_TW("zh_TW"),
  /** 繁体中文HK. */
  @SerializedName("zh_HK")
  ZH_HK("zh_HK"),
  /** 英文. */
  @SerializedName("en")
  EN_US("en"),
  /** 印尼语. */
  @SerializedName("id")
  ID("id"),
  /** 马来语. */
  @SerializedName("ms")
  MS("ms"),
  /** 西班牙语. */
  @SerializedName("es")
  ES("es"),
  /** 韩语. */
  @SerializedName("ko")
  KO("ko"),
  /** 意大利语. */
  @SerializedName("it")
  IT("it"),
  /** 日语. */
  @SerializedName("ja")
  JA("ja"),
  /** 波兰语. */
  @SerializedName("pl")
  PL("pl"),
  /** 葡萄牙语. */
  @SerializedName("pt")
  PT("pt"),
  /** 俄语. */
  @SerializedName("ru")
  RU("ru"),
  /** 泰文. */
  @SerializedName("th")
  TH("th"),
  /** 越南语. */
  @SerializedName("vi")
  VI("vi"),
  /** 阿拉伯语. */
  @SerializedName("ar")
  AR("ar"),
  /** 北印度. */
  @SerializedName("hi")
  HI("hi"),
  /** 希伯来语. */
  @SerializedName("he")
  HE("he"),
  /** 土耳其语. */
  @SerializedName("tr")
  TR("tr"),
  /** 德语. */
  @SerializedName("de")
  DE("de"),
  /** 法语. */
  @SerializedName("fr")
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
