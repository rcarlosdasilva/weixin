package io.github.rcarlosdasilva.weixin.model.response.menu.bean.complate;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MediaCollection {

  @SerializedName("list")
  private List<Media> media;

  /**
   * 多媒体列表.
   * 
   * @return list of {@link Media}
   */
  public List<Media> getMedia() {
    return media;
  }

}
