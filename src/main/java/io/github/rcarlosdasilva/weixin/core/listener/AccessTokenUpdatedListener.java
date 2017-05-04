package io.github.rcarlosdasilva.weixin.core.listener;

public interface AccessTokenUpdatedListener {

  void updated(String key, String appid, String token, long expiredIn);

}
