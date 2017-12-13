package io.github.rcarlosdasilva.weixin.core.cache;

import java.util.Collection;
import java.util.List;

public interface CacheStorage<V extends Cacheable> {

  static final String LOCKER_NAME_SUFFIX = "__lock";

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
   * 清空.
   */
  void clear();

  /**
   * 是否存在.
   * 
   * @param key
   *          键
   * @return boolean
   */
  boolean exists(final String key);

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
   * 放入.
   * 
   * @param key
   *          键
   * @param object
   *          值
   * @param timeout
   *          过期时间（单位：秒）
   * @return 值
   */
  V put(final String key, final V object, final int timeout);

  /**
   * 移除.
   * 
   * @param key
   *          键
   * @return 键不存在时，为false
   */
  boolean remove(final String key);

  /**
   * 查找.
   * <p>
   * 返回查找到的第一个值
   * 
   * @param value
   *          值
   * @return boolean
   */
  V lookup(Lookup<V> lookup);

  /**
   * 查找全部.
   * <p>
   * 返回查找到的所有值
   * 
   * @param value
   *          值
   * @return boolean
   */
  List<V> lookupAll(Lookup<V> lookup);

  /**
   * 加锁.
   * 
   * @param key
   *          与 {@link #put(String, Cacheable)}、 {@link #get(String)}等方法的key一样
   * @param timeout
   *          锁时效（单位：毫秒）
   * @param noWait
   *          不等待，为true时，获取不到锁，直接返回null。否则会不断去尝试获取锁，如果当前锁失效，但被其他线程获取到锁，则不再继续
   * @return 锁标识，解锁用，获取失败返回null
   */
  String lock(final String key, final long timeout, final boolean noWait);

  /**
   * 解锁.
   * 
   * @param key
   *          键
   * @param identifier
   *          锁标识，加锁时获取
   * @return 解锁是否成功
   */
  boolean unlock(final String key, final String identifier);

}
