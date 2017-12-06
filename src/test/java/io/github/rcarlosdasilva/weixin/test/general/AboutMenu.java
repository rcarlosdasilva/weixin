package io.github.rcarlosdasilva.weixin.test.general;

import io.github.rcarlosdasilva.weixin.common.dictionary.Language;
import io.github.rcarlosdasilva.weixin.common.dictionary.Sex;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.Menu;
import io.github.rcarlosdasilva.weixin.model.response.menu.MenuCompleteResponse;
import io.github.rcarlosdasilva.weixin.model.response.menu.MenuInfoResponse;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

/**
 * 自定义菜单的使用
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class AboutMenu {

  /**
   * 菜单的使用.
   */
  @SuppressWarnings("unused")
  public static void main(String[] args) {
    RegisterAndUse.reg();

    // 创建自定义菜单
    Menu menu = Builder.buildMenu().addRootClick("第一个", "一级菜单无子菜单").addRootWithSubButtons("第二个")
        .addClick("第一个", "二级菜单").addClick("第二个", "二级菜单").and().addRootView("第三个", "一级菜单无子菜单")
        .build();
    Weixin.unique().menu().create(menu);

    // 创建个性化菜单
    menu = Builder.buildMenu().addRootClick("第一个", "一级菜单无子菜单").addRootWithSubButtons("第二个")
        .addClick("第一个", "二级菜单").addClick("第二个", "二级菜单").and().addRootView("第三个", "一级菜单无子菜单")
        .withConditional().setSex(Sex.FEMALE).setLanguage(Language.ZH_CN).done().build();
    Weixin.unique().menu().createWithConditional(menu);

    // 删除默认自定义菜单
    Weixin.unique().menu().delete();

    // 删除个性化菜单
    Weixin.unique().menu().deleteWithConditional(0L);

    // 获取菜单信息
    MenuInfoResponse info = Weixin.unique().menu().query();
    MenuCompleteResponse complete = Weixin.unique().menu().queryComplete();

    // 测试个性化菜单
    Weixin.unique().menu().testWithConditional("微信号");
  }

}
