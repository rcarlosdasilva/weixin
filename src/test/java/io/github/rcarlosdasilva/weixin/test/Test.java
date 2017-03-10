package io.github.rcarlosdasilva.weixin.test;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.common.dictionary.WebAuthorizeScope;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.Menu;

public class Test {

  public static void main(String[] args) {
    WeixinRegistry.registryUnique("wxe1f321a16e1f50d5", "28c6bd9501864a4fc24dfbcc4e2ec506");
    // String url1 =
    // Weixin.withUnique().helper().webAuthorize(WebAuthorizeScope.BASE,
    // "http://wjz.tiantianzaixian.net/hebjy/mobile");
    // String url2 =
    // Weixin.withUnique().helper().webAuthorize(WebAuthorizeScope.BASE,
    // "http://wjz.tiantianzaixian.net/hebjy/mobile/article/articleinfo?articleId=f3e995d6fc1c4f779c9b21050eb8cc64");
    // String url3 =
    // Weixin.withUnique().helper().webAuthorize(WebAuthorizeScope.BASE,
    // "http://wjz.tiantianzaixian.net/hebjy/mobile/article/articleinfo?articleId=b1aedff13ca84c9e94f8d0ba0c55e069");
    // Menu menu = Builder.buildMenu().addRootView("微门户", url1)
    // .addRootView("在线直播",
    // "http://wx.wy009.com/layout/party/18261").addRootWithSubButtons("会议")
    // .addView("通知",
    // "http://wjz.tiantianzaixian.net/static/tongzhi.pdf").addView("会序", url2)
    // .addView("演示稿", url3).and().build();
    // boolean su = Weixin.withUnique().menu().create(menu);
    // System.out.println(su);

    String xx = Weixin.withUnique().helper().webAuthorize(WebAuthorizeScope.BASE,
        "http://wjz.tiantianzaixian.net/qdzb/mobile/leave-word/jump");
    System.out.println(xx);
  }

}
