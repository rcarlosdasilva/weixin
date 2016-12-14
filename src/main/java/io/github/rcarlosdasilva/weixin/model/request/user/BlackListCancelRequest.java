package io.github.rcarlosdasilva.weixin.model.request.user;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 取消拉黑用户请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class BlackListCancelRequest extends BasicRequest {

  @SerializedName("openid_list")
  private List<String> list;

  public BlackListCancelRequest() {
    this.path = ApiAddress.URL_BLACK_LIST_APPEND;
  }

  /**
   * 设置OpenId列表，如列表大于20个，设置失败.
   * 
   * @param list
   *          open_id list
   */
  public void setList(List<String> list) {
    this.list = list;
  }

  /**
   * 添加一个OpenId，如果列表中OpenId已经到20个，不再继续添加到列表中.
   * 
   * @param openId
   *          open_id
   */
  public void add(String openId) {
    if (this.list == null) {
      this.list = Lists.newArrayList();
    }
    this.list.add(openId);
  }

}
