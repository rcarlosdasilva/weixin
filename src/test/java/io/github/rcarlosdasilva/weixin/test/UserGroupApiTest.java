package io.github.rcarlosdasilva.weixin.test;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.group.bean.UserGroup;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

public class UserGroupApiTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    RegisterAndUse.reg();
  }

  @SuppressWarnings("deprecation")
  @Test
  public void test() {
    int group1 = Weixin.withUnique().userGroup().create("abc");
    Assert.assertTrue(group1 > 0);
    int group2 = Weixin.withUnique().userGroup().create("xyz");
    Assert.assertTrue(group2 > 0);

    List<UserGroup> groups = Weixin.withUnique().userGroup().list();
    Assert.assertNotNull(groups);
    Assert.assertTrue(groups.size() > 0);

    boolean success = false;
    success = Weixin.withUnique().userGroup().update(group2, "newgroupname");
    Assert.assertTrue(success);

    UserOpenIdListResponse users = Weixin.withUnique().user().listAllUsersOpenId();

    success = Weixin.withUnique().userGroup().moveMemberTo(group1, users.getLastOpenId());
    Assert.assertTrue(success);
    success = Weixin.withUnique().userGroup().moveMemberBatchTo(group2,
        Lists.newArrayList(users.getLastOpenId()));
    Assert.assertTrue(success);

    int usergrp = Weixin.withUnique().userGroup().getByOpenId(users.getLastOpenId());
    Assert.assertTrue(group2 == usergrp);

    success = Weixin.withUnique().userGroup().delete(group1);
    success = success && Weixin.withUnique().userGroup().delete(group2);
    Assert.assertTrue(success);
  }

}
