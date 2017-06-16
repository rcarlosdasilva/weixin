package io.github.rcarlosdasilva.weixin.api.internal;

/**
 * 相关工具
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface HelperApi {

  /**
   * 公众号调用接口调用次数清零API.
   * <p>
   * 请注意：<br>
   * 1、每个公众号每个月有10次清零机会，包括在微信公众平台上的清零以及调用API进行清零<br>
   * 2、第三方代公众号调用，实际上消耗的是公众号的清零quota
   * 
   * @return 如果是超出清零的请求次数限制返回false
   */
  boolean resetQuota();

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
