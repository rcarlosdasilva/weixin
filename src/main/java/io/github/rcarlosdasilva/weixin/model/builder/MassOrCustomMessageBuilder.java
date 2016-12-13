package io.github.rcarlosdasilva.weixin.model.builder;

import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Article;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Card;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.MessageContainer;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Image;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Music;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsExternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsInternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Text;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Video;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Voice;

/**
 * 客服消息内容构造器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MassOrCustomMessageBuilder {

  private MessageContainer messageContainer;

  public MassOrCustomMessageBuilder() {
    this.messageContainer = new MessageContainer();
  }

  /**
   * 指定以某个客服帐号来发消息.
   * 
   * @param account
   *          客服账号
   */
  public MassOrCustomMessageBuilder withCustomService(String account) {
    this.messageContainer.setCustomServiceAccount(account);
    return this;
  }

  /**
   * 构建文本消息.
   * 
   * @param content
   *          内容
   */
  public MessageContainer buildText(String content) {
    Text text = new Text();
    text.setContent(content);

    this.messageContainer.setType(MessageType.TEXT);
    this.messageContainer.setBean(text);
    return this.messageContainer;
  }

  /**
   * 构建图片消息.
   * 
   * @param mediaId
   *          Media_Id
   */
  public MessageContainer buildImage(String mediaId) {
    Image image = new Image();
    image.setMediaId(mediaId);

    this.messageContainer.setType(MessageType.IMAGE);
    this.messageContainer.setBean(image);
    return this.messageContainer;
  }

  /**
   * 构建语音消息.
   * 
   * @param mediaId
   *          Media_Id
   */
  public MessageContainer buildVoice(String mediaId) {
    Voice voice = new Voice();
    voice.setMediaId(mediaId);

    this.messageContainer.setType(MessageType.VOICE);
    this.messageContainer.setBean(voice);
    return this.messageContainer;
  }

  /**
   * 构建视频消息.
   * 
   * @param mediaId
   *          Media_Id
   * @param mediaThumbId
   *          缩略图Media_Id
   * @param title
   *          标题
   * @param description
   *          描述
   */
  public MessageContainer buildVideo(String mediaId, String mediaThumbId, String title,
      String description) {
    Video video = new Video();
    video.setMediaId(mediaId);
    video.setMediaThumbId(mediaThumbId);
    video.setTitle(title);
    video.setDescription(description);

    this.messageContainer.setType(MessageType.VIDEO);
    this.messageContainer.setBean(video);
    return this.messageContainer;
  }

  /**
   * 构建音乐消息.
   * 
   * @param mediaThumbId
   *          音乐封面Media_Id
   * @param url
   *          音乐地址
   * @param url4hq
   *          高品质音乐地址
   * @param title
   *          标题
   * @param description
   *          描述
   */
  public MessageContainer buildMusic(String mediaThumbId, String url, String url4hq, String title,
      String description) {
    Music music = new Music();
    music.setMediaThumbId(mediaThumbId);
    music.setUrl(url);
    music.setUrl4hq(url4hq);
    music.setTitle(title);
    music.setDescription(description);

    this.messageContainer.setType(MessageType.MUSIC);
    this.messageContainer.setBean(music);
    return this.messageContainer;
  }

  /**
   * 构建图文消息（内部跳转），群发的图文消息也使用这个方法构建.
   * 
   * @param mediaId
   *          Media_Id
   */
  public MessageContainer buildNewsInternal(String mediaId) {
    NewsInternal news = new NewsInternal();
    news.setMediaId(mediaId);

    this.messageContainer.setType(MessageType.NEWS_INTERNAL);
    this.messageContainer.setBean(news);
    return this.messageContainer;
  }

  /**
   * 构建图文消息（外链）.
   * 
   * @return 外链图文消息构建器，see {@link NewsExternalBuilder}
   */
  public NewsExternalBuilder buildNewsExternal() {
    this.messageContainer.setType(MessageType.NEWS_EXTERNAL);
    return new NewsExternalBuilder(this.messageContainer);
  }

  /**
   * 构建卡券消息.
   * 
   * @param cardId
   *          卡券id
   */
  public MessageContainer buildCard(String cardId) {
    Card card = new Card();
    card.setCardId(cardId);

    this.messageContainer.setType(MessageType.CARD);
    this.messageContainer.setBean(card);
    return this.messageContainer;
  }

  /**
   * 多图文消息（外链）构建器
   * 
   * @author Dean Zhao (rcarlosdasilva@qq.com)
   */
  public class NewsExternalBuilder {

    private NewsExternal news;
    private MessageContainer messageContainer;

    private NewsExternalBuilder(MessageContainer messageContainer) {
      this.news = new NewsExternal();
      this.messageContainer = messageContainer;
    }

    /**
     * 添加一个外链图文信息.
     * 
     * @param url
     *          地址
     * @param title
     *          标题
     * @param description
     *          描述
     * @param picUrl
     *          图片地址
     */
    public NewsExternalBuilder add(String url, String title, String description, String picUrl) {
      Article article = new Article();
      article.setUrl(url);
      article.setTitle(title);
      article.setDescription(description);
      article.setPicUrl(picUrl);
      this.news.addArticle(article);
      return this;
    }

    /**
     * 构建.
     */
    public MessageContainer build() {
      this.messageContainer.setBean(this.news);
      return this.messageContainer;
    }

  }

}
