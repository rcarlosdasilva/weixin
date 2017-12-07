package io.github.rcarlosdasilva.weixin.core.cache.storage.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;

import io.github.rcarlosdasilva.weixin.common.Convention;

public class RedisKey {

  private static final Joiner JOINER = Joiner.on(Convention.DEFAULT_REDIS_KEY_SEPARATOR);
  private static final Splitter SPLITTER = Splitter.on(Convention.DEFAULT_REDIS_KEY_SEPARATOR);

  private RedisKey() {
    throw new IllegalStateException("RedisKey class");
  }

  public static String fullKey(final String group, final String shortKey) {
    return JOINER.join(Convention.DEFAULT_REDIS_KEY_PREFIX, group, shortKey);
  }

  public static String shortKey(final String fullKey) {
    if (!Strings.isNullOrEmpty(fullKey)) {
      List<String> parts = SPLITTER.splitToList(fullKey);
      if (parts != null && !parts.isEmpty()) {
        return parts.get(parts.size() - 1);
      }
    }
    return "";
  }

  public static Collection<String> shortKeys(final Collection<String> redisKeys) {
    if (redisKeys == null) {
      return Collections.emptyList();
    }

    return Collections2.transform(redisKeys, new Function<String, String>() {

      @Override
      public String apply(String input) {
        return shortKey(input);
      }

    });
  }

}
