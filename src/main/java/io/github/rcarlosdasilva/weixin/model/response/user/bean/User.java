package io.github.rcarlosdasilva.weixin.model.response.user.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class User {

  private int subscribe;
  @SerializedName("openid")
  private String openId;
  private String nickname;
  private int sex;
  private String country;
  private String province;
  private String city;
  private String language;
  @SerializedName("headimgurl")
  private String headImgUrl;
  @SerializedName("subscribe_time")
  private long subscribeTime;
  @SerializedName("unionid")
  private String unionId;
  private String remark;
  @SerializedName("groupid")
  private int groupId;
  @SerializedName("tagid_list")
  private List<Integer> tagList;

  /**
   * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息.
   * 
   * @return subscribe
   */
  public int getSubscribe() {
    return subscribe;
  }

  /**
   * 用户的标识，对当前公众号唯一.
   * 
   * @return open_id
   */
  public String getOpenId() {
    return openId;
  }

  /**
   * 用户的昵称.
   * 
   * @return nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知.
   * 
   * @return sex
   */
  public int getSex() {
    return sex;
  }

  /**
   * 用户所在国家.
   * 
   * @return country
   */
  public String getCountry() {
    return country;
  }

  /**
   * 用户所在省份.
   * 
   * @return province
   */
  public String getProvince() {
    return province;
  }

  /**
   * 用户所在城市.
   * 
   * @return city
   */
  public String getCity() {
    return city;
  }

  /**
   * 用户的语言，简体中文为zh_CN.
   * 
   * @return language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
   * 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效.
   * 
   * @return url
   */
  public String getHeadImgUrl() {
    return headImgUrl;
  }

  /**
   * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间.
   * 
   * @return time
   */
  public long getSubscribeTime() {
    return subscribeTime;
  }

  /**
   * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段.
   * 
   * @return id
   */
  public String getUnionId() {
    return unionId;
  }

  /**
   * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注.
   * 
   * @return remark
   */
  public String getRemark() {
    return remark;
  }

  /**
   * 用户所在的分组ID（兼容旧的用户分组接口）.
   * 
   * @return group id
   */
  public int getGroupId() {
    return groupId;
  }

  /**
   * 用户被打上的标签ID列表.
   * 
   * @return list of tag id
   */
  public List<Integer> getTagList() {
    return tagList;
  }

}
