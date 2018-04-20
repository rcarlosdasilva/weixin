package io.github.rcarlosdasilva.weixin.api;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaMpCommonalityTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private String key;

  @Before
  public void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  /**
   * 判断公众号是否可用
   */
  @Test
  public void test01() {
    boolean usable = Weixin.mp(key).getCommonality().isUsable();
    Assert.assertTrue(usable);
  }

  /**
   * ip是否合法的微信ip
   */
  @Test
  public void test02() {
    boolean bad = Weixin.mp(key).getCommonality().isLegalRequestIp("1.1.1.1");
    Assert.assertFalse(bad);

    boolean good = Weixin.mp(key).getCommonality().isLegalRequestIp("101.226.103.59");
    Assert.assertTrue(good);
  }

  /**
   * 短连接
   */
  @Test
  public void test03() {
    String url = Weixin.mp(key).getCommonality().getShortUrl("http://www.baidu.com");
    Assert.assertNotNull(url);
    Assert.assertTrue(url.startsWith("https://w.url.cn"));
  }

  /**
   * 重置接口调用次数
   */
  @Test
  public void test04() {
    boolean success = Weixin.mp(key).getCommonality().resetQuota();
    Assert.assertTrue(success);
  }

  /**
   * 永久二维码
   */
  @Test
  public void test05() {
    byte[] image = Weixin.mp(key).getCommonality().downloadUnlimitedQrImage("custom");
    Assert.assertNotNull(image);
  }

  /**
   * 临时二维码
   */
  @Test
  public void test06() {
    byte[] image = Weixin.mp(key).getCommonality().downloadTemporaryQrImage(3600, "custom");
    Assert.assertNotNull(image);
  }

}
