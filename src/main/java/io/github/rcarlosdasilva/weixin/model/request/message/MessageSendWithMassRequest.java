package io.github.rcarlosdasilva.weixin.model.request.message;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Card;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Image;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.MassFilter;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Music;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsExternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsInternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Text;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Video;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Voice;

/**
 * 消息群发请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@SuppressWarnings("unused")
public class MessageSendWithMassRequest extends BasicRequest implements MessageRequest {

  @SerializedName("msgtype")
  private String type;
  private MassFilter filter;
  @SerializedName("touser")
  private List<String> users;
  @SerializedName("touser")
  private String user;
  @SerializedName("towxname")
  private String wxname;
  @SerializedName("mpnews")
  private NewsInternal newsInternal;
  private Text text;
  private Voice voice;
  private Image image;
  @SerializedName("mpvideo")
  private Video video;
  @SerializedName("wxcard")
  private Card card;

  /**
   * 按标签群发.
   */
  public void forTag() {
    this.path = ApiAddress.URL_MESSAGE_SEND_WITH_MASS_FOR_TAG;
  }

  /**
   * 按OpenId群发.
   */
  public void forUsers() {
    this.path = ApiAddress.URL_MESSAGE_SEND_WITH_MASS_FOR_USERS;
  }

  /**
   * 预览接口.
   */
  public void forPreview() {
    this.path = ApiAddress.URL_MESSAGE_SEND_WITH_MASS_PREVIEW;
  }

  public void setType(MessageType type) {
    this.type = type.getText();
  }

  /**
   * 设置标签id.
   */
  public void setTagId(int tagId) {
    if (this.filter == null) {
      this.filter = new MassFilter();
    }
    this.filter.setTagId(tagId);
  }

  /**
   * 设置群发所有用户.
   */
  public void toAll() {
    if (this.filter == null) {
      this.filter = new MassFilter();
    }
    this.filter.setToAll(true);
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setWxname(String wxname) {
    this.wxname = wxname;
  }

  @Override
  public void setNewsInternal(NewsInternal newsInternal) {
    this.newsInternal = newsInternal;
  }

  @Override
  public void setText(Text text) {
    this.text = text;
  }

  @Override
  public void setVoice(Voice voice) {
    this.voice = voice;
  }

  @Override
  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public void setVideo(Video video) {
    this.video = video;
  }

  @Override
  public void setCard(Card card) {
    this.card = card;
  }

  @Override
  public void setMusic(Music music) {
  }

  @Override
  public void setNewsExternal(NewsExternal newsExternal) {
  }

}
