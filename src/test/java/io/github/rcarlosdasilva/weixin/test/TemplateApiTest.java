package io.github.rcarlosdasilva.weixin.test;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.rcarlosdasilva.weixin.common.dictionary.Industry;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.model.response.template.TemplateIndustryGetResponse;
import io.github.rcarlosdasilva.weixin.model.response.template.bean.Template;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

public class TemplateApiTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    RegisterAndUse.reg();
  }

  @Test
  public void test() {
    boolean success = Weixin.withUnique().template().setIndustry(Industry.EDUCATION_ACADEMY,
        Industry.IT_INTERNET_AND_ECOMMERCE);
    Assert.assertTrue(success);

    TemplateIndustryGetResponse temp = Weixin.withUnique().template().getIndustry();
    Assert.assertNotNull(temp);
    Assert.assertNotNull(temp.getPrimaryIndustry());
    Assert.assertNotNull(temp.getSecondaryIndustry());

    // TM00221 - 放假通知
    String code = Weixin.withUnique().template().append("TM00221");
    Assert.assertNotNull(code);

    List<Template> temps = Weixin.withUnique().template().query();
    Assert.assertNotNull(temps);
    Assert.assertTrue(temps.size() > 0);

    success = Weixin.withUnique().template().delete(code);
    Assert.assertTrue(success);
  }

}
