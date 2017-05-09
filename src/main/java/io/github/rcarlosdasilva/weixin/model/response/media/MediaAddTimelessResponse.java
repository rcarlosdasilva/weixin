package io.github.rcarlosdasilva.weixin.model.response.media;

import com.google.gson.annotations.SerializedName;

public class MediaAddTimelessResponse {

  @SerializedName("media_id")
  private String mediaId;
  private String url;

  /**
   * 新增的永久素材的media_id.
   * 
   * @return media id
   */
  public String getMediaId() {
    return mediaId;
  }

  /**
   * 新增的图片素材的图片URL（仅新增图片素材时会返回该字段）.
   * 
   * @return url
   */
  public String getUrl() {
    return url;
  }

}
