package io.github.rcarlosdasilva.weixin.model.request

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.handler.Freeze
import io.github.rcarlosdasilva.weixin.terms.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 数据统计请求模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class StatisticsRequest(
  @Freeze private val beginTime: Date,
  @Freeze private val endTime: Date
) : MpRequest() {

  @SerializedName("begin_date")
  private val begin: String = DATE_FORMAT.format(beginTime)
  @SerializedName("end_date")
  private val end: String = DATE_FORMAT.format(endTime)

  fun forUserSummary() {
    this.path = URL_STATISTICS_USER_SUMMARY
  }

  fun forUserCumulate() {
    this.path = URL_STATISTICS_USER_CUMULATE
  }

  fun forNewsSummary() {
    this.path = URL_STATISTICS_NEWS_SUMMARY
  }

  fun forNewsTotal() {
    this.path = URL_STATISTICS_NEWS_TOTAL
  }

  fun forNewsRead() {
    this.path = URL_STATISTICS_NEWS_READ
  }

  fun forNewsReadHour() {
    this.path = URL_STATISTICS_NEWS_READ_HOUR
  }

  fun forNewsShare() {
    this.path = URL_STATISTICS_NEWS_SHARE
  }

  fun forNewsShareHour() {
    this.path = URL_STATISTICS_NEWS_SHARE_HOUR
  }

  fun forMessageSummary() {
    this.path = URL_STATISTICS_MESSAGE_SUMMARY
  }

  fun forMessageHour() {
    this.path = URL_STATISTICS_MESSAGE_HOUR
  }

  fun forMessageWeek() {
    this.path = URL_STATISTICS_MESSAGE_WEEK
  }

  fun forMessageMonth() {
    this.path = URL_STATISTICS_MESSAGE_MONTH
  }

  fun forMessageDistributed() {
    this.path = URL_STATISTICS_MESSAGE_DISTRIBUTED
  }

  fun forMessageDistributedWeek() {
    this.path = URL_STATISTICS_MESSAGE_DISTRIBUTED_WEEK
  }

  fun forMessageDistributedMonth() {
    this.path = URL_STATISTICS_MESSAGE_DISTRIBUTED_MONTH
  }

  fun forInterfaceSummary() {
    this.path = URL_STATISTICS_INTERFACE_SUMMARY
  }

  fun forInterfaceSummaryHour() {
    this.path = URL_STATISTICS_INTERFACE_SUMMARY_HOUR
  }

  companion object {
    private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")
  }
}