package io.github.rcarlosdasilva.weixin.api.internal;

/**
 * 相关工具
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface HelperApi {

  /**
   * 判断ip是否是可信任的微信ip.
   * 
   * @param ip
   *          ip地址
   * @return 是否合法
   * @see CommonApi#getWeixinIps()
   */
  boolean isLegalRequestIp(String ip);

  /**
   * 判断当前公众号配置是否可用.
   * 
   * @return 是否可用
   */
  boolean isUsable();

}
