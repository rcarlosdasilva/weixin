package io.github.rcarlosdasilva.weixin.model.notification;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import io.github.rcarlosdasilva.weixin.common.dictionary.MessageType;
import io.github.rcarlosdasilva.weixin.model.notification.bean.ResponseAdditionalInfo;
import io.github.rcarlosdasilva.weixin.model.notification.converter.EnumStringConverter;
import io.github.rcarlosdasilva.weixin.model.notification.converter.ResponseConverter;

/**
 * 微信推送通知后，被动回复用户消息，明文模式
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@XStreamAlias("xml")
@XStreamConverter(ResponseConverter.class)
public class NotificationResponsePlaintext implements NotificationResponse {

  @XStreamAlias("ToUserName")
  private String toUser;
  @XStreamAlias("FromUserName")
  private String fromUser;
  @XStreamAlias("CreateTime")
  private long time;
  @XStreamAlias("MsgType")
  @XStreamConverter(EnumStringConverter.class)
  private MessageType type;
  @XStreamOmitField
  private ResponseAdditionalInfo info = new ResponseAdditionalInfo();
  @XStreamOmitField
  private List<ResponseAdditionalInfo> infos = Lists.newArrayList();

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public void setTime(long time) {
    this.time = time;
  }

  /**
   * 不能为 {@link MessageType#NEWS_INTERNAL} 和 {@link MessageType#CARD}.
   * 
   * @param type
   *          {@link MessageType}
   */
  public void setType(MessageType type) {
    Preconditions.checkArgument(type != MessageType.NEWS_INTERNAL && type != MessageType.CARD,
        "Unspport message type.");
    this.type = type;
  }

  public MessageType getType() {
    return type;
  }

  public ResponseAdditionalInfo getInfo() {
    return info;
  }

  public List<ResponseAdditionalInfo> getInfos() {
    return infos;
  }

  public void addInfo(String title, String description, String url, String otherUrl) {
    this.infos.add(new ResponseAdditionalInfo(title, description, url, otherUrl));
  }

}
