package io.github.rcarlosdasilva.weixin.api;

import com.google.common.collect.Lists;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.response.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.UserResponse;
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
public class JavaMpUserTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static String key;

  @BeforeClass
  public static void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  /**
   * 备注名与获取用户信息
   */
  @Test
  public void test01() {
    Random r = new Random();
    String originalName;
    String tempName = "temp_" + r.nextInt();
    String openId = TestHelper.INSTANCE.get("openid.my");

    UserResponse user = Weixin.mp(key).getUser().getUserInfo(openId);
    Assert.assertNotNull(user);
    originalName = user.getRemark();

    boolean remarked = Weixin.mp(key).getUser().remarkName(openId, tempName);
    Assert.assertTrue(remarked);

    user = Weixin.mp(key).getUser().getUserInfo(openId);
    Assert.assertNotNull(user);
    Assert.assertEquals(tempName, user.getRemark());
    Assert.assertNotEquals(originalName, user.getRemark());

    boolean recovered = Weixin.mp(key).getUser().remarkName(openId, originalName);
    Assert.assertTrue(recovered);
  }

  /**
   * 获取所有用户
   */
  @Test
  public void test02() {
    UserOpenIdListResponse response = Weixin.mp(key).getUser().listAllUsersOpenId();
    Assert.assertNotNull(response);
    Assert.assertTrue(response.getCount() >= 0);
    if (response.getCount() > 0) {
      Assert.assertNotNull(response.getOpenIds());
      Assert.assertFalse(response.getOpenIds().getList().isEmpty());
    }
  }

  /**
   * 批量获取用户信息
   */
  @Test
  public void test03() {
    String openId = TestHelper.INSTANCE.get("openid.my");
    List<String> users = Lists.newArrayList(openId);
    List<UserResponse> responses = Weixin.mp(key).getUser().getUsersInfo(users);
    Assert.assertNotNull(responses);
    Assert.assertEquals(users.size(), responses.size());
  }

  /**
   * 黑名单相关
   */
  @Test
  public void test04() {
    UserOpenIdListResponse response = Weixin.mp(key).getUser().listUsersInBlack();
    Assert.assertNotNull(response);

    List<String> users = Lists.newArrayList();
    if (response.getCount() == 0) {
      // 黑名单为空的话，使用默认用户
      String openId = TestHelper.INSTANCE.get("openid.my");
      users.add(openId);
    } else {
      // 取黑名单第一个，先取消黑名单
      String openId = response.getOpenIds().getList().get(0);
      users.add(openId);
      boolean canceled = Weixin.mp(key).getUser().cancelUsersFromBlack(users);
      Assert.assertTrue(canceled);
    }

    // 加入黑名单
    boolean appended = Weixin.mp(key).getUser().appendUsersToBlack(users);
    Assert.assertTrue(appended);
  }

}
