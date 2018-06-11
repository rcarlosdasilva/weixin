package io.github.rcarlosdasilva.weixin.model.builder

import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.terms.COLOR_BLACK
import io.github.rcarlosdasilva.weixin.terms.TEMPLATE_DATA_BEGIN_KEY
import io.github.rcarlosdasilva.weixin.terms.TEMPLATE_DATA_END_KEY
import io.github.rcarlosdasilva.weixin.terms.data.MessageType

/**
 * 客服消息内容构造器
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
object MessageBuilder {

  /**
   * 构建文本消息
   *
   * @param content 内容
   * @return [MessageContainer]
   */
  fun buildText(content: String) = MessageContainer(MessageType.TEXT).also {
    it.bean = Text(content)
  }

  /**
   * 构建图片消息
   *
   * @param mediaId Media_Id
   * @return [MessageContainer]
   */
  fun buildImage(mediaId: String) = MessageContainer(MessageType.IMAGE).also {
    it.bean = Image(mediaId)
  }

  /**
   * 构建语音消息
   *
   * @param mediaId Media_Id
   * @return [MessageContainer]
   */
  fun buildVoice(mediaId: String) = MessageContainer(MessageType.VOICE).also {
    it.bean = Voice(mediaId)
  }

  /**
   * 构建视频消息
   *
   * @param mediaId Media_Id
   * @param mediaThumbId 缩略图Media_Id
   * @param title 标题
   * @param description 描述
   * @return [MessageContainer]
   */
  fun buildVideo(mediaId: String, mediaThumbId: String, title: String, description: String) =
      MessageContainer(MessageType.MPVIDEO).also {
        it.bean = Video(mediaId, mediaThumbId, title, description)
      }

  /**
   * 构建音乐消息
   *
   * @param mediaThumbId 音乐封面Media_Id
   * @param url 音乐地址
   * @param url4hq 高品质音乐地址
   * @param title 标题
   * @param description 描述
   * @return [MessageContainer]
   */
  fun buildMusic(
      mediaThumbId: String, url: String, url4hq: String, title: String,
      description: String
  ) = MessageContainer(MessageType.MUSIC).also {
    it.bean = Music(mediaThumbId, url, url4hq, title, description)
  }

  /**
   * 构建图文消息（内部跳转），群发的图文消息也使用这个方法构建
   *
   * @param mediaId Media_Id
   * @return [MessageContainer]
   */
  fun buildNewsInternal(mediaId: String) = MessageContainer(MessageType.NEWS_INTERNAL).also {
    it.bean = NewsInternal(mediaId)
  }

  /**
   * 构建图文消息（外链）
   *
   * @return 外链图文消息构建器，see [NewsExternalBuilder]
   */
  fun buildNewsExternal() = NewsExternalBuilder()

  /**
   * 构建卡券消息
   *
   * @param cardId 卡券id
   * @return [MessageContainer]
   */
  fun buildCard(cardId: String) = MessageContainer(MessageType.CARD).also {
    it.bean = Card(cardId)
  }

  /**
   * 构建模板消息
   *
   * @param color 模板字体默认颜色，默认黑色
   */
  @JvmOverloads
  fun buildTemplate(color: String = COLOR_BLACK) = TemplateMessageBuilder(color)


  /**
   * 多图文消息（外链）构建器
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  class NewsExternalBuilder internal constructor() {

    private val news = NewsExternal()

    /**
     * 添加一个外链图文信息
     *
     * @param url 地址
     * @param title 标题
     * @param description 描述
     * @param picUrl 图片地址
     * @return [NewsExternalBuilder]
     */
    fun add(url: String, title: String, description: String, picUrl: String) = this.also {
      this.news.articles.add(News(title, url, description, picUrl))
    }

    /**
     * 构建
     *
     * @return [MessageContainer]
     */
    fun build() = MessageContainer(MessageType.NEWS_EXTERNAL).also {
      it.bean = this.news
    }

  }

  /**
   * 模板内容构造器
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  class TemplateMessageBuilder internal constructor(private val defaultColor: String) {

    private val data = mutableMapOf<String, Template>()

    /**
     * 设置模板头内容
     *
     * @param value 内容
     * @param color 颜色
     * @return 模板构造器
     */
    @JvmOverloads
    fun begin(value: String, color: String = defaultColor) = this.also {
      data[TEMPLATE_DATA_BEGIN_KEY] = Template(value, color)
      return this
    }

    /**
     * 设置模板尾部内容
     *
     * @param value 内容
     * @param color 颜色
     * @return 模板构造器
     */
    @JvmOverloads
    fun end(value: String, color: String = defaultColor) = this.also {
      data[TEMPLATE_DATA_END_KEY] = Template(value, color)
    }

    /**
     * 添加关键字信息
     *
     * @param key 关键字
     * @param value 内容
     * @param color 颜色
     * @return 模板构造器
     */
    @JvmOverloads
    fun keyword(key: String, value: String, color: String = defaultColor) = this.also {
      data[key] = Template(value, color)
    }

    /**
     * 获取模板内容
     *
     * @return 模板内容
     */
    fun build() = data

  }

}
