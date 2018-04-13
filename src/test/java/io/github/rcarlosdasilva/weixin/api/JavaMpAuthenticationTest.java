package io.github.rcarlosdasilva.weixin.api;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.JsapiSignature;
import io.github.rcarlosdasilva.weixin.model.Mp;
import io.github.rcarlosdasilva.weixin.terms.data.WebAuthorizeScope;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaMpAuthenticationTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private String key;

  @Before
  public void before() {
    String appid = TestHelper.INSTANCE.get("mp.appid");
    String appsecret = TestHelper.INSTANCE.get("mp.appsecret");
    key = TestHelper.INSTANCE.get("mp.key");
    Mp mp = new Mp(appid, appsecret);
    mp.setKey(key);
    Weixin.Companion.getRegistry().checkin(mp);
  }

  /**
   * 基本授权access_token
   */
  @Test
  public void test01() {
    String at1 = Weixin.mp(key).getAuthentication().askAccessToken();
    Assert.assertNotNull(at1);
    logger.info("第一次从微信获取：{}", at1);

    String at2 = Weixin.mp(key).getAuthentication().askAccessToken();
    Assert.assertEquals(at1, at2);
    logger.info("第二次从缓存获取：{}", at2);

    Weixin.mp(key).getAuthentication().updateAccessToken("ct", System.currentTimeMillis() + 10000);
    String atc = Weixin.mp(key).getAuthentication().askAccessToken();
    Assert.assertEquals("ct", atc);
    logger.info("自定义token：{}", atc);

    Weixin.mp(key).getAuthentication().refreshAccessToken();
    String at3 = Weixin.mp(key).getAuthentication().askAccessToken();
    Assert.assertNotEquals(at1, at3);
    logger.info("第三次从微信刷新获取：{}", at3);
  }

  /**
   * 获取jsapi_ticket，以及签名
   */
  @Test
  public void test02() {
    String aj1 = Weixin.mp(key).getAuthentication().askJsapiTicket();
    Assert.assertNotNull(aj1);
    logger.info("第一次从微信获取：{}", aj1);

    String aj2 = Weixin.mp(key).getAuthentication().askJsapiTicket();
    Assert.assertEquals(aj1, aj2);
    logger.info("第二次从缓存获取：{}", aj2);

    Weixin.mp(key).getAuthentication().updateJsapiTicket("cj", System.currentTimeMillis() + 10000);
    String ajc = Weixin.mp(key).getAuthentication().askJsapiTicket();
    Assert.assertEquals("cj", ajc);
    logger.info("自定义ticket：{}", ajc);

    Weixin.mp(key).getAuthentication().refreshJsapiTicket();
    String aj3 = Weixin.mp(key).getAuthentication().askJsapiTicket();
    Assert.assertEquals(aj1, aj3);
    logger.info("第三次从微信刷新获取（jsapi_ticket重复请求返回值不变）：{}", aj3);

    JsapiSignature sign = Weixin.mp(key).getAuthentication().generateJsapiSignature("https://www.exampleurl.com");
    Assert.assertNotNull(sign);
    logger.info("签名：{}", sign.getSignature());
  }

  /**
   * 网页授权连接生成
   */
  @Test
  public void test03() {
    String url = Weixin.mp(key).getAuthentication().webAuthorize(WebAuthorizeScope.BASE, "https://www.exampleurl.com");
    Assert.assertNotNull(url);
    logger.info("网页授权连接：{}", url);
  }

}
