package io.github.rcarlosdasilva.weixin.test;

import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.core.cache.CacheType;
import io.github.rcarlosdasilva.weixin.core.inspect.InspectDispatcher;
import io.github.rcarlosdasilva.weixin.core.setting.Setting;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

public class InspectDispatchTest {

  public static void main(String[] args) throws InterruptedException {
    Setting setting = new Setting();
    setting.setCacheType(CacheType.JDK_MAP);
    Registry.withSetting(setting);
    RegisterAndUse.reg();

    Thread.sleep(1000);
    System.out.println("权当初始化了");
    Weixin.with(RegisterAndUse.DEFAULT_KEY).certificate().askAccessToken();
    Thread.sleep(1000);

    InspectDispatcher.startup();
  }

}
