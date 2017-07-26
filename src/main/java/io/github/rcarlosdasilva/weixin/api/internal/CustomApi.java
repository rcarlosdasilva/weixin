package io.github.rcarlosdasilva.weixin.api.internal;

import java.io.File;
import java.util.Date;
import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.custom.CustomMessageRecordsResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.CustomSessionWaitingsResponse;
import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomAccount;
import io.github.rcarlosdasilva.weixin.model.response.custom.bean.CustomSession;

/**
 * 客服API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface CustomApi {

  /**
   * 获取客服的基本信息
   * 
   * @return 客服列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
   *      >微信文档：客服管理</a>
   */
  List<CustomAccount> accountList();

  /**
   * 获取在线客服的基本信息
   * 
   * @return 客服列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
   *      >微信文档：客服管理</a>
   */
  List<CustomAccount> accountListOnline();

  /**
   * 添加客服账号（只是添加账号，并未绑定微信账号）
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @param nickname
   *          客服昵称，最长16个字
   * @return 是否添加
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
   *      >微信文档：客服管理</a>
   */
  boolean accountAppend(String accountPrefix, String nickname);

  /**
   * 邀请个人微信绑定客服账号
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @param wxId
   *          接收绑定邀请的客服微信号，可以是手机号、微信号、QQ号
   * @return 是否绑定
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
   *      >微信文档：客服管理</a>
   */
  boolean accountBinding(String accountPrefix, String wxId);

  /**
   * 删除客服账号
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @return 是否删除
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
   *      >微信文档：客服管理</a>
   */
  boolean accountDelete(String accountPrefix);

  /**
   * 更新客服昵称
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @param nickname
   *          客服昵称，最长16个字
   * @return 是否更新
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
   *      >微信文档：客服管理</a>
   */
  boolean accountUpdate(String accountPrefix, String nickname);

  /**
   * 上传客服头像
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @param fileName
   *          文件名
   * @param file
   *          文件
   * @return 是否上传
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN"
   *      >微信文档：客服管理</a>
   */
  boolean accountUploadAvatar(String accountPrefix, String fileName, File file);

  /**
   * 创建会话
   * 
   * <p>
   * 在客服和用户之间创建一个会话，如果该客服和用户会话已存在，则直接返回0。指定的客服帐号必须已经绑定微信号且在线。
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @param openId
   *          关注用户open_id
   * @return 是否创建
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN"
   *      >微信文档：会话控制</a>
   */
  boolean sessionCreate(String accountPrefix, String openId);

  /**
   * 关闭会话
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @param openId
   *          关注用户open_id
   * @return 是否关闭
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN"
   *      >微信文档：会话控制</a>
   */
  boolean sessionClose(String accountPrefix, String openId);

  /**
   * 获取一个客户的会话，如果不存在，则kf_account为空
   * 
   * @param openId
   *          关注用户open_id
   * @return 会话状态
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN"
   *      >微信文档：会话控制</a>
   */
  CustomSession sessionStatus(String openId);

  /**
   * 获取指定客服的会话列表
   * 
   * @param accountPrefix
   *          完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线。
   *          这里account只需传账号前缀
   * @return 会话状态列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN"
   *      >微信文档：会话控制</a>
   */
  List<CustomSession> sessionList(String accountPrefix);

  /**
   * 获取未接入会话列表
   * 
   * @return 未接入会话列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN"
   *      >微信文档：会话控制</a>
   */
  CustomSessionWaitingsResponse sessionWaitings();

  /**
   * 获取聊天记录
   * 
   * <p>
   * 聊天记录中，对于图片、语音、视频，分别展示成文本格式的[image]、[voice]、[video]。
   * 对于较可能包含重要信息的图片消息，后续将提供图片拉取URL，近期将上线。
   * 
   * @param start
   *          起始时间
   * @param end
   *          结束时间，每次查询时段不能超过24小时
   * @param size
   *          每次获取的最大条数，最大10000条
   * @param messageId
   *          消息id顺序从小到大，从1开始
   * @return 聊天记录列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1464937269_mUtmK&token=&lang=zh_CN"
   *      >微信文档：获取聊天记录</a>
   */
  CustomMessageRecordsResponse messageRecords(Date start, Date end, int size, long messageId);

}
