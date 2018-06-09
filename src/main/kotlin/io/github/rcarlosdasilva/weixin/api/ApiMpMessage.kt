package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.*
import io.github.rcarlosdasilva.weixin.model.response.MessageQueryAutoReplyResponse
import io.github.rcarlosdasilva.weixin.model.response.MessageSendWithMassResponse
import io.github.rcarlosdasilva.weixin.model.response.MessageSendWithTemplateResponse
import io.github.rcarlosdasilva.weixin.terms.GLOBAL_FALSE_NUMBER
import io.github.rcarlosdasilva.weixin.terms.GLOBAL_TRUE_NUMBER

/**
 * 公众号消息推送API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpMessage(account: Mp) : Api(account) {

  /**
   * 发送模板消息
   *
   * 当前每个账号的模板消息的日调用上限为10万次，单个模板没有特殊限制。【2014年11月18日将接口调用频率从默认的日1万次提升为日10万次，可在MP登录后的开发者中心查看】。
   * 当账号粉丝数超过10W/100W/1000W时，模板消息的日调用上限会相应提升，以公众号MP后台开发者中心页面中标明的数字为准。
   *
   * @param to 发送给（open_id）
   * @param templateId 模板id
   * @param url 消息跳转页面
   * @param data 消息内容，请使用`Builder.buildTemplate()`创建。
   * @return 消息编号
   */
  fun sendWithTemplate(
      to: String,
      templateId: String,
      url: String,
      data: Map<String, Template>
  ): Long = post(
      MessageSendWithTemplateResponse::class.java,
      MessageSendWithTemplateRequest(to, templateId, url, data)
  ).messageId

  /**
   * 发送模板消息（支持小程序）
   *
   * 当前每个账号的模板消息的日调用上限为10万次，单个模板没有特殊限制。【2014年11月18日将接口调用频率从默认的日1万次提升为日10万次，可在MP登录后的开发者中心查看】。
   * 当账号粉丝数超过10W/100W/1000W时，模板消息的日调用上限会相应提升，以公众号MP后台开发者中心页面中标明的数字为准。
   *
   * 注：url和miniprogram都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。开发者可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
   *
   * @param to 发送给（open_id）
   * @param templateId 模板id
   * @param data 消息内容，请使用`Builder.buildTemplate()`创建。
   * @param appid 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
   * @param route 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
   * @return 消息编号
   */
  fun sendWithTemplate(
      to: String,
      templateId: String,
      data: Map<String, Template>,
      appid: String,
      route: String
  ): Long = post(
      MessageSendWithTemplateResponse::class.java,
      MessageSendWithTemplateRequest(to, templateId, data, appid, route)
  ).messageId

  /**
   * 发送客服消息
   *
   * 当用户和公众号产生特定动作的交互时（具体动作列表请见下方说明），微信将会把消息数据推送给开发者，
   * 开发者可以在一段时间内（目前修改为48小时）调用客服接口，通过POST一个JSON数据包来发送消息给普通用户。
   * 此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
   *
   * @param to 普通用户openid
   * @param messageContainer 客服消息内容，see[MessageContainer]
   * @return 发送成功
   */
  fun sendWithCustom(to: String, messageContainer: MessageContainer): Boolean {
    val requestModel = MessageSendWithCustomRequest(to)
    requestModel.setMessage(messageContainer.type, messageContainer.bean!!)

    messageContainer.customServiceAccount?.run {
      requestModel.customService = CustomService(this)
    }

    return post(Boolean::class.java, requestModel)
  }

  /**
   * 获取公众号的自动回复规则
   *
   * 开发者可以通过该接口，获取公众号当前使用的自动回复规则，包括关注后自动回复、消息自动回复（60分钟内触发一次）、关键词自动回复。
   *
   * @return [MessageQueryAutoReplyResponse]
   */
  fun queryAutoReplyStatus(): MessageQueryAutoReplyResponse =
      get(MessageQueryAutoReplyResponse::class.java, MessageQueryAutoReplyRequest())

  /**
   * 根据标签进行群发
   *
   * 1. 对于认证订阅号，群发接口每天可成功调用1次，此次群发可选择发送给全部用户或某个标签；
   * 2. 对于认证服务号虽然开发者使用高级群发接口的每日调用限制为100次，但是用户每月只能接收4条，无论在公众平台网站上，还是使用接口群发，用户每月只能接收4条群发消息，多于4条的群发将对该用户发送失败；
   * 3. 开发者可以使用预览接口校对消息样式和排版，通过预览接口可发送编辑好的消息给指定用户校验效果；
   * 4. 群发过程中，微信后台会自动进行图文消息原创校验，请提前设置好相关参数(send_ignore等)；
   * 5. 开发者可以主动设置 clientmsgid 来避免重复推送。
   * 6. 群发接口每分钟限制请求60次，超过限制的请求会被拒绝。
   * 7. 图文消息正文中插入自己帐号和其他公众号已群发文章链接的能力。
   *
   * 关于群发时使用is_to_all为true使其进入公众号在微信客户端的历史消息列表：
   * 1. 使用is_to_all为true且成功群发，会使得此次群发进入历史消息列表。
   * 2. 为防止异常，认证订阅号在一天内，只能使用is_to_all为true进行群发一次，或者在公众平台官网群发（不管本次群发是对全体还是对某个分组）一次。以避免一天内有2条群发进入历史消息列表。
   * 3. 类似地，服务号在一个月内，使用is_to_all为true群发的次数，加上公众平台官网群发（不管本次群发是对全体还是对某个分组）的次数，最多只能是4次。
   * 4. 设置is_to_all为false时是可以多次群发的，但每个用户只会收到最多4条，且这些群发不会进入历史消息列表。
   *
   * 一、群发接口新增原创校验流程
   * 开发者调用群发接口进行图文消息的群发时，微信会将开发者准备群发的文章，与公众平台原创库中的文章进行比较，校验结果分为以下几种：
   * 1. 当前准备群发的文章，未命中原创库中的文章，则可以群发。
   * 2. 当前准备群发的文章，已命中原创库中的文章，则：
   *   - 若原创作者允许转载该文章，则可以进行群发。群发时，会自动替换成原文的样式，且会自动将文章注明为转载并显示来源。
   *     若希望修改原文内容或样式，或群发时不显示转载来源，可自行与原创公众号作者联系并获得授权之后再进行群发。
   *   - 若原创作者禁止转载该文章，则不能进行群发。
   *     若希望转载该篇文章，可自行与原创公众号作者联系并获得授权之后再进行群发。
   *
   * 二、群发接口新增 send_ignore_reprint 参数
   * 群发接口新增 send_ignore_reprint 参数，开发者可以对群发接口的 send_ignore_reprint 参数进行设置，指定待群发的文章被判定为转载时，是否继续群发。
   * 当 send_ignore_reprint 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。
   * 当 send_ignore_reprint 参数设置为0时，文章被判定为转载时，将停止群发操作。
   * send_ignore_reprint 默认为0。
   *
   * 请注意：在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，仍有可能在后续的发送过程中出现异常情况导致用户未收到消息，
   * 如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
   *
   * @param tagId 群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。如要群发给所有用户，使用[sendWithMassAll]
   * @param messageContainer 群发消息内容
   * @return see [MessageSendWithMassResponse]
   */
  fun sendWithMass4Tag(tagId: Int, messageContainer: MessageContainer): MessageSendWithMassResponse {
    val requestModel = MessageSendWithMassRequest().apply {
      this.forTag(tagId)
      this.setMessage(messageContainer.type, messageContainer.bean!!)
      this.isCanReprint = if (messageContainer.isCanReprint) GLOBAL_TRUE_NUMBER else GLOBAL_FALSE_NUMBER
      messageContainer.businessMark?.run {
        this@apply.mark = this
      }
    }

    return post(MessageSendWithMassResponse::class.java, requestModel)
  }

  /**
   * 群发给所有用户
   *
   * 1. 对于认证订阅号，群发接口每天可成功调用1次，此次群发可选择发送给全部用户或某个标签；
   * 2. 对于认证服务号虽然开发者使用高级群发接口的每日调用限制为100次，但是用户每月只能接收4条，无论在公众平台网站上，还是使用接口群发，用户每月只能接收4条群发消息，多于4条的群发将对该用户发送失败；
   * 3. 开发者可以使用预览接口校对消息样式和排版，通过预览接口可发送编辑好的消息给指定用户校验效果；
   * 4. 群发过程中，微信后台会自动进行图文消息原创校验，请提前设置好相关参数(send_ignore等)；
   * 5. 开发者可以主动设置 clientmsgid 来避免重复推送。
   * 6. 群发接口每分钟限制请求60次，超过限制的请求会被拒绝。
   * 7. 图文消息正文中插入自己帐号和其他公众号已群发文章链接的能力。
   *
   * 关于群发时使用is_to_all为true使其进入公众号在微信客户端的历史消息列表：
   * 1. 使用is_to_all为true且成功群发，会使得此次群发进入历史消息列表。
   * 2. 为防止异常，认证订阅号在一天内，只能使用is_to_all为true进行群发一次，或者在公众平台官网群发（不管本次群发是对全体还是对某个分组）一次。以避免一天内有2条群发进入历史消息列表。
   * 3. 类似地，服务号在一个月内，使用is_to_all为true群发的次数，加上公众平台官网群发（不管本次群发是对全体还是对某个分组）的次数，最多只能是4次。
   * 4. 设置is_to_all为false时是可以多次群发的，但每个用户只会收到最多4条，且这些群发不会进入历史消息列表。
   *
   * 一、群发接口新增原创校验流程
   * 开发者调用群发接口进行图文消息的群发时，微信会将开发者准备群发的文章，与公众平台原创库中的文章进行比较，校验结果分为以下几种：
   * 1. 当前准备群发的文章，未命中原创库中的文章，则可以群发。
   * 2. 当前准备群发的文章，已命中原创库中的文章，则：
   *   - 若原创作者允许转载该文章，则可以进行群发。群发时，会自动替换成原文的样式，且会自动将文章注明为转载并显示来源。
   *     若希望修改原文内容或样式，或群发时不显示转载来源，可自行与原创公众号作者联系并获得授权之后再进行群发。
   *   - 若原创作者禁止转载该文章，则不能进行群发。
   *     若希望转载该篇文章，可自行与原创公众号作者联系并获得授权之后再进行群发。
   *
   * 二、群发接口新增 send_ignore_reprint 参数
   * 群发接口新增 send_ignore_reprint 参数，开发者可以对群发接口的 send_ignore_reprint 参数进行设置，指定待群发的文章被判定为转载时，是否继续群发。
   * 当 send_ignore_reprint 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。
   * 当 send_ignore_reprint 参数设置为0时，文章被判定为转载时，将停止群发操作。
   * send_ignore_reprint 默认为0。
   *
   * 请注意：在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，
   * 仍有可能在后续的发送过程中出现异常情况导致用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
   *
   * @param messageContainer 群发消息内容
   * @return see [MessageSendWithMassResponse]
   */
  fun sendWithMassAll(messageContainer: MessageContainer): MessageSendWithMassResponse {
    val requestModel = MessageSendWithMassRequest().apply {
      this.forAll()
      this.setMessage(messageContainer.type, messageContainer.bean!!)
      this.isCanReprint = if (messageContainer.isCanReprint) GLOBAL_TRUE_NUMBER else GLOBAL_FALSE_NUMBER
      messageContainer.businessMark?.run {
        this@apply.mark = this
      }
    }

    return post(MessageSendWithMassResponse::class.java, requestModel)
  }

  /**
   * 根据OpenID列表群发
   *
   * 1. 对于认证订阅号，群发接口每天可成功调用1次，此次群发可选择发送给全部用户或某个标签；
   * 2. 对于认证服务号虽然开发者使用高级群发接口的每日调用限制为100次，但是用户每月只能接收4条，无论在公众平台网站上，还是使用接口群发，用户每月只能接收4条群发消息，多于4条的群发将对该用户发送失败；
   * 3. 开发者可以使用预览接口校对消息样式和排版，通过预览接口可发送编辑好的消息给指定用户校验效果；
   * 4. 群发过程中，微信后台会自动进行图文消息原创校验，请提前设置好相关参数(send_ignore等)；
   * 5. 开发者可以主动设置 clientmsgid 来避免重复推送。
   * 6. 群发接口每分钟限制请求60次，超过限制的请求会被拒绝。
   * 7. 图文消息正文中插入自己帐号和其他公众号已群发文章链接的能力。
   *
   * 关于群发时使用is_to_all为true使其进入公众号在微信客户端的历史消息列表：
   * 1. 使用is_to_all为true且成功群发，会使得此次群发进入历史消息列表。
   * 2. 为防止异常，认证订阅号在一天内，只能使用is_to_all为true进行群发一次，或者在公众平台官网群发（不管本次群发是对全体还是对某个分组）一次。以避免一天内有2条群发进入历史消息列表。
   * 3. 类似地，服务号在一个月内，使用is_to_all为true群发的次数，加上公众平台官网群发（不管本次群发是对全体还是对某个分组）的次数，最多只能是4次。
   * 4. 设置is_to_all为false时是可以多次群发的，但每个用户只会收到最多4条，且这些群发不会进入历史消息列表。
   *
   * 一、群发接口新增原创校验流程
   * 开发者调用群发接口进行图文消息的群发时，微信会将开发者准备群发的文章，与公众平台原创库中的文章进行比较，校验结果分为以下几种：
   * 1. 当前准备群发的文章，未命中原创库中的文章，则可以群发。
   * 2. 当前准备群发的文章，已命中原创库中的文章，则：
   *   - 若原创作者允许转载该文章，则可以进行群发。群发时，会自动替换成原文的样式，且会自动将文章注明为转载并显示来源。
   *     若希望修改原文内容或样式，或群发时不显示转载来源，可自行与原创公众号作者联系并获得授权之后再进行群发。
   *   - 若原创作者禁止转载该文章，则不能进行群发。
   *     若希望转载该篇文章，可自行与原创公众号作者联系并获得授权之后再进行群发。
   *
   * 二、群发接口新增 send_ignore_reprint 参数
   * 群发接口新增 send_ignore_reprint 参数，开发者可以对群发接口的 send_ignore_reprint
   * 参数进行设置，指定待群发的文章被判定为转载时，是否继续群发。
   * 当 send_ignore_reprint 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。
   * 当 send_ignore_reprint 参数设置为0时，文章被判定为转载时，将停止群发操作。
   * send_ignore_reprint 默认为0。
   *
   * 请注意：在返回成功时，意味着群发任务提交成功，并不意味着此时群发已经结束，所以，
   * 仍有可能在后续的发送过程中出现异常情况导致用户未收到消息，如消息有时会进行审核、服务器不稳定等。此外，群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
   *
   * @param openIds 填写图文消息的接收者，一串OpenID列表，OpenID最少2个，最多10000个
   * @param messageContainer 群发消息内容
   * @return see [MessageSendWithMassResponse]
   */
  fun sendWithMass4Users(openIds: List<String>, messageContainer: MessageContainer): MessageSendWithMassResponse {
    val requestModel = MessageSendWithMassRequest().apply {
      this.forUsers(openIds)
      this.setMessage(messageContainer.type, messageContainer.bean!!)
      this.isCanReprint = if (messageContainer.isCanReprint) GLOBAL_TRUE_NUMBER else GLOBAL_FALSE_NUMBER
      messageContainer.businessMark?.run {
        this@apply.mark = this
      }
    }

    return post(MessageSendWithMassResponse::class.java, requestModel)
  }

  /**
   * 群发预览
   *
   *
   *
   * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版。为了满足第三方平台开发者的需求，
   * 在保留对openID预览能力的同时，增加了对指定微信号发送预览的能力，但该能力每日调用次数有限制（100次），请勿滥用。
   *
   * @param isOpenId 指定target使用的是OpenId还是微信号，为true时target指定一个OpenId，否则指定微信号
   * @param target 接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
   * @param messageContainer 群发消息内容
   * @return see [MessageSendWithMassResponse]
   */
  fun sendWithMassPreview(
      isOpenId: Boolean,
      target: String,
      messageContainer: MessageContainer
  ): MessageSendWithMassResponse {
    val requestModel = MessageSendWithMassRequest().apply {
      this.forPreview(isOpenId, target)
      this.setMessage(messageContainer.type, messageContainer.bean!!)
      this.isCanReprint = if (messageContainer.isCanReprint) GLOBAL_TRUE_NUMBER else GLOBAL_FALSE_NUMBER
      messageContainer.businessMark?.run {
        this@apply.mark = this
      }
    }

    return post(MessageSendWithMassResponse::class.java, requestModel)
  }

  /**
   * 删除群发
   *
   * 1. 只有已经发送成功的消息才能删除
   * 2. 删除消息是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片。
   * 3. 删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
   * 4. 如果多次群发发送的是一个图文消息，那么删除其中一次群发，就会删除掉这个图文消息也，导致所有群发都失效
   *
   * @param messageId 发送出去的消息ID
   * @param index 要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填(null)或填0会删除全部文章
   * @return 是否成功
   */
  fun deleteMass(messageId: String, index: Int): Boolean =
      post(Boolean::class.java, MessageDeleteMassRequest(messageId, index))

  /**
   * 查询群发消息发送状态
   *
   * @param messageId 群发消息后返回的消息id
   * @return 是否发送成功
   */
  fun queryMassStatus(messageId: String): Boolean = post(Boolean::class.java, MessageQueryMassStatusRequest(messageId))

}