package io.github.rcarlosdasilva.weixin.model.response.statistics;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.statistics.bean.InterfaceSummary;

public class StatisticsGetInterfaceSummaryResponse {

  private List<InterfaceSummary> list;

  /**
   * 获取数据集合.
   * 
   * @return list of {@link InterfaceSummary}
   */
  public List<InterfaceSummary> getList() {
    return list;
  }

}
