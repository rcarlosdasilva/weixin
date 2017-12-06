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
    UserOpenIdListResponse users = Weixin.unique().user().listAllUsersOpenId();
    Assert.assertNotNull(users);

    users = Weixin.unique().user().listAllUsersOpenId(users.getLastOpenId());
    Assert.assertNotNull(users);
  }

  @Test
  public void testUserInfoAndRemark() {
    UserOpenIdListResponse resp = Weixin.unique().user().listAllUsersOpenId();
    User user = Weixin.unique().user().getUserInfo(resp.getLastOpenId());
    Assert.assertNotNull(user);

    boolean success = Weixin.unique().user().remarkName(resp.getLastOpenId(), "newname");
    Assert.assertTrue(success);

    success = Weixin.unique().user().remarkName(resp.getLastOpenId(), user.getRemark());
    Assert.assertTrue(success);
  }

  @Test
  public void testGetUsersInfo() {
    UserOpenIdListResponse resp = Weixin.unique().user().listAllUsersOpenId();
    List<User> users = Weixin.unique().user()
        .getUsersInfo(Lists.newArrayList(resp.getLastOpenId()));
    Assert.assertNotNull(users);
    Assert.assertTrue(users.size() > 0);
  }

  @Test
  public void testListUsersOpenIdWithTagInt() {
    int tagid = Weixin.unique().userTag().create("temptag");

    UserOpenIdListResponse resp = Weixin.unique().user().listUsersOpenIdWithTag(tagid);
    Assert.assertNotNull(resp);

    Weixin.unique().userTag().delete(tagid);
  }

  @Test
  public void testBlackList() {
    UserOpenIdListResponse users = Weixin.unique().user().listAllUsersOpenId();

    boolean success = Weixin.unique().user()
        .appendUsersToBlack(Lists.newArrayList(users.getLastOpenId()));
    Assert.assertTrue(success);

    BlackListQueryResponse blacks = Weixin.unique().user().listUsersInBlack();
    Assert.assertNotNull(blacks);
    Assert.assertTrue(blacks.getCount() > 0);

    success = Weixin.unique().user()
        .cancelUsersFromBlack(Lists.newArrayList(users.getLastOpenId()));
    Assert.assertTrue(success);
  }

}
