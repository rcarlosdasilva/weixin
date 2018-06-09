@file:Suppress("UNCHECKED_CAST")

package io.github.rcarlosdasilva.weixin.handler

import io.github.rcarlosdasilva.weixin.terms.DEFAULT_ENCODING
import java.io.*
import java.net.URLEncoder

fun <T> serialize(obj: T): ByteArray? =
    try {
      ByteArrayOutputStream().use { baos ->
        ObjectOutputStream(baos).use { oos ->
          oos.writeObject(obj)
          return baos.toByteArray()
        }
      }
    } catch (ex: Exception) {
      null
    }

fun <T> unserialize(bytes: ByteArray): T? =
    try {
      ObjectInputStream(ByteArrayInputStream(bytes)).use {
        return it.readObject() as T
      }
    } catch (ex: Exception) {
      null
    }


fun urlEncode(url: String): String? {
  try {
    return URLEncoder.encode(url, DEFAULT_ENCODING)
  } catch (ex: UnsupportedEncodingException) {
  }

  return null
}