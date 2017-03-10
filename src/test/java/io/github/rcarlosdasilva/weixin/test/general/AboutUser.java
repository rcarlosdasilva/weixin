package io.github.rcarlosdasilva.weixin.test.general;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

/**
 * 用户的使用
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class AboutUser {

  /**
   * 用户.
   */
  public static void main(String[] args) {
    RegisterAndUse.reg();

    // 修改备注名
    Weixin.withUnique().user().remarkName("openid", "name");

    // 获取关注用户openid列表
    Weixin.withUnique().user().listAllUsersOpenId();
    Weixin.withUnique().user().listAllUsersOpenId("nextOpenId");

    // 获取标签先用户openid列表
    Weixin.withUnique().user().listUsersOpenIdWithTag(0);
    Weixin.withUnique().user().listUsersOpenIdWithTag(0, "nextOpenId");

    // 获取用户信息
    Weixin.withUnique().user().getUserInfo("openid");
    Weixin.withUnique().user().getUsersInfo(Lists.newArrayList("openid"));

    // 获取通过网页授权的用户信息
    Weixin.withUnique().user().getUserInfoByWebAuthorize("accessToken", "openId");

    // 下面代码和黑名单相关
    // 获取黑名单
    Weixin.withUnique().user().listUsersInBlackList();
    Weixin.withUnique().user().listUsersInBlackList("nextOpenId");
    // 拉黑
    Weixin.withUnique().user().appendUsersToBlackList(Lists.newArrayList("openid"));
    // 取消拉黑
    Weixin.withUnique().user().cancelUsersFromBlackList(Lists.newArrayList("openid"));

    // 下面代码和标签相关
    // 创建删除更新查询标签
    Weixin.withUnique().userTag().create("name");
    Weixin.withUnique().userTag().delete(0);
    Weixin.withUnique().userTag().update(0, "newname");
    Weixin.withUnique().userTag().list();
    // 获取用户的标签
    Weixin.withUnique().userTag().listBasedUser("openid");
    // 批量对用户设置取消标签
    Weixin.withUnique().userTag().tagging(0, Lists.newArrayList("openid"));
    Weixin.withUnique().userTag().untagging(0, Lists.newArrayList("openid"));
  }

}
