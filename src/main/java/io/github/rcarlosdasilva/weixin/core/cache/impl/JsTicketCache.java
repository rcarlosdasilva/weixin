package io.github.rcarlosdasilva.weixin.core.cache.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.github.rcarlosdasilva.weixin.core.cache.AbstractCache;
import io.github.rcarlosdasilva.weixin.core.cache.Cache;
import io.github.rcarlosdasilva.weixin.model.response.certificate.JsTicketResponse;

/**
 * jsapi_ticket缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class JsTicketCache extends AbstractCache<JsTicketResponse> {

  private static Cache<JsTicketResponse> instance = new JsTicketCache();

  private final Map<String, JsTicketResponse> cache;

  private JsTicketCache() {
    cache = new ConcurrentHashMap<String, JsTicketResponse>();
  }

  public static Cache<JsTicketResponse> instance() {
    return instance;
  }

  @Override
  public Set<String> keys() {
    return cache.keySet();
  }

  @Override
  public JsTicketResponse get(String key) {
    return cache.get(key);
  }

  @Override
  public JsTicketResponse put(String key, JsTicketResponse value) {
    return cache.put(key, value);
  }

  @Override
  public JsTicketResponse remove(String key) {
    return cache.remove(key);
  }

}
