package io.github.rcarlosdasilva.weixin.core.cache;

/**
 * 缓存
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 * @param <T>
 *          缓存类型
 */
public interface Cache<T> {

  /**
   * 获取.
   * 
   * @param key
   *          键
   * @return 值
   */
  T get(String key);

  /**
   * 放入.
   * 
   * @param key
   *          键
   * @param object
   *          值
   * @return 值
   */
  T put(String key, T object);

  /**
   * 移除.
   * 
   * @param key
   *          键
   * @return 值
   */
  T remove(String key);

  /**
   * 查找.
   * 
   * @param value
   *          值
   * @return 值
   */
  T lookup(Object value);

}
