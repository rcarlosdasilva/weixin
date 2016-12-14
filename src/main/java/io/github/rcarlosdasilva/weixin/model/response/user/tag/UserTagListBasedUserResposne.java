package io.github.rcarlosdasilva.weixin.model.response.user.tag;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UserTagListBasedUserResposne {

  @SerializedName("tagid_list")
  private List<Integer> tags;

  /**
   * 标签id列表.
   * 
   * @return list of tag id
   */
  public List<Integer> getTags() {
    return tags;
  }

}
