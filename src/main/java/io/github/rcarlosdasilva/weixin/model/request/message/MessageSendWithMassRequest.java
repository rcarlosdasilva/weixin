package io.github.rcarlosdasilva.weixin.model.request.message;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;
import io.github.rcarlosdasilva.weixin.core.json.Freeze;
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
  private MessageSendWithMassRequestUser user;
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
  @SerializedName("send_ignore_reprint")
  private int canReprint = 0;
  @SerializedName("clientmsgid")
  private String mark;

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
   * 
   * @param tagId
   *          tag id
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
    this.user = new MessageSendWithMassRequestUser(users);
  }

  public void setUser(String user) {
    this.user = new MessageSendWithMassRequestUser(user);
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

  /**
   * 群发接口新增 send_ignore_reprint 参数，开发者可以对群发接口的 send_ignore_reprint
   * 参数进行设置，指定待群发的文章被判定为转载时，是否继续群发。<br>
   * 当 send_ignore_reprint 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。<br>
   * 当 send_ignore_reprint 参数设置为0时，文章被判定为转载时，将停止群发操作。<br>
   * send_ignore_reprint 默认为0。
   * 
   * @param canReprint
   *          send_ignore_reprint
   */
  public void setCanReprint(boolean canReprint) {
    this.canReprint = canReprint ? Convention.GLOBAL_TRUE_NUMBER : Convention.GLOBAL_FALSE_NUMBER;
  }

  /**
   * 群发接口新增 clientmsgid 参数，开发者调用群发接口时可以主动设置 clientmsgid 参数，避免重复推送。 群发时，微信后台将对 24
   * 小时内的群发记录进行检查，如果该 clientmsgid
   * 已经存在一条群发记录，则会拒绝本次群发请求，返回已存在的群发msgid，开发者可以调用“查询群发消息发送状态”接口查看该条群发的状态。
   * 
   * @param mark
   *          开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
   */
  public void setMark(String mark) {
    this.mark = mark;
  }

}
