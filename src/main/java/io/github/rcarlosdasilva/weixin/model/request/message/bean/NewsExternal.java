package io.github.rcarlosdasilva.weixin.model.request.message.bean;

import java.util.List;

import com.google.common.collect.Lists;

public class NewsExternal implements Message {

  List<Article> articles;

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }

  /**
   * 添加.
   */
  public void addArticle(Article article) {
    if (this.articles == null) {
      this.articles = Lists.newArrayList();
    }
    this.articles.add(article);
  }

}
