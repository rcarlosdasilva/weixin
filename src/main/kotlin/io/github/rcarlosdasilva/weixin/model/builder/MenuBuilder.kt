package io.github.rcarlosdasilva.weixin.model.builder

import io.github.rcarlosdasilva.weixin.model.request.Button
import io.github.rcarlosdasilva.weixin.model.request.MatchRule
import io.github.rcarlosdasilva.weixin.model.request.Menu
import io.github.rcarlosdasilva.weixin.model.request.SubButton
import io.github.rcarlosdasilva.weixin.terms.data.ClientPlatform
import io.github.rcarlosdasilva.weixin.terms.data.Language
import io.github.rcarlosdasilva.weixin.terms.data.MenuType
import io.github.rcarlosdasilva.weixin.terms.data.Sex

/**
 * 自定义菜单构造器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MenuBuilder {

  private val menu: Menu = Menu()

  /**
   * 指定自定义菜单为个性化菜单
   *
   * 为了帮助公众号实现灵活的业务运营，微信公众平台新增了个性化菜单接口， 开发者可以通过该接口，
   * 让公众号的不同用户群体看到不一样的自定义菜单。该接口开放给已认证订阅号和已认证服务号。
   *
   * 当公众号创建多个个性化菜单时，将按照发布顺序，由新到旧逐一匹配，直到用户信息与matchrule相符合。
   * 如果全部个性化菜单都没有匹配成功，则返回默认菜单。
   *
   * @return 匹配规则构建器
   * @see [MatchRuleBuilder]
   */
  fun withConditional(): MatchRuleBuilder = MatchRule().let {
    menu.matchRule = it
    MatchRuleBuilder(this@MenuBuilder, it)
  }

  /**
   * 添加一个一级菜单：click：点击推事件
   *
   * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
   * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值， 开发者可以通过自定义的key值与用户进行交互；
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key 菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  fun addRootClick(title: String, key: String): MenuBuilder =
      Button(title, MenuType.CLICK.text).let {
        it.key = key
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：view：跳转URL
   *
   * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL， 可与网页授权获取用户基本信息接口结合，获得用户基本信息。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param url 网页链接，用户点击菜单可打开链接，不超过1024字节
   * @return 菜单构造器
   */
  fun addRootView(title: String, url: String): MenuBuilder =
      Button(title, MenuType.VIEW.text).let {
        it.url = url
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：scancode_push：扫码推事件
   *
   * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），
   * 且会将扫码的结果传给开发者，开发者可以下发消息。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key 菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  fun addRootScanQrPush(title: String, key: String): MenuBuilder =
      Button(title, MenuType.SCAN_QR_PUSH.text).let {
        it.key = key
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
   *
   * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中
   * ”提示框，随后可能会收到开发者下发的消息。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key 菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  fun addRootScanQrWaitMsg(title: String, key: String): MenuBuilder =
      Button(title, MenuType.SCAN_QR_WAIT_MSG.text).let {
        it.key = key
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：pic_sysphoto：弹出系统拍照发图
   *
   * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，
   * 并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key 菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  fun addRootPicPhoto(title: String, key: String): MenuBuilder =
      Button(title, MenuType.PIC_PHOTO.text).let {
        it.key = key
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：pic_photo_or_album：弹出拍照或者相册发图
   *
   * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key 菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  fun addRootPicPhotoOrAlbum(title: String, key: String): MenuBuilder =
      Button(title, MenuType.PIC_PHOTO_OR_ALBUM.text).let {
        it.key = key
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：pic_weixin：弹出微信相册发图器
   *
   * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，
   * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key 菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  fun addRootPicWxAlbum(title: String, key: String): MenuBuilder =
      Button(title, MenuType.PIC_WX_ALBUM.text).let {
        it.key = key
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：location_select：弹出地理位置选择器
   *
   * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，
   * 将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param key 菜单KEY值，用于消息接口推送，不超过128字节
   * @return 菜单构造器
   */
  fun addRootLocation(title: String, key: String): MenuBuilder =
      Button(title, MenuType.LOCATION.text).let {
        it.key = key
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：media_id：下发消息（除文本消息）
   *
   * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户， 永久素材类型可以是：
   * 图片、音频、视频、图文消息。请注意： 永久素材id必须是在“素材管理/新增永久素材” 接口上传后获得的合法id。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param mediaId 调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
   * @return 菜单构造器
   */
  fun addRootSendMedia(title: String, mediaId: String): MenuBuilder =
      Button(title, MenuType.SEND_MEDIA.text).let {
        it.mediaId = mediaId
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：view_limited：跳转图文消息URL
   *
   * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，
   * 永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
   *
   * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
   * @param mediaId 调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
   * @return 菜单构造器
   */
  fun addRootRedirectView(title: String, mediaId: String): MenuBuilder =
      Button(title, MenuType.REDIRECT_VIEW.text).let {
        it.mediaId = mediaId
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个一级菜单：miniprogram：小程序
   *
   * @param title 单标题，不超过16个字节，子菜单不超过40个字节
   * @param url 支持小程序的老版本客户端将打开本url
   * @param appid 程序的appid
   * @param path 程序的页面路径
   * @return 菜单构造器
   */
  fun addRootMiniProgram(title: String, url: String, appid: String, path: String): MenuBuilder =
      Button(title, MenuType.MINI_PROGRAM.text).let {
        it.url = url
        it.appid = appid
        it.path = path
        menu.buttons.add(it)
        this
      }

  /**
   * 添加一个二级菜单，并开始配置
   *
   * @param title 单标题，不超过16个字节，子菜单不超过40个字节
   * @return 子菜单构造器
   * @see SubMenuBuilder
   */
  fun addRootWithSubButtons(title: String): SubMenuBuilder =
      Button(title, "").let {
        menu.buttons.add(it)
        SubMenuBuilder(this, it)
      }

  /**
   * 构造菜单数据
   *
   * @return 自定义菜按钮
   */
  fun build(): Menu = menu

  /**
   * 自定义菜单二级菜单构造器
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  inner class SubMenuBuilder constructor(private val caller: MenuBuilder, private val rootButton: Button) {

    /**
     * 添加一个二级菜单：click：点击推事件
     *
     * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
     * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值， 开发者可以通过自定义的key值与用户进行交互；
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key 菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    fun addClick(title: String, key: String): SubMenuBuilder =
        SubButton(title, MenuType.CLICK.text).let {
          it.key = key
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：view：跳转URL
     *
     * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL， 可与网页授权获取用户基本信息接口结合，获得用户基本信息。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param url 网页链接，用户点击菜单可打开链接，不超过1024字节
     * @return 菜单构造器
     */
    fun addView(title: String, url: String): SubMenuBuilder =
        SubButton(title, MenuType.VIEW.text).let {
          it.url = url
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：scancode_push：扫码推事件
     *
     * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），
     * 且会将扫码的结果传给开发者，开发者可以下发消息。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key 菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    fun addScanQrPush(title: String, key: String): SubMenuBuilder =
        SubButton(title, MenuType.SCAN_QR_PUSH.text).let {
          it.key = key
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
     *
     * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者， 同时收起扫一扫工具，然后弹出“消息接收中
     * ”提示框，随后可能会收到开发者下发的消息。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key 菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    fun addScanQrWaitMsg(title: String, key: String): SubMenuBuilder =
        SubButton(title, MenuType.SCAN_QR_WAIT_MSG.text).let {
          it.key = key
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：pic_sysphoto：弹出系统拍照发图
     *
     * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，
     * 并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key 菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    fun addPicPhoto(title: String, key: String): SubMenuBuilder =
        SubButton(title, MenuType.PIC_PHOTO.text).let {
          it.key = key
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：pic_photo_or_album：弹出拍照或者相册发图
     *
     * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key 菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    fun addPicPhotoOrAlbum(title: String, key: String): SubMenuBuilder =
        SubButton(title, MenuType.PIC_PHOTO_OR_ALBUM.text).let {
          it.key = key
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：pic_weixin：弹出微信相册发图器
     *
     * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，
     * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key 菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    fun addPicWxAlbum(title: String, key: String): SubMenuBuilder =
        SubButton(title, MenuType.PIC_WX_ALBUM.text).let {
          it.key = key
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：location_select：弹出地理位置选择器
     *
     * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，
     * 将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param key 菜单KEY值，用于消息接口推送，不超过128字节
     * @return 菜单构造器
     */
    fun addLocation(title: String, key: String): SubMenuBuilder =
        SubButton(title, MenuType.LOCATION.text).let {
          it.key = key
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：media_id：下发消息（除文本消息）
     *
     * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户， 永久素材类型可以是：
     * 图片、音频、视频、图文消息。请注意： 永久素材id必须是在“素材管理/新增永久素材” 接口上传后获得的合法id。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param mediaId 调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
     * @return 菜单构造器
     */
    fun addSendMedia(title: String, mediaId: String): SubMenuBuilder =
        SubButton(title, MenuType.SEND_MEDIA.text).let {
          it.mediaId = mediaId
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：view_limited：跳转图文消息URL
     *
     * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，
     * 永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param mediaId 调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型
     * @return 菜单构造器
     */
    fun addRedirectView(title: String, mediaId: String): SubMenuBuilder =
        SubButton(title, MenuType.REDIRECT_VIEW.text).let {
          it.mediaId = mediaId
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 添加一个二级菜单：miniprogram：小程序
     *
     * @param title 菜单标题，不超过16个字节，子菜单不超过40个字节
     * @param url 不支持小程序的老版本客户端将打开本url
     * @param appid 小程序的appid
     * @param path 小程序的页面路径
     * @return 菜单构造器
     */
    fun addMiniProgram(title: String, url: String, appid: String, path: String): SubMenuBuilder =
        SubButton(title, MenuType.MINI_PROGRAM.text).let {
          it.url = url
          it.appid = appid
          it.path = path
          rootButton.subButtons.add(it)
          this
        }

    /**
     * 返回一级菜单构造器
     *
     * @return 菜单构造器
     */
    fun and(): MenuBuilder = caller

  }

  /**
   * 匹配规则构建器
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  inner class MatchRuleBuilder constructor(private val caller: MenuBuilder, private val matchRule: MatchRule) {

    /**
     * 用户标签的id，可通过用户标签管理接口获取
     *
     * @param tagId 标签id
     * @return [MatchRuleBuilder]
     */
    fun setTagId(tagId: Int): MatchRuleBuilder {
      this.matchRule.tagId = tagId
      return this
    }

    /**
     * 性别：男（1）女（2），不填则不做匹配
     *
     * @param sex [Sex]
     * @return [MatchRuleBuilder]
     */
    fun setSex(sex: Sex): MatchRuleBuilder {
      this.matchRule.sex = sex.code
      return this
    }

    /**
     * 国家信息，是用户在微信中设置的地区，具体请参考地区信息表
     *
     * @param country 国家
     * @return [MatchRuleBuilder]
     */
    fun setCountry(country: String): MatchRuleBuilder {
      this.matchRule.country = country
      return this
    }

    /**
     * 省份信息，是用户在微信中设置的地区，具体请参考地区信息表
     *
     * @param province 省份
     * @return [MatchRuleBuilder]
     */
    fun setProvince(province: String): MatchRuleBuilder {
      this.matchRule.province = province
      return this
    }

    /**
     * 城市信息，是用户在微信中设置的地区，具体请参考地区信息表
     *
     * @param city 城市
     * @return [MatchRuleBuilder]
     */
    fun setCity(city: String): MatchRuleBuilder {
      this.matchRule.city = city
      return this
    }

    /**
     * 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配
     *
     * @param type [ClientPlatform]
     * @return [MatchRuleBuilder]
     */
    fun setClientPlatformType(platform: ClientPlatform): MatchRuleBuilder {
      this.matchRule.clientPlatform = platform.code
      return this
    }

    /**
     * 语言信息，是用户在微信中设置的语言
     *
     * @param language [Language]
     * @return [MatchRuleBuilder]
     */
    fun setLanguage(language: Language): MatchRuleBuilder {
      this.matchRule.language = language.text
      return this
    }

    /**
     * 完成匹配规则的配置
     *
     * @return 匹配条件
     */
    fun done(): MenuBuilder = caller

  }

}