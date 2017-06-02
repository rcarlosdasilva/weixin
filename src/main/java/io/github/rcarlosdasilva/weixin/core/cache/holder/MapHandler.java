package io.github.rcarlosdasilva.weixin.core.cache.holder;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class MapHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(MapHandler.class);

  private static Map<String, Map<String, Object>> maps = Maps.newConcurrentMap();

  private MapHandler() {
  }

  @SuppressWarnings("unchecked")
  public static <V> Map<String, V> getObject(final String key) {
    Map<String, Object> tmp = maps.get(key);
    Map<String, V> map;
    if (tmp == null) {
      synchronized (MapHandler.class) {
        map = Maps.newConcurrentMap();
        maps.put(key, (Map<String, Object>) map);
        tmp = maps.get(key);
      }
    }

    try {
      map = (Map<String, V>) tmp;
    } catch (ClassCastException ex) {
      LOGGER.error("weixin map handler", ex);
      map = null;
    }

    return map;
  }

}
