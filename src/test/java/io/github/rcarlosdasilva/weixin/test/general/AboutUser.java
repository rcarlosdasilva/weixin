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
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().remarkName("openid", "name");

    // 获取关注用户openid列表
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listAllUsersOpenId();
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listAllUsersOpenId("nextOpenId");

    // 获取标签先用户openid列表
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listUsersOpenIdWithTag(0);
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listUsersOpenIdWithTag(0, "nextOpenId");

    // 获取用户信息
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().getUserInfo("openid");
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().getUsersInfo(Lists.newArrayList("openid"));

    // 获取通过网页授权的用户信息
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().getUserInfoByWebAuthorize("accessToken", "openId");

    // 下面代码和黑名单相关
    // 获取黑名单
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listUsersInBlack();
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().listUsersInBlack("nextOpenId");
    // 拉黑
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().appendUsersToBlack(Lists.newArrayList("openid"));
    // 取消拉黑
    Weixin.with(RegisterAndUse.DEFAULT_KEY).user().cancelUsersFromBlack(Lists.newArrayList("openid"));

    // 下面代码和标签相关
    // 创建删除更新查询标签
    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().create("name");
    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().delete(0);
    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().update(0, "newname");
    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().list();
    // 获取用户的标签
    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().listBasedUser("openid");
    // 批量对用户设置取消标签
    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().tagging(0, Lists.newArrayList("openid"));
    Weixin.with(RegisterAndUse.DEFAULT_KEY).userTag().untagging(0, Lists.newArrayList("openid"));
  }

}
