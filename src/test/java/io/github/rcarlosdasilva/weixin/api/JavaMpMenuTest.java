package io.github.rcarlosdasilva.weixin.api;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.builder.MenuBuilder;
import io.github.rcarlosdasilva.weixin.model.request.Menu;
import io.github.rcarlosdasilva.weixin.model.response.MenuCompleteResponse;
import io.github.rcarlosdasilva.weixin.model.response.MenuInfoResponse;
import io.github.rcarlosdasilva.weixin.terms.data.Language;
import io.github.rcarlosdasilva.weixin.terms.data.Sex;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaMpMenuTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static String key;

  @BeforeClass
  public static void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  /**
   * 创建自定义菜单
   */
  @Test
  public void test01() {
    Menu menu = new MenuBuilder().addRootClick("第一个", "一级菜单无子菜单").addRootWithSubButtons("第二个")
        .addClick("第一个", "二级菜单").addClick("第二个", "二级菜单").and().addRootView("第三个", "http://www.baidu.com")
        .build();
    boolean created = Weixin.mp(key).getMenu().create(menu);
    Assert.assertTrue(created);

    MenuInfoResponse response = Weixin.mp(key).getMenu().query();
    Assert.assertNotNull(response);
    Assert.assertNotNull(response.getMenu());
    Assert.assertNull(response.getConditionalMenu());
  }

  /**
   * 创建，删除个性化菜单
   */
  @Test
  public void test02() {
    Menu menu = new MenuBuilder().addRootClick("第一个", "一级菜单无子菜单").addRootWithSubButtons("第二个")
        .addClick("第一个", "二级菜单").addClick("第二个", "二级菜单").and().addRootView("第三个", "http://www.baidu.com")
        .withConditional().setSex(Sex.FEMALE).setLanguage(Language.ZH_CN).done().build();
    long menuId = Weixin.mp(key).getMenu().createWithConditional(menu);
    Assert.assertNotEquals(0, menuId);

    String openId = TestHelper.INSTANCE.get("openid.my");
    MenuInfoResponse response = Weixin.mp(key).getMenu().testWithConditional(openId);
    Assert.assertNotNull(response);

    boolean deleted = Weixin.mp(key).getMenu().deleteWithConditional(menuId);
    Assert.assertTrue(deleted);
  }

  /**
   * 获取自定义菜单配置
   */
  @Test
  public void test03() {
    MenuCompleteResponse response = Weixin.mp(key).getMenu().queryComplete();
    Assert.assertNotNull(response);
  }

  /**
   * 删除自定义菜案
   */
  @Test
  public void test04() {
    boolean deleted = Weixin.mp(key).getMenu().delete();
    Assert.assertTrue(deleted);
  }

}
