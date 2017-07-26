package io.github.rcarlosdasilva.weixin.api.internal;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.user.tag.bean.UserTag;

/**
 * 用户标签API
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface UserTagApi {

  /**
   * 创建标签.
   * 
   * @param name
   *          标签名
   * @return 标签id，由微信分配
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  int create(String name);

  /**
   * 获取公众号已创建的标签.
   * 
   * @return 标签列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  List<UserTag> list();

  /**
   * 编辑标签.
   * 
   * @param id
   *          标签id
   * @param newName
   *          新标签名
   * @return 是否更新
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  boolean update(int id, String newName);

  /**
   * 删除标签.
   * 
   * @param id
   *          标签id
   * @return 是否删除
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  boolean delete(int id);

  /**
   * 批量为用户打标签.
   * 
   * @param id
   *          标签id
   * @param openIds
   *          关注用户open_id列表
   * @return 是否成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  boolean tagging(int id, List<String> openIds);

  /**
   * 批量为用户取消标签.
   * 
   * @param id
   *          标签id
   * @param openIds
   *          关注用户open_id列表
   * @return 是否成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  boolean untagging(int id, List<String> openIds);

  /**
   * 获取用户身上的标签列表.
   * 
   * @param openId
   *          关注用户open_id
   * @return 标签列表
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN"
   *      >用户标签管理</a>
   */
  List<Integer> listBasedUser(String openId);

}
