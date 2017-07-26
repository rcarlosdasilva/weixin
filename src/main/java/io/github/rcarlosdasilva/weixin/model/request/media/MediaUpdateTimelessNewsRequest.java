package io.github.rcarlosdasilva.weixin.model.request.media;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.bean.Article;

/**
 * 修改永久图文素材请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
@SuppressWarnings("unused")
public class MediaUpdateTimelessNewsRequest extends BasicWeixinRequest {

  private String mediaId;
  private int index;
  @SerializedName("articles")
  private Article article;

  public MediaUpdateTimelessNewsRequest() {
    this.path = ApiAddress.URL_MEDIA_TIMELESS_UPDATE;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

}
