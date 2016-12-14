package io.github.rcarlosdasilva.weixin.model.response.statistics;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.statistics.bean.UserSummary;

public class StatisticsGetUserSummaryResponse {

  private List<UserSummary> list;

  /**
   * 获取数据集合.
   * 
   * @return list of {@link UserSummary}
   */
  public List<UserSummary> getList() {
    return list;
  }

}
