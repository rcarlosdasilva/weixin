package io.github.rcarlosdasilva.weixin.core.task;

import java.util.Set;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccessTokenCacheHandler;

public class AccessTokenChecker implements Runnable {

  @Override
  public void run() {
    Set<String> keys = AccessTokenCacheHandler.getInstance().keys();
    for (String key : keys) {
      check(key);
    }
  }

  private void check(String key) {
    Weixin.with(key).common().getWeixinIps();
  }

}
