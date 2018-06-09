package io.github.rcarlosdasilva.weixin.api;

import com.google.common.collect.Lists;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.response.UserTag;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaMpUserTagTest {

  private static String key;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @BeforeClass
  public static void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  /**
   * 标签增删改查，以及标记取消标记
   */
  @Test
  public void test() {
    Random random = new Random();
    String tagName1 = "tag_" + random.nextInt();
    String tagName2 = "tag_" + random.nextInt();
    String openId = TestHelper.INSTANCE.get("openid.my");

    // 创建
    UserTag tag = Weixin.mp(key).getUserTag().create(tagName1);
    Assert.assertNotNull(tag);

    // 获取所有标签
    List<UserTag> allTags = Weixin.mp(key).getUserTag().list();
    Assert.assertNotNull(allTags);
    Assert.assertTrue(allTags.size() > 0);
    // 验证刚创建的标签存在
    boolean found = false;
    for (UserTag t : allTags) {
      found |= t.getId() == tag.getId();
    }
    Assert.assertTrue(found);

    // 修改
    boolean updated = Weixin.mp(key).getUserTag().update(tag.getId(), tagName2);
    Assert.assertTrue(updated);

    // 标记用户
    boolean tagged = Weixin.mp(key).getUserTag().tag(tag.getId(), Lists.newArrayList(openId));
    Assert.assertTrue(tagged);

    // 用户拥有的所有标签
    List<Integer> tags = Weixin.mp(key).getUserTag().listBasedUser(openId);
    Assert.assertNotNull(tags);
    Assert.assertTrue(tags.size() > 0);
    // 验证刚刚标记在用户身上的标签存在
    boolean isU = false;
    for (Integer t : tags) {
      isU |= t == tag.getId();
    }
    Assert.assertTrue(isU);

    // 取消标记该用户
    boolean untagged = Weixin.mp(key).getUserTag().untag(tag.getId(), Lists.newArrayList(openId));
    Assert.assertTrue(untagged);

    // 删除刚刚创建的标签
    boolean deleted = Weixin.mp(key).getUserTag().delete(tag.getId());
    Assert.assertTrue(deleted);
  }

}
