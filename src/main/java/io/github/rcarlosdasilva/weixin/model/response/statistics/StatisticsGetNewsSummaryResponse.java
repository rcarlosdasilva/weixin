package io.github.rcarlosdasilva.weixin.model.response.statistics;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.statistics.bean.NewsSummary;

public class StatisticsGetNewsSummaryResponse {

  private List<NewsSummary> list;

  /**
   * 获取数据集合.
   */
  public List<NewsSummary> getList() {
    return list;
  }

}
