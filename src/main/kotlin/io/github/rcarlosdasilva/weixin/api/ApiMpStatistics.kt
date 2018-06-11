package io.github.rcarlosdasilva.weixin.api

import io.github.rcarlosdasilva.weixin.model.Mp
import io.github.rcarlosdasilva.weixin.model.request.StatisticsRequest
import io.github.rcarlosdasilva.weixin.model.response.*
import java.util.*

/**
 * 公众号数据统计相关API
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class ApiMpStatistics(account: Mp) : Api(account) {

  /**
   * 获取用户增减数据，最大时间跨度7
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsUserSummaryResponse]
   */
  fun getUserSummary(begin: Date, end: Date): StatisticsUserSummaryResponse =
      post(StatisticsUserSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forUserSummary() })

  /**
   * 获取累计用户数据，最大时间跨度7
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsUserSummaryResponse]
   */
  fun getUserCumulate(begin: Date, end: Date): StatisticsUserSummaryResponse =
      post(StatisticsUserSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forUserCumulate() })

  /**
   * 获取图文群发每日数据，最大时间跨度1
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getNewsSummary(begin: Date, end: Date): StatisticsNewsSummaryResponse =
      post(StatisticsNewsSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forNewsSummary() })

  /**
   * 获取图文群发总数据，最大时间跨度1
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getNewsTotal(begin: Date, end: Date): StatisticsNewsSummaryResponse =
      post(StatisticsNewsSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forNewsTotal() })

  /**
   * 获取图文统计数据，最大时间跨度3
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getNewsRead(begin: Date, end: Date): StatisticsNewsSummaryResponse =
      post(StatisticsNewsSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forNewsRead() })

  /**
   * 获取图文统计分时数据，最大时间跨度1
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getNewsReadHour(begin: Date, end: Date): StatisticsNewsSummaryResponse =
      post(StatisticsNewsSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forNewsReadHour() })

  /**
   * 获取图文分享转发数据，最大时间跨度7
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getNewsShare(begin: Date, end: Date): StatisticsNewsSummaryResponse =
      post(StatisticsNewsSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forNewsShare() })

  /**
   * 获取图文分享转发分时数据，最大时间跨度1
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getNewsShareHour(begin: Date, end: Date): StatisticsNewsSummaryResponse =
      post(StatisticsNewsSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forNewsShareHour() })

  /**
   * 获取消息发送概况数据，最大时间跨度7
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getMessageSummary(begin: Date, end: Date): StatisticsMessageSummaryResponse =
      post(StatisticsMessageSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forMessageSummary() })

  /**
   * 获取消息分送分时数据，最大时间跨度1
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getMessageSummaryHour(begin: Date, end: Date): StatisticsMessageSummaryResponse =
      post(StatisticsMessageSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forMessageHour() })

  /**
   * 获取消息发送周数据，最大时间跨度30
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getMessageSummaryWeek(begin: Date, end: Date): StatisticsMessageSummaryResponse =
      post(StatisticsMessageSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forMessageWeek() })

  /**
   * 获取消息发送月数据，最大时间跨度30
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getMessageSummaryMonth(begin: Date, end: Date): StatisticsMessageSummaryResponse =
      post(StatisticsMessageSummaryResponse::class.java, StatisticsRequest(begin, end).also { it.forMessageMonth() })

  /**
   * 获取消息发送分布数据，最大时间跨度15
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getMessageDistributed(begin: Date, end: Date): StatisticsMessageDistributedResponse =
      post(
          StatisticsMessageDistributedResponse::class.java,
          StatisticsRequest(begin, end).also { it.forMessageDistributed() })

  /**
   * 获取消息发送分布周数据，最大时间跨度30
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getMessageDistributedWeek(begin: Date, end: Date): StatisticsMessageDistributedResponse =
      post(
          StatisticsMessageDistributedResponse::class.java,
          StatisticsRequest(begin, end).also { it.forMessageDistributedWeek() })

  /**
   * 获取消息发送分布月数据，最大时间跨度30
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getMessageDistributedMonth(begin: Date, end: Date): StatisticsMessageDistributedResponse =
      post(
          StatisticsMessageDistributedResponse::class.java,
          StatisticsRequest(begin, end).also { it.forMessageDistributedMonth() })

  /**
   * 获取接口分析数据，最大时间跨度30
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getInterfaceSummary(begin: Date, end: Date): StatisticsInterfaceSummaryResponse =
      post(
          StatisticsInterfaceSummaryResponse::class.java,
          StatisticsRequest(begin, end).also { it.forInterfaceSummary() })

  /**
   * 获取接口分析分时数据，最大时间跨度1
   *
   * @param begin 获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   * （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end 获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see [StatisticsNewsSummaryResponse]
   */
  fun getInterfaceSummaryHour(begin: Date, end: Date): StatisticsInterfaceSummaryResponse =
      post(
          StatisticsInterfaceSummaryResponse::class.java,
          StatisticsRequest(begin, end).also { it.forInterfaceSummaryHour() })

}