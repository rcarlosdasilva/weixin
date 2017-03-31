package io.github.rcarlosdasilva.weixin.core.cache.impl;

import io.github.rcarlosdasilva.weixin.model.response.certificate.JsTicketResponse;

/**
 * jsapi_ticket缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class JsTicketCacheHandler extends AbstractCacheHandler<JsTicketResponse> {

  private static final String DEFAULT_MARK = "js_ticket";
  private static final JsTicketCacheHandler instance = new JsTicketCacheHandler();

  private JsTicketCacheHandler() {
    this.mark = DEFAULT_MARK;
  }

  public static JsTicketCacheHandler getInstance() {
    return instance;
  }

}
