package io.github.rcarlosdasilva.weixin.handler.cache

import io.github.rcarlosdasilva.weixin.handler.CacheStorage
import io.github.rcarlosdasilva.weixin.handler.Cacheable
import io.github.rcarlosdasilva.weixin.handler.Lookup

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

  override fun size(): Int = cache.size

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

  override fun lookupAll(lookup: Lookup<V>): List<V> =
    cache.entries.filter { lookup.isYou(it.key, it.value) }.map { it.value }

  /**
   * 暂不支持
   */
  override fun lock(key: String, timeout: Long, promptly: Boolean): String? = null

  /**
   * 暂不支持
   */
  override fun unlock(key: String, identifier: String): Boolean = false

}

//------------------------ Guava Cache -----------------------

/**
 * 暂未实现
 */
class GuavaStorage<V : Cacheable> : CacheStorage<V> {

  override fun initialize(config: Map<String, Any>) {
  }

  override fun keys(): List<String> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun size(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun clear() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun exists(key: String): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun get(key: String): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun put(key: String, value: V): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun put(key: String, value: V, timeout: Int): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun remove(key: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun lookup(lookup: Lookup<V>): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun lookupAll(lookup: Lookup<V>): List<V> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun lock(key: String, timeout: Long, promptly: Boolean): String? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun unlock(key: String, identifier: String): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}

//------------------------ Caffeine Cache -----------------------

/**
 * 暂未实现
 */
class CaffeineStorage<V : Cacheable> : CacheStorage<V> {

  override fun initialize(config: Map<String, Any>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun keys(): List<String> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun size(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun clear() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun exists(key: String): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun get(key: String): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun put(key: String, value: V): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun put(key: String, value: V, timeout: Int): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun remove(key: String) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun lookup(lookup: Lookup<V>): V? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun lookupAll(lookup: Lookup<V>): List<V> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun lock(key: String, timeout: Long, promptly: Boolean): String? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun unlock(key: String, identifier: String): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}
