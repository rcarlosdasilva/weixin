package io.github.rcarlosdasilva.weixin.model.response.message.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class KeywordReplyRule {

  private String ruleName;
  private long createTime;
  private String replyMode;
  @SerializedName("keyword_list_info")
  private List<Keyword> keywords;
  @SerializedName("reply_list_info")
  private List<Reply> replies;

  /**
   * 规则名称.
   * 
   * @return rule
   */
  public String getRuleName() {
    return ruleName;
  }

  /**
   * 创建时间.
   * 
   * @return time
   */
  public long getCreateTime() {
    return createTime;
  }

  /**
   * 回复模式，reply_all代表全部回复，random_one代表随机回复其中一条.
   * 
   * @return mode
   */
  public String getReplyMode() {
    return replyMode;
  }

  /**
   * 匹配的关键词列表.
   * 
   * @return list of {@link Keyword}
   */
  public List<Keyword> getKeywords() {
    return keywords;
  }

  /**
   * 回复列表.
   * 
   * @return list of {@link Reply}
   */
  public List<Reply> getReplies() {
    return replies;
  }

}
