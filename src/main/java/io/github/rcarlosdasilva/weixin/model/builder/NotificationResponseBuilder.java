package io.github.rcarlosdasilva.weixin.model.builder;

import java.util.Calendar;

import com.google.common.base.Preconditions;

import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationMeta;
import io.github.rcarlosdasilva.weixin.model.notification.NotificationResponsePlaintext;

/**
 * 微信推送通知的回复构建器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class NotificationResponseBuilder {

  private NotificationMeta meta;
  private NotificationResponsePlaintext response;
  private boolean noResponse = false;

  public NotificationResponseBuilder() {
    this.response = new NotificationResponsePlaintext();
  }

  /**
   * 创建微信推送通知的回复构建器.
   * 
   * @param notificationMeta
   *          推送基础内容.
   * @return 构建器
   */
  public NotificationResponseBuilder with(NotificationMeta notificationMeta) {
    Preconditions.checkNotNull(notificationMeta);
    NotificationResponseBuilder builder = new NotificationResponseBuilder();
    builder.meta = notificationMeta;
    return builder;
  }

  /**
   * 什么也不回复.
   * 
   * <p>
   * 假如服务器无法保证在五秒内处理并回复，必须做出下述回复：<br>
   * 1、直接回复success（推荐方式） <br>
   * 2、直接回复空串（指字节长度为0的空字符串，而不是XML结构体中content字段的内容为空）
   */
  public NotificationResponseBuilder responseNothing() {
    this.noResponse = true;
    return this;
  }

  /**
   * 回复文本消息.
   * 
   * @param content
   *          回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
   */
  public NotificationResponseBuilder responseText(String content) {
    this.response.setType(MessageType.TEXT);
    this.response.getInfo().setContent(content);
    return this;
  }

  /**
   * 回复图片消息.
   * 
   * @param mediaId
   *          通过素材管理中的接口上传多媒体文件，得到的id。
   */
  public NotificationResponseBuilder responseImage(String mediaId) {
    this.response.setType(MessageType.IMAGE);
    this.response.getInfo().setMediaId(mediaId);
    return this;
  }

  /**
   * 回复语音消息.
   * 
   * @param mediaId
   *          通过素材管理中的接口上传多媒体文件，得到的id。
   */
  public NotificationResponseBuilder responseVoice(String mediaId) {
    this.response.setType(MessageType.VOICE);
    this.response.getInfo().setMediaId(mediaId);
    return this;
  }

  /**
   * 回复视频消息.
   * 
   * @param mediaId
   *          通过素材管理中的接口上传多媒体文件，得到的id。
   * @param title
   *          视频消息的标题
   * @param description
   *          视频消息的描述
   */
  public NotificationResponseBuilder responseVideo(String mediaId, String title,
      String description) {
    this.response.setType(MessageType.VIDEO);
    this.response.getInfo().setMediaId(mediaId);
    this.response.getInfo().setTitle(title);
    this.response.getInfo().setDescription(description);
    return this;
  }

  /**
   * 回复音乐消息.
   * 
   * @param mediaThumbId
   *          缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
   * @param title
   *          音乐标题
   * @param description
   *          音乐描述
   * @param musicUrl
   *          音乐链接
   * @param musicHqUrl
   *          高质量音乐链接，WIFI环境优先使用该链接播放音乐
   */
  public NotificationResponseBuilder responseMusic(String mediaThumbId, String title,
      String description, String musicUrl, String musicHqUrl) {
    this.response.setType(MessageType.MUSIC);
    this.response.getInfo().setTitle(title);
    this.response.getInfo().setDescription(description);
    this.response.getInfo().setMediaThumbId(mediaThumbId);
    this.response.getInfo().setUrl(musicUrl);
    this.response.getInfo().setOtherUrl(musicHqUrl);
    return this;
  }

  /**
   * 回复图文消息.
   * <p>
   * 指定图文消息中的一个图文信息，如有多个图文，可多次调用该方法
   * 
   * @param title
   *          图文消息标题
   * @param description
   *          图文消息描述
   * @param url
   *          点击图文消息跳转链接
   * @param picUrl
   *          图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
   */
  public NotificationResponseBuilder responseNewsOneOf(String title, String description, String url,
      String picUrl) {
    this.response.setType(MessageType.NEWS_EXTERNAL);
    this.response.addInfo(title, description, url, picUrl);
    return this;
  }

  /**
   * 构建微信推送响应模型.
   */
  public NotificationResponsePlaintext build() {
    if (this.noResponse) {
      return null;
    }

    if (this.response.getType() == null) {
      throw new RuntimeException("Response for weixin notification is not ensure yet.");
    }
    this.response.setFromUser(this.meta.getToUser());
    this.response.setToUser(this.meta.getFromUser());
    this.response.setTime(Calendar.getInstance().getTimeInMillis());
    return this.response;
  }

}
