package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 卡券颜色
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum CardColor {

  /** Color010 - 浅绿 #63b359. */
  Color_010("Color010"),
  /** Color020 - 深绿 #2c9f67. */
  Color_020("Color020"),
  /** Color030 - 浅蓝 #509fc9. */
  Color_030("Color030"),
  /** Color040 - 深蓝 #5885cf. */
  Color_040("Color040"),
  /** Color050 - 紫色 #9062c0. */
  Color_050("Color050"),
  /** Color060 - 屎黄 #d09a45. */
  Color_060("Color060"),
  /** Color070 - 土黄 #e4b138. */
  Color_070("Color070"),
  /** Color080 - 淡橙 #ee903c. */
  Color_080("Color080"),
  /** Color081 - 深橙 #f08500. */
  Color_081("Color081"),
  /** Color082 - 翠绿 #a9d92d. */
  Color_082("Color082"),
  /** Color090 - 砖红 #dd6549. */
  Color_090("Color090"),
  /** Color100 - 猪血色A #cc463d. */
  Color_100("Color100"),
  /** Color101 - 猪血色B #cf3e36. */
  Color_101("Color101"),
  /** Color102 - 灰蓝 #5E6671. */
  Color_102("Color102");

  private String code;

  private CardColor(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return this.code;
  }

}
