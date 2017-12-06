package io.github.rcarlosdasilva.weixin.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Template;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

public class MessageApiTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    RegisterAndUse.reg();
  }

  @Test
  public void testSendWithTemplate() {
    // TM00221 - 放假通知 :: {{first.DATA}} 发送人：{{noticeSender.DATA}}
    // 发送时间：{{time.DATA}}
    // 是否需回复：{{status.DATA}} 放假原因：{{reason.DATA}} {{remark.DATA}}
    Map<String, Template> params = Builder.buildTemplateMessage().begin("测试模板")
        .keyword("noticeSender", "王二麻子").keyword("time", "明天").keyword("status", "昂")
        .keyword("reason", "No Why").end("详情..").build();
    Long msgid = Weixin.unique().message().sendWithTemplate("o4bFzwme0EES8Jz2mbN-AGlmUPKo",
        "35X9xk5Z5RgbfS_o9NpH7Vg5zu2x3SpuyJ2IHlaEXyI", "http://www.baidu.com", params);
    Assert.assertTrue(msgid > 0);
  }

  @Test
  public void testSendWithCustom() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testQueryAutoReplyStatus() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testSendWithMass4Tag() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testSendWithMassAll() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testSendWithMass4Users() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testSendWithMassPreview() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testDeleteMass() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testQueryMassStatus() {
    Assert.fail("Not yet implemented");
  }

  @Test
  public void testInjectMessageContent() {
    Assert.fail("Not yet implemented");
  }

}
