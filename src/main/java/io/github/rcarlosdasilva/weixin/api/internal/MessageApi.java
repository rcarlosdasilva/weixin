package io.github.rcarlosdasilva.weixin.api.internal;

import java.util.List;
import java.util.Map;

import io.github.rcarlosdasilva.weixin.model.builder.Builder;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.MessageContainer;
import io.github.rcarlosdasilva.weixin.model.request.message.bean.Template;
import io.github.rcarlosdasilva.weixin.model.response.message.MessageQueryAutoReplyResponse;
import io.github.rcarlosdasilva.weixin.model.response.message.MessageSendWithMassResponse;

/**
 * 消息推送API
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface MessageApi {

  /**
   * 发送模板消息.
   * 
   * <p>
   * 当前每个账号的模板消息的日调用上限为10万次，单个模板没有特殊限制。【
   * 2014年11月18日将接口调用频率从默认的日1万次提升为日10万次，可在MP登录后的开发者中心查看】。当账号粉丝数超过10W/
   * 100W/1000W时，模板消息的日调用上限会相应提升，以公众号MP后台开发者中心页面中标明的数字为准。
   * 
   * @param to
   *          发送给（open_id）
   * @param templateId
   *          模板id
   * @param url
   *          消息跳转页面
   * @param data
   *          消息内容，请使用{@code Builder.buildTemplate()}创建。
   * @return 消息编号
   * @see Builder#buildTemplateMessage()
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN"
   *      >模板消息接口</a>
   */
  long sendWithTemplate(String to, String templateId, String url, Map<String, Template> data);

  /**
   * 发送客服消息.
   * 
   * <p>
   * 当用户和公众号产生特定动作的交互时（具体动作列表请见下方说明），微信将会把消息数据推送给开发者，
   * 开发者可以在一段时间内（目前修改为48小时）调用客服接口，通过POST一个JSON数据包来发送消息给普通用户。
   * 此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
   * 
   * @param to
   *          普通用户openid
   * @param messageContainer
   *          客服消息内容，see
   *          {@link MessageContainer}，使用{@link Builder#buildMessage()}构建
   * @return 发送成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547&token=&lang=zh_CN"
   *      >模板消息接口</a>
   */
  boolean sendWithCustom(String to, MessageContainer messageContainer);

  /**
   * 获取公众号的自动回复规则.
   * 
   * <p>
   * 开发者可以通过该接口，获取公众号当前使用的自动回复规则，包括关注后自动回复、消息自动回复（60分钟内触发一次）、关键词自动回复。
   * 
   * @return {@link MessageQueryAutoReplyResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751299&token=&lang=zh_CN"
   *      >获取公众号的自动回复规则</a>
   */
  MessageQueryAutoReplyResponse queryAutoReplyStatus();

  /**
   * 根据标签进行群发.
   * 
   * <p>
   * 在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，仍有可能在后续的发送过程中出现异常情况导致
   * 用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
   * 
   * @param tagId
   *          群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。如要群发给所有用户，使用
   *          {@link #sendWithMassAll(MessageContainer)}
   * @param messageContainer
   *          群发消息内容
   * @return see {@link MessageSendWithMassResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >根据标签进行群发</a>
   */
  MessageSendWithMassResponse sendWithMass4Tag(int tagId, MessageContainer messageContainer);

  /**
   * 群发给所有用户.
   * 
   * <p>
   * 在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，仍有可能在后续的发送过程中出现异常情况导致
   * 用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
   * 
   * @param messageContainer
   *          群发消息内容
   * @return see {@link MessageSendWithMassResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >根据标签进行群发中 is_to_all 为 ture 时</a>
   */
  MessageSendWithMassResponse sendWithMassAll(MessageContainer messageContainer);

  /**
   * 根据OpenID列表群发.
   * 
   * @param openIds
   *          填写图文消息的接收者，一串OpenID列表，OpenID最少2个，最多10000个
   * @param messageContainer
   *          群发消息内容
   * @return see {@link MessageSendWithMassResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >根据OpenID列表群发</a>
   */
  MessageSendWithMassResponse sendWithMass4Users(List<String> openIds,
      MessageContainer messageContainer);

  /**
   * 群发预览.
   * 
   * <p>
   * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版。为了满足第三方平台开发者的需求，
   * 在保留对openID预览能力的同时，增加了对指定微信号发送预览的能力，但该能力每日调用次数有限制（100次），请勿滥用。
   * 
   * @param isOpenId
   *          指定target使用的是OpenId还是微信号，为true时target指定一个OpenId，否则指定微信号
   * @param target
   *          接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
   * @param messageContainer
   *          群发消息内容
   * @return see {@link MessageSendWithMassResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >预览接口</a>
   */
  MessageSendWithMassResponse sendWithMassPreview(boolean isOpenId, String target,
      MessageContainer messageContainer);

  /**
   * 删除群发.
   * 
   * <p>
   * 1、只有已经发送成功的消息才能删除 <br>
   * 2、删除消息是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片。 <br>
   * 3、删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。 <br>
   * 4、如果多次群发发送的是一个图文消息，那么删除其中一次群发，就会删除掉这个图文消息也，导致所有群发都失效 <br>
   * 
   * @param messageId
   *          发送出去的消息ID
   * @return 是否成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >删除群发</a>
   */
  boolean deleteMass(String messageId);

  /**
   * 查询群发消息发送状态.
   * 
   * @param messageId
   *          群发消息后返回的消息id
   * @return 是否发送成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >查询群发消息发送状态</a>
   */
  boolean queryMassStatus(String messageId);

}