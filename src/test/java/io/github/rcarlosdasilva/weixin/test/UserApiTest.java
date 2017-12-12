package io.github.rcarlosdasilva.weixin.test;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.model.response.user.BlackListQueryResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.bean.User;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

public class UserApiTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    RegisterAndUse.reg();
  }

  @Test
  public void testListAllUsersOpenId() {
    UserOpenIdListResponse users = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listAllUsersOpenId();
    Assert.assertNotNull(users);

    users = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listAllUsersOpenId(users.getLastOpenId());
    Assert.assertNotNull(users);
  }

  @Test
  public void testUserInfoAndRemark() {
    UserOpenIdListResponse resp = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listAllUsersOpenId();
    User user = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().getUserInfo(resp.getLastOpenId());
    Assert.assertNotNull(user);

    boolean success = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().remarkName(resp.getLastOpenId(), "newname");
    Assert.assertTrue(success);

    success = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().remarkName(resp.getLastOpenId(), user.getRemark());
    Assert.assertTrue(success);
  }

  @Test
  public void testGetUsersInfo() {
    UserOpenIdListResponse resp = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listAllUsersOpenId();
    List<User> users = Weixin.with(RegisterAndUse.DEFAULT_KEY).user()
        .getUsersInfo(Lists.newArrayList(resp.getLastOpenId()));
    Assert.assertNotNull(users);
    Assert.assertTrue(users.size() > 0);
  }

  @Test
  public void testListUsersOpenIdWithTagInt() {
    int tagid = Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().create("temptag");

    UserOpenIdListResponse resp = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listUsersOpenIdWithTag(tagid);
    Assert.assertNotNull(resp);

    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().delete(tagid);
  }

  @Test
  public void testBlackList() {
    UserOpenIdListResponse users = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listAllUsersOpenId();

    boolean success = Weixin.with(RegisterAndUse.DEFAULT_KEY).user()
        .appendUsersToBlack(Lists.newArrayList(users.getLastOpenId()));
    Assert.assertTrue(success);

    BlackListQueryResponse blacks = Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listUsersInBlack();
    Assert.assertNotNull(blacks);
    Assert.assertTrue(blacks.getCount() > 0);

    success = Weixin.with(RegisterAndUse.DEFAULT_KEY).user()
        .cancelUsersFromBlack(Lists.newArrayList(users.getLastOpenId()));
    Assert.assertTrue(success);
  }

}
