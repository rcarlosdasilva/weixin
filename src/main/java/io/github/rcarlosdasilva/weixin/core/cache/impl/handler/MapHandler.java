package io.github.rcarlosdasilva.weixin.core.cache.impl.handler;

import java.util.Map;

import com.google.common.collect.Maps;

public class MapHandler {

  private static Map<String, Map<String, Object>> maps = Maps.newConcurrentMap();

  @SuppressWarnings("unchecked")
  public static <V> Map<String, V> getObject(final String key) {
    Map<String, Object> tmp = maps.get(key);
    Map<String, V> map;
    if (tmp == null) {
      synchronized (MapHandler.class) {
        if (tmp == null) {
          map = Maps.newConcurrentMap();
          maps.put(key, (Map<String, Object>) map);
          tmp = maps.get(key);
        }
      }
    }

    try {
      map = (Map<String, V>) tmp;
    } catch (ClassCastException e) {
      e.printStackTrace();
      map = null;
    }

    return map;
  }

}
