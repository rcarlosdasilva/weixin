package io.github.rcarlosdasilva.weixin.api.internal;

import java.util.List;

import io.github.rcarlosdasilva.weixin.common.dictionary.Language;
import io.github.rcarlosdasilva.weixin.model.response.user.BlackListQueryResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.UserOpenIdListResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.UserResponse;
import io.github.rcarlosdasilva.weixin.model.response.user.bean.User;

/**
 * 用户相关API
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface UserApi {

  /**
   * 设置用户备注名.
   * 
   * <p>
   * 开发者可以通过该接口对指定用户设置备注名，该接口暂时开放给微信认证的服务号
   * 
   * @param openId
   *          OpenId
   * @param name
   *          新的备注名，长度必须小于30字符
   * @return 是否成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140838&token=&lang=zh_CN"
   *      >设置用户备注名</a>
   */
  boolean remarkName(String openId, String name);

  /**
   * 获取用户信息.
   * 
   * @param openId
   *          OpenId
   * @return {@link UserResponse} 用户信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN"
   *      >获取用户基本信息(UnionID机制)</a>
   */
  User getUserInfo(String openId);

  /**
   * 批量获取用户信息.
   * 
   * @param openIds
   *          OpenId列表
   * @return {@link UserResponse} 用户信息列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN"
   *      >获取用户基本信息(UnionID机制)</a>
   */
  List<User> getUsersInfo(List<String> openIds);

  /**
   * 获取用户信息.
   * 
   * @param openId
   *          OpenId
   * @return {@link UserResponse} 用户信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN"
   *      >获取用户基本信息(UnionID机制)</a>
   */
  User getUserInfo(String openId, Language language);

  /**
   * 批量获取用户信息.
   * 
   * @param openIds
   *          OpenId列表
   * @return {@link UserResponse} 用户信息列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN"
   *      >获取用户基本信息(UnionID机制)</a>
   */
  List<User> getUsersInfo(List<String> openIds, Language language);

  /**
   * 获取公众号的关注者列表.
   * 
   * <p>
   * 关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
   * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
   * 
   * @return {@link UserOpenIdListResponse} 列表信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN"
   *      >获取用户列表</a>
   */
  UserOpenIdListResponse listAllUsersOpenId();

  /**
   * 获取公众号的关注者列表.
   * 
   * <p>
   * 关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
   * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
   * 
   * @param nextOpenId
   *          第一个拉取的OPENID，不填默认从头开始拉取
   * @return {@link UserOpenIdListResponse} 列表信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN"
   *      >获取用户列表</a>
   */
  UserOpenIdListResponse listAllUsersOpenId(String nextOpenId);

  /**
   * 网页授权，拉取用户信息(需scope为 snsapi_userinfo).
   * 
   * <p>
   * 通过网页授权获取的access_token，获取用户信息（用户不需要关注公众号，只要授权即可）
   * 
   * @param accessToken
   *          网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
   * @param openId
   *          OpenId
   * @return {@link UserResponse} 用户信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN"
   *      >拉取用户信息</a>
   */
  User getUserInfoByWebAuthorize(String accessToken, String openId);

  /**
   * 获取标签下粉丝列表.
   * 
   * @param tagId
   *          标签id
   * @return {@link UserOpenIdListResponse} 列表信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  UserOpenIdListResponse listUsersOpenIdWithTag(int tagId);

  /**
   * 获取标签下粉丝列表.
   * 
   * @param tagId
   *          标签id
   * @param nextOpenId
   *          第一个拉取的OPENID，不填默认从头开始拉取
   * @return {@link UserOpenIdListResponse} 列表信息
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  UserOpenIdListResponse listUsersOpenIdWithTag(int tagId, String nextOpenId);

  /**
   * 获取黑名单中的用户列表.
   * 
   * <p>
   * 公众号可通过该接口来获取帐号的黑名单列表，黑名单列表由一串 OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
   * 该接口每次调用最多可拉取 10000 个OpenID，当列表数较多时，可以通过多次拉取的方式来满足需求。
   * 
   * @return {@link BlackListQueryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN">黑名单管理</a>
   */
  BlackListQueryResponse listUsersInBlackList();

  /**
   * 获取黑名单中的用户列表.
   * 
   * <p>
   * 公众号可通过该接口来获取帐号的黑名单列表，黑名单列表由一串 OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
   * 该接口每次调用最多可拉取 10000 个OpenID，当列表数较多时，可以通过多次拉取的方式来满足需求。
   * 
   * @param beginOpenId
   *          当 begin_openid 为空时，默认从开头拉取
   * @return {@link BlackListQueryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN">黑名单管理</a>
   */
  BlackListQueryResponse listUsersInBlackList(String beginOpenId);

  /**
   * 把用户拉黑.
   * 
   * @param openIds
   *          需要拉入黑名单的用户的openid，一次拉黑最多允许20个
   * @return 成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN">黑名单管理</a>
   */
  boolean appendUsersToBlackList(List<String> openIds);

  /**
   * 取消拉黑用户.
   * 
   * @param openIds
   *          需要取消拉黑名单的用户的openid，一次拉黑最多允许20个
   * @return 成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN">黑名单管理</a>
   */
  boolean cancelUsersFromBlackList(List<String> openIds);

}
