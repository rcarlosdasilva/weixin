package io.github.rcarlosdasilva.weixin.core.cache.holder;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateHandler {

  private RedisTemplateHandler() {
    throw new IllegalStateException("RedisTemplateHandler class");
  }

  @SuppressWarnings("rawtypes")
  public static RedisTemplate redisTemplate = null;

}
