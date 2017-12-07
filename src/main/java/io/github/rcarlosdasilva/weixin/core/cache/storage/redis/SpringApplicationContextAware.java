package io.github.rcarlosdasilva.weixin.core.cache.storage.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContextAware implements ApplicationContextAware {

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    @SuppressWarnings("rawtypes")
    RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
    if (redisTemplate != null) {
      RedisHandler.setRedisTemplate(redisTemplate);
    }
  }

}
