package io.github.rcarlosdasilva.weixin.core.cache.impl;

import io.github.rcarlosdasilva.weixin.model.response.certificate.JsTicketResponse;

/**
 * jsapi_ticket缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class JsTicketCache extends AbstractCache<JsTicketResponse> {

  private static final String DEFAULT_MARK = "jtc_";
  private static final JsTicketCache instance = new JsTicketCache();

  private JsTicketCache() {
    this.mark = DEFAULT_MARK;
  }

  public static JsTicketCache getInstance() {
    return instance;
  }

}
