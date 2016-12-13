package io.github.rcarlosdasilva.weixin.model.response.statistics;

import java.util.List;

import io.github.rcarlosdasilva.weixin.model.response.statistics.bean.MessageDistributed;

public class StatisticsGetMessageDistributedResponse {

  private List<MessageDistributed> list;

  /**
   * 获取数据集合.
   */
  public List<MessageDistributed> getList() {
    return list;
  }

}
