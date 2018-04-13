package io.github.rcarlosdasilva.weixin.handler

import java.io.Serializable

/**
 * 指定特殊值（可缓存的类，放入缓存时的分组名）的字段名称
 */
const val CACHEABLE_CLASS_GROUP_MARK_FIELD_NAME = "GROUP_NAME"
const val CONFIG_GROUP_KEY = "group"

/**
 * 缓存处理
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
@Suppress("UNCHECKED_CAST")
object CacheHandler {

  @Suppress("UNCHECKED_CAST")
  private val cacheStorageClass: Class<*>  by lazy {
    // TODO 配置   获取缓存器类型
    Class.forName("io.github.rcarlosdasilva.weixin.handler.cache.JvmMapStorage")
  }
  /**
   * 针对不同缓存器实现的差异化配置
   */
  val config = mutableMapOf<String, Any>()
  private val storages = mutableMapOf<String, CacheStorage<*>>()

  /**
   * 对每一个实现[Cacheable]的对象，会生成一个缓存器
   */
  fun <V : Cacheable> of(clazz: Class<V>): CacheStorage<V> =
    storages[clazz.toString()] as CacheStorage<V>? ?: synchronized(storages) {
      newStorage(clazz).also { storages[clazz.toString()] = it }
    }

  private fun <V : Cacheable> newStorage(clazz: Class<V>): CacheStorage<V> =
    (cacheStorageClass.newInstance() as CacheStorage<V>).also {
      config[CONFIG_GROUP_KEY] = groupName(clazz)
      it.initialize(config)
    }

  private fun groupName(clazz: Class<*>): String =
    try {
      val field = clazz.getField(CACHEABLE_CLASS_GROUP_MARK_FIELD_NAME)
      field.get(clazz).toString()
    } catch (ex: Exception) {
      val clazzName = clazz.simpleName
      val length = clazzName.length
      val sb = StringBuilder()
      for (i in 0 until length) {
        var c = clazzName[i]
        if (c in 'a'..'z') {
          sb.append(c)
        } else if (c in 'A'..'Z') {
          if (i > 0) {
            sb.append('_')
          }
          c += 32
          sb.append(c)
        }
      }
      sb.toString()
    }

}

/**
 * 标记可缓存对象
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface Cacheable

class GeneralCacheableObject(var obj: Any?) : Serializable, Cacheable {
  companion object {
    private const val serialVersionUID = 6464095895660138441L
  }
}


/**
 * 缓存器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface CacheStorage<V : Cacheable> {

  /**
   * 针对不同缓存器的实现，做相应的初始化
   */
  fun initialize(config: Map<String, Any>)

  /**
   * 所有键
   */
  fun keys(): List<String>

  /**
   * 缓存大小
   */
  fun size(): Long

  /**
   * 清空
   */
  fun clear()

  /**
   * 是否存在
   */
  fun exists(key: String): Boolean

  /**
   * 获取
   */
  fun get(key: String): V?

  /**
   * 放入
   */
  fun put(key: String, value: V): V?

  /**
   * 放入
   */
  fun put(key: String, value: V, timeout: Int): V?

  /**
   * 移除
   */
  fun remove(key: String)

  /**
   * 返回查找到的第一个值
   */
  fun lookup(lookup: Lookup<V>): V?

  /**
   * 返回查找到的所有值
   */
  fun lookupAll(lookup: Lookup<V>): List<V>

  /**
   * 加锁，如不支持请返回空字符串，不要返回null
   *
   * @param key 与 {@link #put(String, Cacheable)}、 {@link #get(String)}等方法的key一样
   * @param timeout 锁时效（单位：毫秒）
   * @param promptly 不等待，为true时，获取不到锁，直接返回null。否则会不断去尝试获取锁，如果当前锁失效，但被其他线程获取到锁，则不再继续
   * @return 锁标识，解锁用，获取失败返回null
   */
  fun lock(key: String, timeout: Long, promptly: Boolean): String?

  /**
   * 解锁，如不支持请返回true
   *
   * @param key 键
   * @param identifier 锁标识，加锁时获取
   * @return 解锁是否成功
   */
  fun unlock(key: String, identifier: String): Boolean

}

interface Lookup<in V : Cacheable> {

  fun isYou(key: String, obj: V?): Boolean

}