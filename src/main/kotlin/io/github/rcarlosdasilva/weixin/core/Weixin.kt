package io.github.rcarlosdasilva.weixin.core

import io.github.rcarlosdasilva.weixin.api.MpApiWrapper
import io.github.rcarlosdasilva.weixin.api.OpApiWrapper
import io.github.rcarlosdasilva.weixin.api.WeixinListener
import io.github.rcarlosdasilva.weixin.handler.CacheHandler
import io.github.rcarlosdasilva.weixin.handler.Lookup
import io.github.rcarlosdasilva.weixin.handler.cache.JedisSetting
import io.github.rcarlosdasilva.weixin.model.AccessToken
import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.Op
import mu.KotlinLogging

/**
 * 微信API调用入口
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Weixin private constructor() {

  private val logger = KotlinLogging.logger {}

  /**
   * 微信公众号（开放平台）注册入口
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  inner class Registry internal constructor() {

    var setting: Setting = Setting()
    private var hasOp = false
    private val listeners by lazy { mutableMapOf<String, WeixinListener>() }
    internal val mpApi = mutableMapOf<String, MpApiWrapper>()
    internal var opApi: OpApiWrapper? = null

    /**
     * 注册开放平台信息
     *
     * 注意：使用[checkin]方法注册的公众号必须满足下列条件，才会由开放平台代理调用微信API：
     * - [Mp.proxyWithOp] = true
     * - [Mp.refreshToken]有有效值
     *
     * 否则，[Mp]公众号必须提供[Mp.appId]和[Mp.appSecret]，通过公众平台调用微信API
     */
    fun checkin(op: Op) {
      CacheHandler.of(Op::class.java).put(op.key, op)
      opApi = OpApiWrapper(op)
      logger.info { "注册开放平台：[APPID: ${op.appId}]" }
      hasOp = true
    }

    /**
     * 注册一个公众号配置，建议使用开放平台授权方式管理公众号，key默认为[Mp.appId]
     *
     * 注意：在[Mp]中指明[Mp.proxyWithOp]变量，如果为true（默认）代表公众号是通过开放平台授权过来（必须包含authorizer_refresh_token），
     * 否则会直接使用[Mp.appId]和[Mp.appSecret]（可兼容使用）管理公众号API调用凭证
     */
    fun checkin(mp: Mp) {
      lookup(mp.key)?.run {
        remove(mp.key)
      }

      val verified = when {
        !mp.proxyWithOp && mp.appSecret?.isBlank() == true -> {
          logger.warn { "未找到公众号的app_secret，该公众号将不被注册" }
          false
        }
        mp.proxyWithOp && !hasOp -> {
          logger.warn { "未找到开放平台信息，但该公众号配置为使用开放平台，将不被注册" }
          false
        }
        mp.proxyWithOp && mp.refreshToken?.isBlank() == true -> {
          logger.warn { "未找到开放平台授权方的刷新令牌authorizer_refresh_token，该公众号将不被注册" }
          false
        }
        mp.mpId?.isBlank() == true -> {
          logger.warn { "公众号[appid=${mp.appId}]未设置Mp.mpId，当微信通知回调或其他操作的时候，不设置有可能会导致无法正确找到对应的公众号信息" }
          true
        }
        mp.appId == mp.key -> {
          logger.warn { "未设置Mp.key，将使用公众号的appid：[${mp.appId}]作为默认key" }
          true
        }
        else -> true
      }

      if (verified) {
        CacheHandler.of(Mp::class.java).put(mp.key, mp)
        mpApi[mp.key] = MpApiWrapper(mp)
        logger.info { "注册公众号：[KEY: ${mp.key}, APPID: ${mp.appId}]" }
      }
    }

    /**
     * 添加监听
     */
    fun checkin(listener: WeixinListener) {
      listeners[listener.javaClass.name] = listener
    }

    /**
     * 移除公众号
     */
    fun remove(key: String) {
      lookup(key)?.let {
        CacheHandler.of(Mp::class.java).remove(it.key)
        CacheHandler.of(AccessToken::class.java).remove(it.key)
        logger.info("注销公众号信息：[$key]")
      }
    }

    /**
     * 获取监听
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : WeixinListener> listener(classType: Class<T>): T? = listeners[classType.name] as T?

  }


  companion object {

    val registry = Weixin().Registry()

    /**
     * 公众号入口
     */
    @JvmStatic
    fun mp(key: String): MpApiWrapper = registry.mpApi[key]!!

    /**
     * 开放平台入口
     */
    @JvmStatic
    fun op() = registry.opApi!!

    /**
     * 通过注册时得key、appId或原始ID获取账号配置信息
     */
    @JvmStatic
    fun lookup(key: String): Mp? =
      CacheHandler.of(Mp::class.java).get(key) ?: CacheHandler.of(Mp::class.java).lookup(object : Lookup<Mp> {
        override fun isYou(key: String, obj: Mp): Boolean {
          return obj.let { key == it.appId || key == it.mpId }
        }
      })
  }
}

class Setting {

  /**
   * 接口请求失败后的重试次数
   */
  var retries = 2
  /**
   * 当调用api接口出错时，是否抛出异常，否则只打印日志。默认true
   */
  var isThrowException = true
  /**
   * 缓存器实现类，建议使用SpringRedisStorage。默认JvmMapStorage
   */
  var cacheClass = "io.github.rcarlosdasilva.weixin.handler.cache.JvmMapStorage"
  /**
   * （开放平台第三方平台下）是否在公众号管理员授权后，自动加载公众号的授权内容与公众号基本信息，建议开启。默认true
   */
  var autoLoadAuthorizedWeixinData = true
  /**
   * 轮询access_token等token是否过期（重新获取）的间隔时间，默认10秒
   */
  var inspectDispatchIntervalInMill = 10000L
  /**
   * cacheClass使用JedisStorage时的配置
   */
  var redisSetting: JedisSetting? = null

}