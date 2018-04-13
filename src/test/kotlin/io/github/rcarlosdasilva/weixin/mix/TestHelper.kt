package io.github.rcarlosdasilva.weixin.mix

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

}