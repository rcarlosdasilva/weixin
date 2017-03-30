package io.github.rcarlosdasilva.weixin.core.cache.impl.holder;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateHandler {

  public static RedisTemplate<byte[], Object> redisTemplate;

}
