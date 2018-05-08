package io.github.rcarlosdasilva.weixin.api;

import com.google.common.collect.Lists;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.builder.MessageBuilder;
import io.github.rcarlosdasilva.weixin.model.builder.TemplateMessageBuilder;
import io.github.rcarlosdasilva.weixin.model.request.MessageContainer;
import io.github.rcarlosdasilva.weixin.model.response.MessageQueryAutoReplyResponse;
import io.github.rcarlosdasilva.weixin.model.response.MessageSendWithMassResponse;
import io.github.rcarlosdasilva.weixin.model.response.UserTag;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaMpMessageTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static String key;

  @BeforeClass
  public static void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  /**
   * 发送模板消息
   */
  @Test
  public void test01() {
    // TM00221 - 放假通知
    String templateCode = TestHelper.INSTANCE.get("template.code");
    String openId = TestHelper.INSTANCE.get("openid.my");

    String templateId = Weixin.mp(key).getTemplate().append(templateCode);
    Assert.assertNotNull(templateId);

    TemplateMessageBuilder builder = MessageBuilder.INSTANCE.buildTemplate();
    builder.begin("带薪假期！！")
        .keyword("noticeSender", "老王")
        .keyword("time", "2018年1月1日")
        .keyword("status", "无需")
        .keyword("reason", "老板疯了")
        .end("老板说从1月1日开始放假365天，工资照发不误");

    Long messageId = Weixin.mp(key).getMessage()
        .sendWithTemplate(openId, templateId, "http://www.baidu.com", builder.build());
    Assert.assertNotNull(messageId);

    boolean deleted = Weixin.mp(key).getTemplate().delete(templateId);
    Assert.assertTrue(deleted);
  }

  /**
   * 群发所有人，涉及到文本和图片发送
   */
  @Test
  public void test02() throws URISyntaxException, InterruptedException {
    MessageContainer textMc = MessageBuilder.INSTANCE.buildText("文本消息ABC to All");
    MessageSendWithMassResponse textResponse = Weixin.mp(key).getMessage().sendWithMassAll(textMc);
    Assert.assertNotNull(textResponse);

    Thread.sleep(1000);
    boolean sent = Weixin.mp(key).getMessage().queryMassStatus(textResponse.getMessageId());
    Assert.assertTrue(sent);

    // 使用测试号，不支持
//    Random r = new Random();
//    String imageFilename = "file_" + r.nextInt() + ".jpg";
//    URL url = ClassLoader.getSystemResource("img_2.jpg");
//    File imageFile = new File(url.toURI());
//    MaterialAddTimelessResponse materialResponse = Weixin.mp(key).getMaterial()
//        .addTimelessMaterial(MaterialType.IMAGE, imageFilename, imageFile);
//    MessageContainer imageMc = MessageBuilder.INSTANCE.buildImage(materialResponse.getId());
//    MessageSendWithMassResponse imageResponse = Weixin.mp(key).getMessage().sendWithMassAll(imageMc);
//    Assert.assertNotNull(imageResponse);
  }

  /**
   * 按openid群发
   */
  @Test
  public void test03() {
    String openIdA = TestHelper.INSTANCE.get("openid.my");
    String openIdB = TestHelper.INSTANCE.get("openid.a");
    String openIdC = TestHelper.INSTANCE.get("openid.b");
    List<String> users = Lists.newArrayList(openIdA, openIdB, openIdC);
    MessageContainer textMc = MessageBuilder.INSTANCE.buildText("only you !");
    MessageSendWithMassResponse textResponse = Weixin.mp(key).getMessage().sendWithMass4Users(users, textMc);
    Assert.assertNotNull(textResponse);
  }

  /**
   * 按标签发
   */
  @Test
  public void test04() {
    Random random = new Random();
    String tagName = "tag_" + random.nextInt();
    String openId = TestHelper.INSTANCE.get("openid.my");

    UserTag tag = Weixin.mp(key).getUserTag().create(tagName);
    Weixin.mp(key).getUserTag().tag(tag.getId(), Lists.newArrayList(openId));

    MessageContainer textMc = MessageBuilder.INSTANCE.buildText("on tag");
    MessageSendWithMassResponse textResponse = Weixin.mp(key).getMessage().sendWithMass4Tag(tag.getId(), textMc);
    Assert.assertNotNull(textResponse);
  }

  /**
   * 预览
   */
  @Test
  public void test05() {
    String openId = TestHelper.INSTANCE.get("openid.my");

    MessageContainer textMc = MessageBuilder.INSTANCE.buildText("preview");
    MessageSendWithMassResponse textResponse = Weixin.mp(key).getMessage().sendWithMassPreview(true, openId, textMc);
    Assert.assertNotNull(textResponse);
  }

  /**
   * 回复规则
   */
  @Test
  public void test06() {
    MessageQueryAutoReplyResponse response = Weixin.mp(key).getMessage().queryAutoReplyStatus();
    Assert.assertNotNull(response);
  }

}
