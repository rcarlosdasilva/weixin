package io.github.rcarlosdasilva.weixin.handler.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.common.cache.CacheBuilder
import io.github.rcarlosdasilva.weixin.handler.CacheStorage
import io.github.rcarlosdasilva.weixin.handler.Cacheable
import io.github.rcarlosdasilva.weixin.handler.Lookup
import java.util.concurrent.TimeUnit
import com.github.benmanes.caffeine.cache.Cache as CaffeineCache
import com.google.common.cache.Cache as GuavaCache

const val EXPIRE_TIME_AFTER_WRITE = "EXPIRE_TIME_AFTER_WRITE"
const val EXPIRE_TIME_AFTER_LAST_ACCESS = "EXPIRE_TIME_AFTER_LAST_ACCESS"
const val MAX_SIZE = "MAX_SIZE"

/**
 * 基于JVM的缓存器，包含简单HashMap实现，与Guava和Caffeine实现
 */

//------------------------ JVM HashMap -----------------------

/**
 * JVM Map缓存器，不支持缓存超时
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class JvmMapStorage<V : Cacheable> : CacheStorage<V> {

  override fun initialize(config: Map<String, Any>) {}

  private val cache = mutableMapOf<String, V>()

  override fun keys(): List<String> = cache.keys.toList()

  override fun size(): Long = cache.size.toLong()

  override fun clear() = cache.clear()

  override fun exists(key: String): Boolean = cache.containsKey(key)

  override fun get(key: String): V? = cache[key]

  override fun put(key: String, value: V): V? = cache.put(key, value)

  override fun put(key: String, value: V, timeout: Int): V? = put(key, value)

  override fun remove(key: String) {
    cache.remove(key)
  }

  override fun lookup(lookup: Lookup<V>): V? =
      cache.entries.firstOrNull { lookup.isYou(it.key, it.value) }?.value

  override fun lookupAll(lookup: Lookup<V>): Map<String, V> =
      cache.filter { lookup.isYou(it.key, it.value) }

  /**
   * 暂不支持
   */
  override fun lock(key: String, timeout: Long, promptly: Boolean): String? = ""

  /**
   * 暂不支持
   */
  override fun unlock(key: String, identifier: String): Boolean = true

}

//------------------------ Guava Cache -----------------------

/**
 * 暂未实现
 */
class GuavaStorage<V : Cacheable> : CacheStorage<V> {

  private lateinit var cache: GuavaCache<String, V>

  override fun initialize(config: Map<String, Any>) {
    val builder = CacheBuilder.newBuilder()
    config[EXPIRE_TIME_AFTER_WRITE]?.run { builder.expireAfterWrite(this.toString().toLong(), TimeUnit.SECONDS) }
    config[EXPIRE_TIME_AFTER_LAST_ACCESS]?.run { builder.expireAfterAccess(this.toString().toLong(), TimeUnit.SECONDS) }
    config[MAX_SIZE]?.run { builder.maximumSize(this.toString().toLong()) }
    cache = builder.build()
  }

  override fun keys(): List<String> = cache.asMap().keys.toList()

  override fun size(): Long = cache.size()

  override fun clear() = cache.invalidateAll()

  override fun exists(key: String): Boolean = cache.getIfPresent(key) != null

  override fun get(key: String): V? = cache.getIfPresent(key)

  override fun put(key: String, value: V): V? {
    val oldValue = get(key)
    cache.put(key, value)
    return oldValue
  }

  override fun put(key: String, value: V, timeout: Int): V? = put(key, value)

  override fun remove(key: String) = cache.invalidate(key)

  override fun lookup(lookup: Lookup<V>): V? =
      cache.asMap().toList().firstOrNull { lookup.isYou(it.first, it.second) }?.second

  override fun lookupAll(lookup: Lookup<V>): Map<String, V> =
      cache.asMap().filter { lookup.isYou(it.key, it.value) }

  /**
   * 暂不支持
   */
  override fun lock(key: String, timeout: Long, promptly: Boolean): String? = ""

  /**
   * 暂不支持
   */
  override fun unlock(key: String, identifier: String): Boolean = true

}

//------------------------ Caffeine Cache -----------------------

/**
 * 暂未实现
 */
class CaffeineStorage<V : Cacheable> : CacheStorage<V> {

  private lateinit var cache: CaffeineCache<String, V>

  override fun initialize(config: Map<String, Any>) {
    val builder = Caffeine.newBuilder()
    config[EXPIRE_TIME_AFTER_WRITE]?.run { builder.expireAfterWrite(this.toString().toLong(), TimeUnit.SECONDS) }
    config[EXPIRE_TIME_AFTER_LAST_ACCESS]?.run { builder.expireAfterAccess(this.toString().toLong(), TimeUnit.SECONDS) }
    config[MAX_SIZE]?.run { builder.maximumSize(this.toString().toLong()) }
    cache = builder.build()
  }

  override fun keys(): List<String> = cache.asMap().keys.toList()

  override fun size(): Long = cache.estimatedSize()

  override fun clear() = cache.invalidateAll()

  override fun exists(key: String): Boolean = cache.getIfPresent(key) != null

  override fun get(key: String): V? = cache.getIfPresent(key)

  override fun put(key: String, value: V): V? {
    val oldValue = get(key)
    cache.put(key, value)
    return oldValue
  }

  override fun put(key: String, value: V, timeout: Int): V? = put(key, value)

  override fun remove(key: String) = cache.invalidate(key)

  override fun lookup(lookup: Lookup<V>): V? =
      cache.asMap().toList().firstOrNull { lookup.isYou(it.first, it.second) }?.second

  override fun lookupAll(lookup: Lookup<V>): Map<String, V> =
      cache.asMap().filter { lookup.isYou(it.key, it.value) }

  /**
   * 暂不支持
   */
  override fun lock(key: String, timeout: Long, promptly: Boolean): String? = ""

  /**
   * 暂不支持
   */
  override fun unlock(key: String, identifier: String): Boolean = true

}
