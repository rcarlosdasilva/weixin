package io.github.rcarlosdasilva.weixin.model.request.message;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Card;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.CustomService;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Image;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Music;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsExternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsInternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Text;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Video;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Voice;

/**
 * 客服消息推送请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@SuppressWarnings("unused")
public class MessageSendWithCustomRequest extends BasicWeixinRequest implements MessageRequest {

  @SerializedName("touser")
  private String to;
  @SerializedName("msgtype")
  private String type;
  private Text text;
  private Image image;
  private Voice voice;
  private Video video;
  private Music music;
  @SerializedName("news")
  private NewsExternal newsExternal;
  @SerializedName("mpnews")
  private NewsInternal newsInternal;
  @SerializedName("wxcard")
  private Card card;
  @SerializedName("customservice")
  private CustomService customService;

  public MessageSendWithCustomRequest() {
    this.path = ApiAddress.URL_MESSAGE_SEND_WITH_CUSTOM;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public void setType(MessageType type) {
    this.type = type.getText();
  }

  @Override
  public void setText(Text text) {
    this.text = text;
  }

  @Override
  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public void setVoice(Voice voice) {
    this.voice = voice;
  }

  @Override
  public void setVideo(Video video) {
    this.video = video;
  }

  @Override
  public void setMusic(Music music) {
    this.music = music;
  }

  @Override
  public void setNewsExternal(NewsExternal newsExternal) {
    this.newsExternal = newsExternal;
  }

  @Override
  public void setNewsInternal(NewsInternal newsInternal) {
    this.newsInternal = newsInternal;
  }

  @Override
  public void setCard(Card card) {
    this.card = card;
  }

  public void setCustomService(CustomService customService) {
    this.customService = customService;
  }

}
