package io.github.rcarlosdasilva.weixin.core.cache;

import java.util.Collection;

/**
 * 缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface CacheHandler<V> {

  /**
   * 所有键.
   * 
   * @return 键
   */
  Collection<String> keys();

  /**
   * 缓存大小
   * 
   * @return int
   */
  int size();

  /**
   * 清空
   */
  void clear();

  /**
   * 获取.
   * 
   * @param key
   *          键
   * @return 值
   */
  V get(final String key);

  /**
   * 放入.
   * 
   * @param key
   *          键
   * @param object
   *          值
   * @return 值
   */
  V put(final String key, final V object);

  /**
   * 移除.
   * 
   * @param key
   *          键
   * @return 值
   */
  V remove(final String key);

  /**
   * 查找.
   * 
   * @param value
   *          值
   * @return boolean
   */
  String lookup(final V value);

}
