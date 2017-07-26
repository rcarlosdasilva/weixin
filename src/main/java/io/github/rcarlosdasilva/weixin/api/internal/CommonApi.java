package io.github.rcarlosdasilva.weixin.api.internal;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.common.QrCodeCreateResponse;

/**
 * 公共API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface CommonApi {

  /**
   * 获取微信服务器IP地址.
   * 
   * <p>
   * 如果公众号基于消息接收安全上的考虑，需要获知微信服务器的IP地址列表，以便识别出哪些消息是微信官方推送给你的，
   * 哪些消息可能是他人伪造的，可以通过该接口获得微信服务器IP地址列表。
   * 
   * @return IP地址列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140187&token=&lang=zh_CN"
   *      >获取微信服务器IP地址</a>
   */
  List<String> getWeixinIps();

  /**
   * 将一条长链接转成短链接.
   * 
   * <p>
   * 主要使用场景：<br>
   * 开发者用于生成二维码的原链接（商品、支付二维码等）太长导致扫码速度和成功率下降，
   * 将原长链接通过此接口转成短链接再生成二维码将大大提升扫码速度和成功率。
   * 
   * @param url
   *          需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
   * @return 短链接
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433600&token=&lang=zh_CN"
   *      >长链接转短链接接口</a>
   */
  String getShortUrl(String url);

  /**
   * 生成带参数的临时二维码.
   * 
   * <p>
   * 临时二维码，是有过期时间的，最长可以设置为在二维码生成后的30天（即2592000秒）后过期，但能够生成较多数量。
   * 临时二维码主要用于帐号绑定等不要求二维码永久保存的业务场景
   * 
   * @param expireSeconds
   *          二维码过期时间，单位秒。最大不超过2592000（即30天），默认30秒
   * @param senceId
   *          场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return {@link QrCodeCreateResponse} 包含ticket和url两个有效值
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN"
   *      >生成带参数的二维码</a>
   */
  QrCodeCreateResponse createQrWithTemporary(long expireSeconds, int senceId);

  /**
   * 生成带参数的永久二维码.
   * 
   * <p>
   * 永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景。
   * 
   * @param senceId
   *          场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return {@link QrCodeCreateResponse} 包含ticket和url两个有效值
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN"
   *      >生成带参数的二维码</a>
   */
  QrCodeCreateResponse createQrWithUnlimited(int senceId);

  /**
   * 生成带参数的永久二维码.
   * 
   * <p>
   * 永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景。
   * 
   * @param senceString
   *          场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
   * @return {@link QrCodeCreateResponse} 包含ticket和url两个有效值
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN"
   *      >生成带参数的二维码</a>
   */
  QrCodeCreateResponse createQrWithUnlimited(String senceString);

  /**
   * 通过创建二维码结果获取图片.
   * 
   * @param qrResponse
   *          带参数的二维码创建结果
   * @return 图片文件流
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN"
   *      >生成带参数的二维码</a>
   */
  byte[] qrImage(QrCodeCreateResponse qrResponse);

  /**
   * 直接获取带参数的临时二维码.
   * 
   * <p>
   * 临时二维码，是有过期时间的，最长可以设置为在二维码生成后的30天（即2592000秒）后过期，但能够生成较多数量。
   * 临时二维码主要用于帐号绑定等不要求二维码永久保存的业务场景
   * 
   * @param expireSeconds
   *          二维码过期时间，单位秒。最大不超过2592000（即30天），默认30秒
   * @param senceId
   *          场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return 图片文件流
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN"
   *      >生成带参数的二维码</a>
   */
  byte[] qrImageWithTemporary(long expireSeconds, int senceId);

  /**
   * 直接获取带参数的永久二维码.
   * 
   * <p>
   * 永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景。
   * 
   * @param senceId
   *          场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return 图片文件流
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN"
   *      >生成带参数的二维码</a>
   */
  byte[] qrImageWithUnlimited(int senceId);

  /**
   * 直接获取带参数的永久二维码.
   * 
   * <p>
   * 永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。永久二维码主要用于适用于帐号绑定、用户来源统计等场景。
   * 
   * @param senceString
   *          场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段
   * @return 图片文件流
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542&token=&lang=zh_CN"
   *      >生成带参数的二维码</a>
   */
  byte[] qrImageWithUnlimited(String senceString);

}
