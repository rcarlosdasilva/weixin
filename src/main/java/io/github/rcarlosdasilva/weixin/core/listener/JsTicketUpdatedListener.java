package io.github.rcarlosdasilva.weixin.core.listener;

public interface JsTicketUpdatedListener {

  void updated(String key, String appid, String ticket, long expiredIn);

}
