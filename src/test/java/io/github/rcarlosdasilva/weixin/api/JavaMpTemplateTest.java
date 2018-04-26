package io.github.rcarlosdasilva.weixin.api;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.response.Template;
import io.github.rcarlosdasilva.weixin.model.response.TemplateIndustryGetResponse;
import io.github.rcarlosdasilva.weixin.terms.data.Industry;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaMpTemplateTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static String key;

  @BeforeClass
  public static void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  /**
   * 设置行业，有次数限制
   */
  @Test
  public void test01() {
    boolean success = Weixin.mp(key).getTemplate()
        .setIndustry(Industry.EDUCATION_ACADEMY, Industry.IT_INTERNET_AND_E_COMMERCE);
    Assert.assertTrue(success);

    TemplateIndustryGetResponse response = Weixin.mp(key).getTemplate().getIndustry();
    Assert.assertNotNull(response);
    Assert.assertNotNull(response.getPrimaryIndustry());
    Assert.assertNotNull(response.getSecondaryIndustry());
  }

  /**
   * 添加，删除模板
   */
  @Test
  public void test02() {
    // TM00221 - 放假通知
    String code = Weixin.mp(key).getTemplate().append("TM00221");
    Assert.assertNotNull(code);

    List<Template> temps = Weixin.mp(key).getTemplate().query();
    Assert.assertNotNull(temps);
    Assert.assertTrue(temps.size() > 0);

    boolean deleted = Weixin.mp(key).getTemplate().delete(code);
    Assert.assertTrue(deleted);
  }

}
