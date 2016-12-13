package io.github.rcarlosdasilva.weixin.model.response.statistics;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.statistics.bean.MessageSummary;

public class StatisticsGetMessageSummaryResponse {

  private List<MessageSummary> list;

  /**
   * 获取数据集合.
   */
  public List<MessageSummary> getList() {
    return list;
  }

}
