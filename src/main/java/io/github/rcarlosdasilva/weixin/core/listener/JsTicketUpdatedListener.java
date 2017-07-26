package io.github.rcarlosdasilva.weixin.core.listener;

/**
 * 公众号JsTicket更新监听器
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface JsTicketUpdatedListener extends WeixinListener {

  /**
   * js_ticket更新.
   * 
   * @param key
   *          注册时的key
   * @param appid
   *          appid
   * @param ticket
   *          调用微信JS接口的临时票据
   * @param expiredIn
   *          有效期
   */
  void updated(String key, String appid, String ticket, long expiredIn);

}
