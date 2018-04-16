package io.github.rcarlosdasilva.weixin.handler

import io.github.rcarlosdasilva.weixin.core.Weixin
import io.github.rcarlosdasilva.weixin.model.AccessToken
import io.github.rcarlosdasilva.weixin.model.JsapiTicket
import io.github.rcarlosdasilva.weixin.terms.DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN
import kotlin.concurrent.thread

const val INSPECTOR_DISPATCHER_THREAD_NAME = "t_weixin_inspector_dispatch"
const val INSPECTOR_WORKING_LOCK_NAME = "inspector_working"

/**
 * 分布式巡查调度器，用做对AccessToken等凭证主动做过期验证。需要手动启动
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class InspectDispatcher private constructor() {

  private val interval = Weixin.registry.setting.inspectDispatchIntervalInMill
  private val inspectors: List<Inspector> = listOf(AccessTokenInspector(), JsapiTicketInspector())
  private val workers: List<Worker> = listOf(AccessTokenWorker(), JsapiTicketWorker())

  fun start() {
    while (!Thread.currentThread().isInterrupted) {
      for (inspector in inspectors) {
        val problem = inspector.inspect() ?: continue

        for (worker in workers) {
          if (worker.support(problem)) {
            worker.run(problem)
          }
        }
      }

      try {
        Thread.sleep(interval)
      } catch (ex: InterruptedException) {
        Thread.currentThread().interrupt()
      }
    }
  }

  companion object {
    fun startup() {
      thread(start = true, isDaemon = true, name = INSPECTOR_DISPATCHER_THREAD_NAME) { InspectDispatcher().start() }
    }
  }

}

interface Inspector {
  fun inspect(): Problem?
}

interface Worker {
  fun support(problem: Problem): Boolean
  fun run(problem: Problem)
}

data class Problem(val type: Class<*>, val elements: Collection<Any>)


class AccessTokenInspector : Inspector {

  override fun inspect(): Problem? {
    val results = CacheHandler.of(AccessToken::class.java).lookupAll(object : Lookup<AccessToken> {
      override fun isYou(key: String, obj: AccessToken): Boolean = obj.isExpired
    }).map { it.value }

    if (results.isEmpty()) {
      return null
    }

    return Problem(AccessToken::class.java, results)
  }

}

class AccessTokenWorker : Worker {

  override fun support(problem: Problem): Boolean =
    AccessToken::class.java == problem.type

  override fun run(problem: Problem) {
    thread(start = true, isDaemon = true) {
      val identifier = CacheHandler.of(AccessToken::class.java).lock(INSPECTOR_WORKING_LOCK_NAME, 2000, true)
      if (identifier != null) {
        problem.elements.forEach {
          val accessToken = it as AccessToken
          val key = accessToken.accountKey!!

          if (DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN == key) {
            Weixin.op().authentication.askAccessToken()
          } else {
            Weixin.mp(key).authentication.askAccessToken()
          }
        }
        CacheHandler.of(AccessToken::class.java).unlock(INSPECTOR_WORKING_LOCK_NAME, identifier)
      }
    }
  }

}


class JsapiTicketInspector : Inspector {

  override fun inspect(): Problem? {
    val results = CacheHandler.of(JsapiTicket::class.java).lookupAll(object : Lookup<JsapiTicket> {
      override fun isYou(key: String, obj: JsapiTicket): Boolean = obj.isExpired
    }).map { it.value }

    if (results.isEmpty()) {
      return null
    }

    return Problem(JsapiTicket::class.java, results)
  }

}

class JsapiTicketWorker : Worker {

  override fun support(problem: Problem): Boolean =
    JsapiTicket::class.java == problem.type

  override fun run(problem: Problem) {
    thread(start = true, isDaemon = true) {
      val identifier = CacheHandler.of(JsapiTicket::class.java).lock(INSPECTOR_WORKING_LOCK_NAME, 2000, true)
      if (identifier != null) {
        problem.elements.forEach {
          val jsapiTicket = it as JsapiTicket
          val key = jsapiTicket.accountKey!!

          Weixin.mp(key).authentication.askAccessToken()
        }
        CacheHandler.of(JsapiTicket::class.java).unlock(INSPECTOR_WORKING_LOCK_NAME, identifier)
      }
    }
  }

}