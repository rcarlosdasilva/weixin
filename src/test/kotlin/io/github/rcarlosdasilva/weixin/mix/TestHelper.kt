package io.github.rcarlosdasilva.weixin.mix

import io.github.rcarlosdasilva.weixin.core.Weixin
import io.github.rcarlosdasilva.weixin.model.Mp
import java.util.*

object TestHelper {

  private val prop by lazy {
    val `is` = ClassLoader.getSystemResourceAsStream("account.properties")
    `is`.use {
      Properties().apply {
        load(it)
      }
    }
  }

  fun get(key: String) = prop.getProperty(key)!!

  fun initSingle(): String {
    val appid = TestHelper.get("mp.appid")
    val appsecret = TestHelper.get("mp.appsecret")
    val key = TestHelper.get("mp.key")
    val mp = Mp(appid, appsecret)
    mp.key = key
    Weixin.registry.checkin(mp)
    return key
  }

}