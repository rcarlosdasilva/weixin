package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.Date;

import com.google.common.base.Preconditions;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.StatisticsApi;
import io.github.rcarlosdasilva.weixin.model.request.statistics.StatisticsGetRequest;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetInterfaceSummaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetMessageDistributedResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetMessageSummaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetNewsSummaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetUserSummaryResponse;

/**
 * 数据统计相关API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class StatisticsApiImpl extends BasicApi implements StatisticsApi {

  public StatisticsApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public StatisticsGetUserSummaryResponse getUserSummary(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forUserSummary();

    return post(StatisticsGetUserSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetUserSummaryResponse getUserCumulate(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forUserCumulate();

    return post(StatisticsGetUserSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetNewsSummaryResponse getNewsSummary(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forNewsSummary();

    return post(StatisticsGetNewsSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetNewsSummaryResponse getNewsTotal(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forNewsTotal();

    return post(StatisticsGetNewsSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetNewsSummaryResponse getNewsRead(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forNewsRead();

    return post(StatisticsGetNewsSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetNewsSummaryResponse getNewsReadHour(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forNewsReadHour();

    return post(StatisticsGetNewsSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetNewsSummaryResponse getNewsShare(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forNewsShare();

    return post(StatisticsGetNewsSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetNewsSummaryResponse getNewsShareHour(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forNewsShareHour();

    return post(StatisticsGetNewsSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetMessageSummaryResponse getMessageSummary(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forMessageSummary();

    return post(StatisticsGetMessageSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetMessageSummaryResponse getMessageSummaryHour(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forMessageHour();

    return post(StatisticsGetMessageSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetMessageSummaryResponse getMessageSummaryMonth(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forMessageWeek();

    return post(StatisticsGetMessageSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetMessageSummaryResponse getMessageSummaryWeek(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forMessageMonth();

    return post(StatisticsGetMessageSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetMessageDistributedResponse getMessageDistributed(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forMessageDistributed();

    return post(StatisticsGetMessageDistributedResponse.class, requestModel);
  }

  @Override
  public StatisticsGetMessageDistributedResponse getMessageDistributedMonth(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forMessageDistributedWeek();

    return post(StatisticsGetMessageDistributedResponse.class, requestModel);
  }

  @Override
  public StatisticsGetMessageDistributedResponse getMessageDistributedWeek(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forMessageDistributedMonth();

    return post(StatisticsGetMessageDistributedResponse.class, requestModel);
  }

  @Override
  public StatisticsGetInterfaceSummaryResponse getInterfaceSummary(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forInterfaceSummary();

    return post(StatisticsGetInterfaceSummaryResponse.class, requestModel);
  }

  @Override
  public StatisticsGetInterfaceSummaryResponse getInterfaceSummaryHour(Date begin, Date end) {
    StatisticsGetRequest requestModel = setupRequestModel(begin, end);
    requestModel.forInterfaceSummaryHour();

    return post(StatisticsGetInterfaceSummaryResponse.class, requestModel);
  }

  private StatisticsGetRequest setupRequestModel(Date begin, Date end) {
    Preconditions.checkArgument(begin.before(end), "Begin time should be before end time");

    StatisticsGetRequest requestModel = new StatisticsGetRequest();
    requestModel.setBegin(begin);
    requestModel.setEnd(end);

    return requestModel;
  }

}
