package io.github.rcarlosdasilva.weixin.model.builder;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.dictionary.ClientPlatformType;
import io.github.rcarlosdasilva.weixin.common.dictionary.Language;
import io.github.rcarlosdasilva.weixin.common.dictionary.MenuType;
import io.github.rcarlosdasilva.weixin.common.dictionary.Sex;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.Button;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.MatchRule;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.Menu;
import io.github.rcarlosdasilva.weixin.model.request.menu.bean.SubButton;

/**
 * 自定义菜单构造器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MenuBuilder {

  private Menu menu;

  public MenuBuilder() {
    this.menu = new Menu();
    this.menu.setButtons(Lists.newArrayList());
  }

  /**
   * 指定自定义菜单为个性化菜单.
   * 
   * <p>
   * 为了帮助公众号实现灵活的业务运营，微信公众平台新增了个性化菜单接口， 开发者可以通过该接口，
   * 让公众号的不同用户群体看到不一样的自定义菜单。该接口开放给已认证订阅号和已认证服务号。
   * 
   * <p>
   * 当公众号创建多个个性化菜单时，将按照发布顺序，由新到旧逐一匹配，直到用户信息与matchrule相符合。
   * 如果全部个性化菜单都没有匹配成功，则返回默认菜单。
   * 
   * @return 匹配规则构建器
   * @see MatchRuleBuilder
   */
  public MatchRuleBuilder withConditional() {
    MatchRule matchRule = new MatchRule();
    this.menu.setMatchRule(matchRule);
    return new MatchRuleBuilder(this, matchRule);
  }

  /**
   * 添加一个一级菜单：click：点击推事件.
   * 
   * <p>
   * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
   * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值， 开发者可以通过自定义的key值与用户进行交互；
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key
   *          菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootClick(String title, String key) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.CLICK.getText());
    button.setKey(key);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：view：跳转URL.
   * 
   * <p>
   * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL， 可与网页授权获取用户基本信息接口结合，获得用户基本信息。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param url
   *          网页链接，用户点击菜单可打开链接，不超过1024字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootView(String title, String url) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.VIEW.getText());
    button.setUrl(url);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：scancode_push：扫码推事件.
   * 
   * <p>
   * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），
   * 且会将扫码的结果传给开发者，开发者可以下发消息。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key
   *          菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootScanQrPush(String title, String key) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.SCAN_QR_PUSH.getText());
    button.setKey(key);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框.
   * 
   * <p>
   * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中
   * ”提示框，随后可能会收到开发者下发的消息。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key
   *          菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootScanQrWaitMsg(String title, String key) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.SCAN_QR_WAIT_MSG.getText());
    button.setKey(key);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：pic_sysphoto：弹出系统拍照发图.
   * 
   * <p>
   * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，
   * 并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key
   *          菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootPicPhoto(String title, String key) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.PIC_PHOTO.getText());
    button.setKey(key);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：pic_photo_or_album：弹出拍照或者相册发图.
   * 
   * <p>
   * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key
   *          菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootPicPhotoOrAlbum(String title, String key) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.PIC_PHOTO_OR_ALBUM.getText());
    button.setKey(key);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：pic_weixin：弹出微信相册发图器.
   * 
   * <p>
   * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，
   * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key
   *          菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootPicWxAlbum(String title, String key) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.PIC_WX_ALBUM.getText());
    button.setKey(key);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：location_select：弹出地理位置选择器.
   * 
   * <p>
   * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，
   * 将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key
   *          菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  public MenuBuilder addRootLocation(String title, String key) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.LOCATION.getText());
    button.setKey(key);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：media_id：下发消息（除文本消息）.
   * 
   * <p>
   * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户， 永久素材类型可以是：
   * 图片、音频、视频、图文消息。请注意： 永久素材id必须是在“素材管理/新增永久素材” 接口上传后获得的合法id。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param mediaId
   *          调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
   * @return 菜单构造器
   */
  public MenuBuilder addRootSendMedia(String title, String mediaId) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.SEND_MEDIA.getText());
    button.setMediaId(mediaId);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个一级菜单：view_limited：跳转图文消息URL.
   * 
   * <p>
   * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，
   * 永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
   * 
   * @param title
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param mediaId
   *          调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
   * @return 菜单构造器
   */
  public MenuBuilder addRootRedirectView(String title, String mediaId) {
    Button button = new Button();
    button.setName(title);
    button.setType(MenuType.REDIRECT_VIEW.getText());
    button.setMediaId(mediaId);

    this.menu.getButtons().add(button);
    return this;
  }

  /**
   * 添加一个二级菜单，并开始配置.
   * 
   * @param name
   *          菜单标题，不超过16个字节，子菜单不超过40个字节
   * @return 子菜单构造器
   * @see SubMenuBuilder
   */
  public SubMenuBuilder addRootWithSubButtons(String name) {
    Button button = new Button();
    button.setName(name);

    this.menu.getButtons().add(button);

    return new SubMenuBuilder(this, button);
  }

  /**
   * 构造菜单数据.
   * 
   * @return 自定义菜按钮
   */
  public Menu build() {
    return this.menu;
  }

  /**
   * 自定义菜单二级菜单构造器
   * 
   * @author Dean Zhao (rcarlosdasilva@qq.com)
   */
  public class SubMenuBuilder {

    private MenuBuilder caller;
    private Button rootButton;

    private SubMenuBuilder(MenuBuilder caller, Button rootButton) {
      this.caller = caller;
      this.rootButton = rootButton;
    }

    /**
     * 添加一个二级菜单：click：点击推事件.
     * 
     * <p>
     * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
     * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值， 开发者可以通过自定义的key值与用户进行交互；
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key
     *          菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addClick(String title, String key) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.CLICK.getText());
      button.setKey(key);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：view：跳转URL.
     * 
     * <p>
     * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL， 可与网页授权获取用户基本信息接口结合，获得用户基本信息。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param url
     *          网页链接，用户点击菜单可打开链接，不超过1024字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addView(String title, String url) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.VIEW.getText());
      button.setUrl(url);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：scancode_push：扫码推事件.
     * 
     * <p>
     * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），
     * 且会将扫码的结果传给开发者，开发者可以下发消息。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key
     *          菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addScanQrPush(String title, String key) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.SCAN_QR_PUSH.getText());
      button.setKey(key);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框.
     * 
     * <p>
     * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者， 同时收起扫一扫工具，然后弹出“消息接收中
     * ”提示框，随后可能会收到开发者下发的消息。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key
     *          菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addScanQrWaitMsg(String title, String key) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.SCAN_QR_WAIT_MSG.getText());
      button.setKey(key);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：pic_sysphoto：弹出系统拍照发图.
     * 
     * <p>
     * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，
     * 并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key
     *          菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addPicPhoto(String title, String key) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.PIC_PHOTO.getText());
      button.setKey(key);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：pic_photo_or_album：弹出拍照或者相册发图.
     * 
     * <p>
     * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key
     *          菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addPicPhotoOrAlbum(String title, String key) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.PIC_PHOTO_OR_ALBUM.getText());
      button.setKey(key);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：pic_weixin：弹出微信相册发图器.
     * 
     * <p>
     * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，
     * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key
     *          菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addPicWxAlbum(String title, String key) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.PIC_WX_ALBUM.getText());
      button.setKey(key);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：location_select：弹出地理位置选择器.
     * 
     * <p>
     * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，
     * 将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key
     *          菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    public SubMenuBuilder addLocation(String title, String key) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.LOCATION.getText());
      button.setKey(key);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：media_id：下发消息（除文本消息）.
     * 
     * <p>
     * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户， 永久素材类型可以是：
     * 图片、音频、视频、图文消息。请注意： 永久素材id必须是在“素材管理/新增永久素材” 接口上传后获得的合法id。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param mediaId
     *          调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
     * @return 菜单构造器
     */
    public SubMenuBuilder addSendMedia(String title, String mediaId) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.SEND_MEDIA.getText());
      button.setMediaId(mediaId);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 添加一个二级菜单：view_limited：跳转图文消息URL.
     * 
     * <p>
     * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，
     * 永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
     * 
     * @param title
     *          菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param mediaId
     *          调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
     * @return 菜单构造器
     */
    public SubMenuBuilder addRedirectView(String title, String mediaId) {
      SubButton button = new SubButton();
      button.setName(title);
      button.setType(MenuType.REDIRECT_VIEW.getText());
      button.setMediaId(mediaId);

      this.rootButton.addSubButton(button);
      return this;
    }

    /**
     * 返回一级菜单构造器.
     * 
     * @return 菜单构造器
     */
    public MenuBuilder and() {
      return this.caller;
    }

  }

  /**
   * 匹配规则构建器
   * 
   * @author Dean Zhao (rcarlosdasilva@qq.com)
   */
  public class MatchRuleBuilder {

    private MenuBuilder caller;
    private MatchRule matchRule;

    private MatchRuleBuilder(MenuBuilder caller, MatchRule matchRule) {
      this.caller = caller;
      this.matchRule = matchRule;
    }

    /**
     * 用户标签的id，可通过用户标签管理接口获取.
     * 
     * @param tagId
     *          标签id
     * @return {@link MatchRuleBuilder}
     */
    public MatchRuleBuilder setTagId(int tagId) {
      this.matchRule.setTagId(tagId);
      return this;
    }

    /**
     * 性别：男（1）女（2），不填则不做匹配.
     * 
     * @param sex
     *          {@link Sex}
     * @return {@link MatchRuleBuilder}
     */
    public MatchRuleBuilder setSex(Sex sex) {
      this.matchRule.setSex(sex.getCode());
      return this;
    }

    /**
     * 国家信息，是用户在微信中设置的地区，具体请参考地区信息表.
     * 
     * @param country
     *          国家
     * @return {@link MatchRuleBuilder}
     */
    public MatchRuleBuilder setCountry(String country) {
      this.matchRule.setCountry(country);
      return this;
    }

    /**
     * 省份信息，是用户在微信中设置的地区，具体请参考地区信息表.
     * 
     * @param province
     *          省份
     * @return {@link MatchRuleBuilder}
     */
    public MatchRuleBuilder setProvince(String province) {
      this.matchRule.setProvince(province);
      return this;
    }

    /**
     * 城市信息，是用户在微信中设置的地区，具体请参考地区信息表.
     * 
     * @param city
     *          城市
     * @return {@link MatchRuleBuilder}
     */
    public MatchRuleBuilder setCity(String city) {
      this.matchRule.setCity(city);
      return this;
    }

    /**
     * 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配.
     * 
     * @param type
     *          {@link ClientPlatformType}
     * @return {@link MatchRuleBuilder}
     */
    public MatchRuleBuilder setClientPlatformType(ClientPlatformType type) {
      this.matchRule.setClientPlatformType(type.getCode());
      return this;
    }

    /**
     * 语言信息，是用户在微信中设置的语言.
     * 
     * @param language
     *          {@link Language}
     * @return {@link MatchRuleBuilder}
     */
    public MatchRuleBuilder setLanguage(Language language) {
      this.matchRule.setLanguage(language.toString());
      return this;
    }

    /**
     * 完成匹配规则的配置.
     * 
     * @return 匹配条件
     */
    public MenuBuilder done() {
      return this.caller;
    }

  }

}
