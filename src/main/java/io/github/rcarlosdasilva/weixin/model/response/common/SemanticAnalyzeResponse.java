package io.github.rcarlosdasilva.weixin.model.response.common;

import io.github.rcarlosdasilva.weixin.model.response.common.bean.Semantic;
import io.github.rcarlosdasilva.weixin.model.response.common.bean.SemanticResult;

public class SemanticAnalyzeResponse {

  private String query;
  private String type;
  private Semantic semantic;
  private SemanticResult result;
  private String answer;
  private String text;

  /**
   * 用户的输入字符串.
   */
  public String getQuery() {
    return query;
  }

  /**
   * 服务的全局类型id，详见协议文档中垂直服务协议定义.
   */
  public String getType() {
    return type;
  }

  /**
   * 语义理解后的结构化标识，各服务不同.
   */
  public Semantic getSemantic() {
    return semantic;
  }

  /**
   * 部分类别的结果.
   */
  public SemanticResult getResult() {
    return result;
  }

  /**
   * 部分类别的结果html5展示，目前不支持.
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * 特殊回复说明.
   */
  public String getText() {
    return text;
  }

}
