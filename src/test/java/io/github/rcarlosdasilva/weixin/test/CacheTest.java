package io.github.rcarlosdasilva.weixin.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.registry.Configuration;
import io.github.rcarlosdasilva.weixin.core.registry.RedisConfiguration;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.tag.bean.UserTag;

public class CacheTest {

  @Test
  public void test() {
    String self = "weixin1";
    String test = "weixin2";

    Configuration config = new Configuration();
    RedisConfiguration rc = new RedisConfiguration();
    rc.setHost("localhost");
    rc.setDatabase(2);
    config.setRedisConfiguration(rc);

    WeixinRegistry.withConfig(config);

    WeixinRegistry.register(self, "wx4a1d70e8f0d2a4ec", "9a258c341245bc3f7f9640dda4f4b82e");
    WeixinRegistry.register(test, "wx89e4a038cb7a8ce0", "1574ab11c2355835678f9e1b246bb510");
    WeixinRegistry.done();

    UserOpenIdListResponse uoil1 = Weixin.with(self).user().listAllUsersOpenId();
    int t_uoil1 = uoil1.getTotal();
    assertTrue(t_uoil1 > 0);

    List<UserTag> utl1 = Weixin.with(self).userTag().list();
    assertTrue(utl1 != null);

    UserOpenIdListResponse uoil2 = Weixin.with(test).user().listAllUsersOpenId();
    int t_uoil2 = uoil2.getTotal();
    assertTrue(t_uoil2 > 0);

    List<UserTag> utl2 = Weixin.with(test).userTag().list();
    assertTrue(utl2 != null);
  }

}
