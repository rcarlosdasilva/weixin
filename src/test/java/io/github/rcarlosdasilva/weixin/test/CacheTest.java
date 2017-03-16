package io.github.rcarlosdasilva.weixin.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.config.RedisConfiguration;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.tag.bean.UserTag;

public class CacheTest {

  @Test
  public void test() {
    String self = "self";
    String test = "test";

    RedisConfiguration rc = WeixinRegistry.useRedis();
    rc.setHost("localhost");
    rc.setDatabase(1);

    WeixinRegistry.init();

    WeixinRegistry.registry(self, "wx4a1d70e8f0d2a4ec", "9a258c341245bc3f7f9640dda4f4b82e");
    WeixinRegistry.registry(test, "wx89e4a038cb7a8ce0", "1574ab11c2355835678f9e1b246bb510");

    UserOpenIdListResponse uoil = Weixin.with(self).user().listAllUsersOpenId();
    int t_uoil = uoil.getTotal();
    assertTrue(t_uoil > 0);

    List<UserTag> utl = Weixin.with(self).userTag().list();
    assertTrue(utl != null);
  }

}
