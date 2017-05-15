package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.MessageApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageDeleteMassRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageQueryAutoReplyRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageQueryMassStatusRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageSendWithCustomRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageSendWithMassRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageSendWithTemplateRequest;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Card;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.CustomService;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Image;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.MessageContainer;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Music;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsExternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.NewsInternal;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Template;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Text;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Video;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Voice;
import io.github.rcarlosdasilva.weixin.model.response.message.MessageQueryAutoReplyResponse;
import io.github.rcarlosdasilva.weixin.model.response.message.MessageSendWithMassResponse;
import io.github.rcarlosdasilva.weixin.model.response.message.MessageSendWithTemplateResponse;

/**
 * 消息推送API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MessageApiImpl extends BasicApi implements MessageApi {

  public MessageApiImpl(String accountKey) {
    this.accountKey = accountKey;
  }

  @Override
  public long sendWithTemplate(String to, String templateId, String url,
      Map<String, Template> data) {
    MessageSendWithTemplateRequest requestModel = new MessageSendWithTemplateRequest();
    requestModel.setTo(to);
    requestModel.setTemplateId(templateId);
    requestModel.setUrl(url);
    requestModel.setData(data);

    MessageSendWithTemplateResponse responseModel = post(MessageSendWithTemplateResponse.class,
        requestModel);
    return responseModel == null ? Convention.GLOBAL_FAIL_ID : responseModel.getMessageId();
  }

  @Override
  public boolean sendWithCustom(String to, MessageContainer messageContainer) {
    MessageSendWithCustomRequest requestModel = new MessageSendWithCustomRequest();
    requestModel.setTo(to);
    requestModel.setType(messageContainer.getType());
    injectMessageContent(messageContainer, requestModel);

    if (Strings.isNullOrEmpty(messageContainer.getCustomServiceAccount())) {
      CustomService customService = new CustomService();
      customService.setAccount(messageContainer.getCustomServiceAccount());
      requestModel.setCustomService(customService);
    }

    return post(Boolean.class, requestModel);
  }

  @Override
  public MessageQueryAutoReplyResponse queryAutoReplyStatus() {
    MessageQueryAutoReplyRequest requestModel = new MessageQueryAutoReplyRequest();

    return get(MessageQueryAutoReplyResponse.class, requestModel);
  }

  @Override
  public MessageSendWithMassResponse sendWithMass4Tag(int tagId,
      MessageContainer messageContainer) {
    MessageSendWithMassRequest requestModel = new MessageSendWithMassRequest();
    requestModel.setTagId(tagId);
    requestModel.setType(messageContainer.getType());
    injectMessageContent(messageContainer, requestModel);
    requestModel.forTag();

    return post(MessageSendWithMassResponse.class, requestModel);
  }

  @Override
  public MessageSendWithMassResponse sendWithMassAll(MessageContainer messageContainer) {
    MessageSendWithMassRequest requestModel = new MessageSendWithMassRequest();
    requestModel.toAll();
    requestModel.setType(messageContainer.getType());
    injectMessageContent(messageContainer, requestModel);
    requestModel.forTag();

    return post(MessageSendWithMassResponse.class, requestModel);
  }

  @Override
  public MessageSendWithMassResponse sendWithMass4Users(List<String> openIds,
      MessageContainer messageContainer) {
    MessageSendWithMassRequest requestModel = new MessageSendWithMassRequest();
    requestModel.setUsers(openIds);
    requestModel.setType(messageContainer.getType());
    injectMessageContent(messageContainer, requestModel);
    requestModel.forUsers();

    return post(MessageSendWithMassResponse.class, requestModel);
  }

  @Override
  public MessageSendWithMassResponse sendWithMassPreview(boolean isOpenId, String target,
      MessageContainer messageContainer) {
    MessageSendWithMassRequest requestModel = new MessageSendWithMassRequest();
    if (isOpenId) {
      requestModel.setUser(target);
    } else {
      requestModel.setWxname(target);
    }
    requestModel.setType(messageContainer.getType());
    injectMessageContent(messageContainer, requestModel);
    requestModel.forPreview();

    return post(MessageSendWithMassResponse.class, requestModel);
  }

  @Override
  public boolean deleteMass(String messageId) {
    MessageDeleteMassRequest reuqestModel = new MessageDeleteMassRequest();
    reuqestModel.setMessageId(messageId);

    return post(Boolean.class, reuqestModel);
  }

  @Override
  public boolean queryMassStatus(String messageId) {
    MessageQueryMassStatusRequest requestModel = new MessageQueryMassStatusRequest();
    requestModel.setMessageId(messageId);

    return post(Boolean.class, requestModel);
  }

  /**
   * 根据 {@link MessageContainer} 中的type，将对应的消息内容注入到 {@link MessageRequest} 中.
   * 
   * @param messageContainer
   *          {@link MessageContainer}
   * @param requestModel
   *          {@link MessageRequest}
   */
  private void injectMessageContent(MessageContainer messageContainer, MessageRequest requestModel) {
    switch (messageContainer.getType()) {
      case TEXT: {
        requestModel.setText((Text) messageContainer.getBean());
        break;
      }
      case IMAGE: {
        requestModel.setImage((Image) messageContainer.getBean());
        break;
      }
      case VOICE: {
        requestModel.setVoice((Voice) messageContainer.getBean());
        break;
      }
      case VIDEO: {
        requestModel.setVideo((Video) messageContainer.getBean());
        break;
      }
      case MUSIC: {
        requestModel.setMusic((Music) messageContainer.getBean());
        break;
      }
      case NEWS_EXTERNAL: {
        requestModel.setNewsExternal((NewsExternal) messageContainer.getBean());
        break;
      }
      case NEWS_INTERNAL: {
        requestModel.setNewsInternal((NewsInternal) messageContainer.getBean());
        break;
      }
      case CARD: {
        requestModel.setCard((Card) messageContainer.getBean());
        break;
      }
      default:
        throw new UnknownError();
    }
  }

}
