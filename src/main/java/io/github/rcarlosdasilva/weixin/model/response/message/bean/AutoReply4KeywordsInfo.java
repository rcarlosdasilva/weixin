package io.github.rcarlosdasilva.weixin.model.response.message.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AutoReply4KeywordsInfo {

  @SerializedName("list")
  private List<KeywordReplyRule> rules;

  public List<KeywordReplyRule> getRules() {
    return rules;
  }

}
