package io.github.rcarlosdasilva.weixin.test.general;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

/**
 * 用户的使用
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class AboutUser {

  /**
   * 用户.
   */
  public static void main(String[] args) {
    RegisterAndUse.reg();

    // 修改备注名
    Weixin.unique().user().remarkName("openid", "name");

    // 获取关注用户openid列表
    Weixin.unique().user().listAllUsersOpenId();
    Weixin.unique().user().listAllUsersOpenId("nextOpenId");

    // 获取标签先用户openid列表
    Weixin.unique().user().listUsersOpenIdWithTag(0);
    Weixin.unique().user().listUsersOpenIdWithTag(0, "nextOpenId");

    // 获取用户信息
    Weixin.unique().user().getUserInfo("openid");
    Weixin.unique().user().getUsersInfo(Lists.newArrayList("openid"));

    // 获取通过网页授权的用户信息
    Weixin.unique().user().getUserInfoByWebAuthorize("accessToken", "openId");

    // 下面代码和黑名单相关
    // 获取黑名单
    Weixin.unique().user().listUsersInBlack();
    Weixin.unique().user().listUsersInBlack("nextOpenId");
    // 拉黑
    Weixin.unique().user().appendUsersToBlack(Lists.newArrayList("openid"));
    // 取消拉黑
    Weixin.unique().user().cancelUsersFromBlack(Lists.newArrayList("openid"));

    // 下面代码和标签相关
    // 创建删除更新查询标签
    Weixin.unique().userTag().create("name");
    Weixin.unique().userTag().delete(0);
    Weixin.unique().userTag().update(0, "newname");
    Weixin.unique().userTag().list();
    // 获取用户的标签
    Weixin.unique().userTag().listBasedUser("openid");
    // 批量对用户设置取消标签
    Weixin.unique().userTag().tagging(0, Lists.newArrayList("openid"));
    Weixin.unique().userTag().untagging(0, Lists.newArrayList("openid"));
  }

}
