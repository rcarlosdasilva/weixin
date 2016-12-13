package io.github.rcarlosdasilva.weixin.model.response.media.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ArticleCollection {

  @SerializedName("news_item")
  private List<Article> articles;

  /**
   * 图文列表.
   */
  public List<Article> getArticles() {
    return articles;
  }

}
