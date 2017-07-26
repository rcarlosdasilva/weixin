package io.github.rcarlosdasilva.weixin.api.internal;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.user.group.bean.UserGroup;

/**
 * 用户组API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface UserGroupApi {

  /**
   * 创建用户组.
   * 
   * <p>
   * 一个公众账号，最多支持创建100个分组
   * 
   * @param name
   *          组名
   * @return 用户组id
   * @see <a href=
   *      "http://mp.weixin.qq.com/wiki/8/d6d33cf60bce2a2e4fb10a21be9591b8.html"
   *      >用户分组管理</a>
   */
  int create(String name);

  /**
   * 查询所有用户组.
   * 
   * @return {@link UserGroupApi} 列表
   * @see <a href=
   *      "http://mp.weixin.qq.com/wiki/8/d6d33cf60bce2a2e4fb10a21be9591b8.html"
   *      >用户分组管理</a>
   */
  List<UserGroup> list();

  /**
   * 通过用户的OpenID查询其所在的GroupID.
   * 
   * @param openId
   *          OpenId
   * @return 用户组id
   * @see <a href=
   *      "http://mp.weixin.qq.com/wiki/8/d6d33cf60bce2a2e4fb10a21be9591b8.html"
   *      >用户分组管理</a>
   */
  int getByOpenId(String openId);

  /**
   * 修改分组名.
   * 
   * @param id
   *          用户组id
   * @param newName
   *          新组名
   * @return 是否成功
   * @see <a href=
   *      "http://mp.weixin.qq.com/wiki/8/d6d33cf60bce2a2e4fb10a21be9591b8.html"
   *      >用户分组管理</a>
   */
  boolean update(int id, String newName);

  /**
   * 移动用户分组.
   * 
   * @param toGroupId
   *          到分组id
   * @param openId
   *          用户OpenId
   * @return 是否成功
   * @see <a href=
   *      "http://mp.weixin.qq.com/wiki/8/d6d33cf60bce2a2e4fb10a21be9591b8.html"
   *      >用户分组管理</a>
   */
  boolean moveMemberTo(int toGroupId, String openId);

  /**
   * 批量移动用户分组.
   * 
   * @param openIds
   *          用户OpenId列表
   * @param toGroupId
   *          到分组id
   * @return 是否成功
   * @see <a href=
   *      "http://mp.weixin.qq.com/wiki/8/d6d33cf60bce2a2e4fb10a21be9591b8.html"
   *      >用户分组管理</a>
   */
  boolean moveMemberBatchTo(int toGroupId, List<String> openIds);

  /**
   * 删除用户组.
   * 
   * @param id
   *          用户组id
   * @return 是否成功
   * @see <a href=
   *      "http://mp.weixin.qq.com/wiki/8/d6d33cf60bce2a2e4fb10a21be9591b8.html"
   *      >用户分组管理</a>
   */
  boolean delete(int id);

}
