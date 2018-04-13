package io.github.rcarlosdasilva.weixin.handler

import org.junit.Assert
import org.junit.Test
import java.io.IOException

class HttpHandlerTest {

  @Test
  fun testRequest() {
    val url = "https://api.weixin.qq.com/cgi-bin/getcallbackip"
    val response = HttpHandler.request(url, HttpMethod.GET, "", ContentType.JSON)
    Assert.assertNotNull(response)
  }

  @Test
  @Throws(IOException::class)
  fun testRequestStream() {
    val url = "https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png"
    val stream = HttpHandler.requestStream(url, HttpMethod.GET, "", ContentType.PNG)
    Assert.assertNotNull(stream)
    Assert.assertTrue(stream.read() >= 0)
    stream.close()
  }

}