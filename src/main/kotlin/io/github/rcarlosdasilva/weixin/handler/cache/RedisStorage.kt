package io.github.rcarlosdasilva.weixin.handler.cache

import com.google.common.base.Joiner
import io.github.rcarlosdasilva.weixin.handler.*
import io.github.rcarlosdasilva.weixin.terms.DEFAULT_REDIS_KEY_PATTERN
import io.github.rcarlosdasilva.weixin.terms.DEFAULT_REDIS_KEY_PREFIX
import io.github.rcarlosdasilva.weixin.terms.DEFAULT_REDIS_KEY_SEPARATOR
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SessionCallback
import org.springframework.util.SerializationUtils.serialize
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol
import java.util.*
import java.util.concurrent.TimeUnit

private const val LOCKER_NAME_SUFFIX = "__lock"

/**
 * Redis两种实现，Jedis与SpringRedisTemplate
 */

// ------------------------ Jedis Implements ---------------------------

/**
 * Jedis缓存器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class JedisStorage<V : Cacheable> : CacheStorage<V> {

  private lateinit var group: String
  private val keyPattern by lazy { fullKey(group, DEFAULT_REDIS_KEY_PATTERN) }
  private lateinit var pool: JedisPool
  private val jedis: Jedis
    @Synchronized get() = pool.resource

  override fun initialize(config: Map<String, Any>) {
    group = config[CONFIG_GROUP_KEY]!! as String

    // TODO 获取配置
    val redisSetting = JedisSetting()
    pool = JedisPool(
        redisSetting.config,
        redisSetting.host,
        redisSetting.port,
        redisSetting.timeout,
        redisSetting.password,
        redisSetting.database,
        redisSetting.useSsl
    )
  }

  override fun keys(): List<String> = jedis.run {
    val keys = keys(keyPattern)
    close()
    keys.toList()
  }

  override fun size(): Long = keys().size.toLong()

  override fun clear() = jedis.run {
    keys(keyPattern).forEach { del(it) }
    close()
  }

  override fun exists(key: String): Boolean = jedis.run {
    val exists = exists(fullKey(group, key).toByteArray())
    close()
    return exists
  }

  override fun get(key: String): V? = jedis.run {
    val value = get(fullKey(group, key).toByteArray())
    close()
    return unserialize(value)
  }

  override fun put(key: String, value: V): V? = jedis.run {
    val oldValue = this@JedisStorage.get(key)
    set(fullKey(group, key).toByteArray(), serialize(value))
    close()
    oldValue
  }

  override fun put(key: String, value: V, timeout: Int): V? = jedis.run {
    val oldValue = this@JedisStorage.get(key)
    setex(fullKey(group, key).toByteArray(), timeout, serialize(value))
    close()
    oldValue
  }

  override fun remove(key: String) = jedis.run {
    del(fullKey(group, key))!!
    close()
  }

  override fun lookup(lookup: Lookup<V>): V? = jedis.run {
    val key = keys().firstOrNull { k ->
      val v = get(k.toByteArray()).let { unserialize<V>(it) }
      v?.let { lookup.isYou(k, v) } ?: false
    }
    close()
    key?.let { this@JedisStorage.get(it) }
  }

  override fun lookupAll(lookup: Lookup<V>): Map<String, V> = jedis.run {
    val result = keys().associateNotNull { k ->
      val v = get(k.toByteArray()).let { unserialize<V>(it) }
      v?.let {
        if (lookup.isYou(k, v)) {
          k to v
        } else null
      }
    }
    close()
    result
  }

  override fun lock(key: String, timeout: Long, promptly: Boolean): String? = jedis.run {
    val fullKey = fullKey(group, key) + LOCKER_NAME_SUFFIX
    val identifier = UUID.randomUUID().toString()
    var lastTtl: Long = -1

    while (!Thread.currentThread().isInterrupted) {
      if (setnx(fullKey, identifier) == 1L) {
        pexpire(fullKey, timeout)
        // 正常获取锁
        break
      }

      val ttl = pttl(fullKey)
      lastTtl = ttl.takeIf { lastTtl < 0 } ?: lastTtl

      if (ttl == -1L) {
        // 没设置过期时间
        pexpire(fullKey, timeout)
      } else if (ttl == -2L) {
        // 神奇，万一setnx之后到这里key过期了呢
        continue
      } else {
        // 每次获取ttl肯定是越来越小，如果突然变大了，只能说明，有另一个贱人比你提前获取到锁，那就不惜当等了
        if (lastTtl in 1 until ttl) {
          close()
          return null
        }
        lastTtl = ttl
      }

      if (promptly) {
        close()
        return null
      }

      try {
        Thread.sleep(10)
      } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
      }

    }

    close()
    identifier
  }

  override fun unlock(key: String, identifier: String): Boolean = jedis.run {
    val fullKey = fullKey(group, key) + LOCKER_NAME_SUFFIX

    while (true) {
      // 监视lock，准备开始事务
      watch(fullKey)
      // 看看是不是自己的锁
      if (identifier == get(fullKey)) {
        val transaction = multi()
        transaction.del(fullKey)
        transaction.exec() ?: continue
        close()
        return true
      }
      unwatch()
      break
    }

    close()
    return false
  }

}

class JedisSetting {

  @Transient
  var config = JedisPoolConfig()
  var host = Protocol.DEFAULT_HOST
  var port = Protocol.DEFAULT_PORT
  var timeout = Protocol.DEFAULT_TIMEOUT
  var password: String? = null
  var database = Protocol.DEFAULT_DATABASE
  var useSsl = false

}

// ------------------------ SpringRedisTemplate Implements ---------------------------

/**
 * SpringRedisTemplate缓存器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
const val CONFIG_SPRING_REDIS_TEMPLATE_KEY = "CONFIG_SPRING_REDIS_TEMPLATE_KEY"

@Suppress("UNCHECKED_CAST")
class SpringRedisStorage<V : Cacheable> : CacheStorage<V> {

  private lateinit var group: String
  private val keyPattern by lazy { fullKey(group, DEFAULT_REDIS_KEY_PATTERN) }
  private lateinit var redisTemplate: RedisTemplate<String, Any>

  override fun initialize(config: Map<String, Any>) {
    group = config[CONFIG_GROUP_KEY]!! as String
    redisTemplate = config[CONFIG_SPRING_REDIS_TEMPLATE_KEY]!! as RedisTemplate<String, Any>
  }

  override fun keys(): List<String> = redisTemplate.keys(keyPattern).toList()

  override fun size(): Long = keys().size.toLong()

  override fun clear() = redisTemplate.delete(keys())

  override fun exists(key: String): Boolean = redisTemplate.hasKey(fullKey(group, key))

  override fun get(key: String): V? = redisTemplate.opsForValue().get(fullKey(group, key)) as V

  override fun put(key: String, value: V): V? {
    val oldValue = get(key)
    redisTemplate.opsForValue().set(key, value)
    return oldValue
  }

  override fun put(key: String, value: V, timeout: Int): V? {
    val oldValue = get(key)
    redisTemplate.opsForValue().set(key, value, timeout.toLong(), TimeUnit.SECONDS)
    return oldValue
  }

  override fun remove(key: String) = redisTemplate.delete(key)

  override fun lookup(lookup: Lookup<V>): V? = keys().firstOrNull { key ->
    redisTemplate.opsForValue().get(key)?.let { value ->
      lookup.isYou(key, value as V)
    } ?: false
  }?.let { get(it) }

  override fun lookupAll(lookup: Lookup<V>): Map<String, V> = keys().associateNotNull { key ->
    redisTemplate.opsForValue().get(key)?.let { value ->
      if (lookup.isYou(key, value as V)) {
        key to value
      } else null
    }
  }

  override fun lock(key: String, timeout: Long, promptly: Boolean): String? {
    val fullKey = fullKey(group, key) + LOCKER_NAME_SUFFIX
    val identifier = UUID.randomUUID().toString()
    var lastTtl: Long = -1

    while (!Thread.currentThread().isInterrupted) {
      if (redisTemplate.opsForValue().setIfAbsent(fullKey, identifier)) {
        redisTemplate.expire(fullKey, timeout, TimeUnit.MILLISECONDS)
        // 正常获取锁
        break
      }

      val ttl = redisTemplate.getExpire(fullKey, TimeUnit.MILLISECONDS)
      lastTtl = ttl.takeIf { lastTtl < 0 } ?: lastTtl

      if (ttl == -1L) {
        // 没设置过期时间
        redisTemplate.expire(fullKey, timeout, TimeUnit.MILLISECONDS)
      } else if (ttl == -2L) {
        // 神奇，万一setnx之后到这里key过期了呢
        continue
      } else {
        // 每次获取ttl肯定是越来越小，如果突然变大了，只能说明，有另一个贱人比你提前获取到锁，那就不惜当等了
        if (lastTtl in 1 until ttl) {
          return null
        }
        lastTtl = ttl
      }

      if (promptly) {
        return null
      }

      try {
        Thread.sleep(10)
      } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
      }
    }

    return identifier
  }

  override fun unlock(key: String, identifier: String): Boolean {
    val fullKey = fullKey(group, key) + LOCKER_NAME_SUFFIX

    while (true) {
      // 监视lock，准备开始事务
      redisTemplate.watch(fullKey)
      // 看看是不是自己的锁
      val obj = redisTemplate.opsForValue().get(fullKey)

      if (identifier == obj.toString()) {
        val sessionCallback = object : SessionCallback<Any> {
          override fun <K : Any?, V : Any?> execute(operations: RedisOperations<K, V>): Any? {
            operations.multi()
            operations.delete(fullKey as K)
            return operations.exec()
          }
        }

        redisTemplate.execute(sessionCallback)
        return true
      }
      redisTemplate.unwatch()
      break
    }

    return false
  }
}

// ---------------------- 通用方法 --------------------------

private val JOINER = Joiner.on(DEFAULT_REDIS_KEY_SEPARATOR)
//private val SPLITTER = Splitter.on(DEFAULT_REDIS_KEY_SEPARATOR)

internal fun fullKey(group: String, shortKey: String): String = JOINER.join(DEFAULT_REDIS_KEY_PREFIX, group, shortKey)

//internal fun shortKey(fullKey: String): String = SPLITTER.splitToList(fullKey).last()

internal inline fun <T, K, V> Iterable<T>.associateNotNull(transform: (T) -> Pair<K, V>?): Map<K, V> {
  val destination = mutableMapOf<K, V>()
  for (element in this) {
    transform(element)?.run {
      destination += this
    }
  }
  return destination.toMap()
}