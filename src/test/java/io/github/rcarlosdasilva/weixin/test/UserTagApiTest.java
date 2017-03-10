package io.github.rcarlosdasilva.weixin.test;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.tag.bean.UserTag;

public class UserTagApiTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    WeixinRegistry.registryUnique(Property.get("appid"), Property.get("appsecret"));
  }

  @Test
  public void test() {
    int tagid = Weixin.withUnique().userTag().create("newtag");
    Assert.assertTrue(tagid > 0);

    List<UserTag> tags = Weixin.withUnique().userTag().list();
    Assert.assertNotNull(tags);
    Assert.assertTrue(tags.size() > 0);

    UserOpenIdListResponse users = Weixin.withUnique().user().listAllUsersOpenId();

    boolean success = Weixin.withUnique().userTag().tagging(tagid,
        Lists.newArrayList(users.getLastOpenId()));

    success = false;
    List<Integer> tagidlist = Weixin.withUnique().userTag().listBasedUser(users.getLastOpenId());
    for (Integer id : tagidlist) {
      success = id == tagid;
      if (success) {
        break;
      }
    }
    Assert.assertTrue(success);

    Weixin.withUnique().userTag().untagging(tagid, Lists.newArrayList(users.getLastOpenId()));

    success = Weixin.withUnique().userTag().update(tagid, "updatedtag");
    Assert.assertTrue(success);

    success = Weixin.withUnique().userTag().delete(tagid);
    Assert.assertTrue(success);
  }

}
