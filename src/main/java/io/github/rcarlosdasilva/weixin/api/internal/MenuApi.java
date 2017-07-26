package io.github.rcarlosdasilva.weixin.api.internal;

import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.Menu;
import io.github.rcarlosdasilva.weixin.model.response.menu.MenuCompleteResponse;
import io.github.rcarlosdasilva.weixin.model.response.menu.MenuInfoResponse;

/**
 * 自定义菜单API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface MenuApi {

  /**
   * 创建自定义菜单.
   * 
   * <p>
   * 1、自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。<br>
   * 2、一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。<br>
   * 3、创建自定义菜单后，菜单的刷新策略是，在用户进入公众号会话页或公众号profile页时，
   * 如果发现上一次拉取菜单的请求在5分钟以前，就会拉取一下菜单，如果菜单有更新，
   * 就会刷新客户端的菜单。测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。
   * 
   * @param menu
   *          菜单具体信息，参考 {@link Builder#buildMenu()}
   * @return 是否创建
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN"
   *      >自定义菜单创建接口</a>
   */
  boolean create(Menu menu);

  /**
   * 创建个性化菜单.
   * 
   * <p>
   * 参看 {@code Builder.buildMenu().withConditional()}
   * 
   * <p>
   * {@code create(MenuCreateRequest menu)} 创建的是默认菜单。个性化菜单可以创建多个，依照最新创建的开始依次匹配。
   * 
   * @param menu
   *          菜单具体信息，参考 {@link Builder#buildMenu()}
   * @return 个性化菜单id
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN"
   *      >个性化菜单接口</a>
   */
  long createWithConditional(Menu menu);

  /**
   * 使用接口创建自定义菜单后，开发者还可使用接口查询自定义菜单的结构。另外请注意，在设置了个性化菜单后，
   * 使用本自定义菜单查询接口可以获取默认菜单和全部个性化菜单信息.
   * 
   * @return 自定义菜单结果
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN"
   *      >自定义菜单查询接口</a>
   */
  MenuInfoResponse query();

  /**
   * 本接口将会提供公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
   * 而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置.
   * 
   * <p>
   * 本接口与自定义菜单查询接口的不同之处在于，本接口无论公众号的接口是如何设置的，都能查询到接口，
   * 而自定义菜单查询接口则仅能查询到使用API设置的菜单配置。认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
   * 
   * @return 自定义菜单配置
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN"
   *      >获取自定义菜单配置接口</a>
   */
  MenuCompleteResponse queryComplete();

  /**
   * 删除默认菜单及全部个性化菜单.
   * 
   * @return 是否删除
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141015&token=&lang=zh_CN"
   *      >自定义菜单删除接口</a>
   */
  boolean delete();

  /**
   * 删除个性化菜单.
   * 
   * @param menuId
   *          个性化菜单id
   * @return 是否删除
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN"
   *      >个性化菜单接口</a>
   */
  boolean deleteWithConditional(long menuId);

  /**
   * 测试个性化菜单匹配结果.
   * 
   * @param userLabel
   *          可以是粉丝的OpenID，也可以是粉丝的微信号
   * @return 菜单结果
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN"
   *      >个性化菜单接口</a>
   */
  MenuInfoResponse testWithConditional(String userLabel);

}
