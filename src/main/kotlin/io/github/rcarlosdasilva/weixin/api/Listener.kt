package io.github.rcarlosdasilva.weixin.api

/**
 * 公众号AccessToken更新监听器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface MpAccessTokenUpdatedListener {

  /**
   * @param key 注册时的key
   * @param appid 公众号appid
   * @param token 凭证
   * @param expiredIn 凭证有效时间，单位：秒
   */
  fun updated(key: String, appid: String, token: String, expiredIn: Long)

}

/**
 * 公众号JsTicket更新监听器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface MpJsTicketUpdatedListener {

  /**
   * @param key 注册时的key
   * @param appid appid
   * @param ticket 调用微信JS接口的临时票据
   * @param expiredIn 有效期
   */
  fun updated(key: String, appid: String, ticket: String, expiredIn: Long)

}

/**
 * 开放平台AccessToken更新监听器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface OpAccessTokenUpdatedListener {

  /**
   * @param token 第三方平台access_token
   * @param expiredIn 有效期
   */
  fun updated(token: String, expiredIn: Long)

}

/**
 * 开放平台中授权方公众号AccessToken更新监听器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
interface OpLisensorMpAccessTokenUpdatedListener {

  /**
   * @param appid 授权方appid
   * @param token 授权方令牌
   * @param refresh 刷新令牌
   * @param expiredIn 有效期
   */
  fun updated(appid: String, token: String, refresh: String, expiredIn: Long)

}