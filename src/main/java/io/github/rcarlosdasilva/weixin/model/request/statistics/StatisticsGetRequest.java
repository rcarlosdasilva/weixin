package io.github.rcarlosdasilva.weixin.model.request.statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicWeixinRequest;

/**
 * 数据统计请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class StatisticsGetRequest extends BasicWeixinRequest {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @SerializedName("begin_date")
  private String begin;
  @SerializedName("end_date")
  private String end;

  public void forUserSummary() {
    this.path = ApiAddress.URL_STATISTICS_USER_SUMMARY;
  }

  public void forUserCumulate() {
    this.path = ApiAddress.URL_STATISTICS_USER_CUMULATE;
  }

  public void forNewsSummary() {
    this.path = ApiAddress.URL_STATISTICS_NEWS_SUMMARY;
  }

  public void forNewsTotal() {
    this.path = ApiAddress.URL_STATISTICS_NEWS_TOTAL;
  }

  public void forNewsRead() {
    this.path = ApiAddress.URL_STATISTICS_NEWS_READ;
  }

  public void forNewsReadHour() {
    this.path = ApiAddress.URL_STATISTICS_NEWS_READ_HOUR;
  }

  public void forNewsShare() {
    this.path = ApiAddress.URL_STATISTICS_NEWS_SHARE;
  }

  public void forNewsShareHour() {
    this.path = ApiAddress.URL_STATISTICS_NEWS_SHARE_HOUR;
  }

  public void forMessageSummary() {
    this.path = ApiAddress.URL_STATISTICS_MESSAGE_SUMMARY;
  }

  public void forMessageHour() {
    this.path = ApiAddress.URL_STATISTICS_MESSAGE_HOUR;
  }

  public void forMessageWeek() {
    this.path = ApiAddress.URL_STATISTICS_MESSAGE_WEEK;
  }

  public void forMessageMonth() {
    this.path = ApiAddress.URL_STATISTICS_MESSAGE_MONTH;
  }

  public void forMessageDistributed() {
    this.path = ApiAddress.URL_STATISTICS_MESSAGE_DISTRIBUTED;
  }

  public void forMessageDistributedWeek() {
    this.path = ApiAddress.URL_STATISTICS_MESSAGE_DISTRIBUTED_WEEK;
  }

  public void forMessageDistributedMonth() {
    this.path = ApiAddress.URL_STATISTICS_MESSAGE_DISTRIBUTED_MONTH;
  }

  public void forInterfaceSummary() {
    this.path = ApiAddress.URL_STATISTICS_INTERFACE_SUMMARY;
  }

  public void forInterfaceSummaryHour() {
    this.path = ApiAddress.URL_STATISTICS_INTERFACE_SUMMARY_HOUR;
  }

  /**
   * DateFormats在多线程下是不安全的，容易造成异常
   * 
   * @param begin
   *          begin
   */
  public synchronized void setBegin(Date begin) {
    this.begin = StatisticsGetRequest.DATE_FORMAT.format(begin);
  }

  public synchronized void setEnd(Date end) {
    this.end = StatisticsGetRequest.DATE_FORMAT.format(end);
  }

}
