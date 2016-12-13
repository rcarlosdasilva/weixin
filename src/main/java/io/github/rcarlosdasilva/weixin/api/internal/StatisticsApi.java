package io.github.rcarlosdasilva.weixin.api.internal;

import java.util.Date;

import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetInterfaceSummaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetMessageDistributedResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetMessageSummaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetNewsSummaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.statistics.StatisticsGetUserSummaryResponse;

/**
 * 数据统计相关API
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface StatisticsApi {

  /**
   * 获取用户增减数据，最大时间跨度7.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetUserSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN"
   *      >用户分析数据接口</a>
   */
  StatisticsGetUserSummaryResponse getUserSummary(Date begin, Date end);

  /**
   * 获取累计用户数据，最大时间跨度7.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetUserSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141082&token=&lang=zh_CN"
   *      >用户分析数据接口</a>
   */
  StatisticsGetUserSummaryResponse getUserCumulate(Date begin, Date end);

  /**
   * 获取图文群发每日数据，最大时间跨度1.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN"
   *      >图文分析数据接口</a>
   */
  StatisticsGetNewsSummaryResponse getNewsSummary(Date begin, Date end);

  /**
   * 获取图文群发总数据，最大时间跨度1.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN"
   *      >图文分析数据接口</a>
   */
  StatisticsGetNewsSummaryResponse getNewsTotal(Date begin, Date end);

  /**
   * 获取图文统计数据，最大时间跨度3.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN"
   *      >图文分析数据接口</a>
   */
  StatisticsGetNewsSummaryResponse getNewsRead(Date begin, Date end);

  /**
   * 获取图文统计分时数据，最大时间跨度1.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN"
   *      >图文分析数据接口</a>
   */
  StatisticsGetNewsSummaryResponse getNewsReadHour(Date begin, Date end);

  /**
   * 获取图文分享转发数据，最大时间跨度7.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN"
   *      >图文分析数据接口</a>
   */
  StatisticsGetNewsSummaryResponse getNewsShare(Date begin, Date end);

  /**
   * 获取图文分享转发分时数据，最大时间跨度1.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141084&token=&lang=zh_CN"
   *      >图文分析数据接口</a>
   */
  StatisticsGetNewsSummaryResponse getNewsShareHour(Date begin, Date end);

  /**
   * 获取消息发送概况数据，最大时间跨度7.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN"
   *      >消息分析数据接口</a>
   */
  StatisticsGetMessageSummaryResponse getMessageSummary(Date begin, Date end);

  /**
   * 获取消息分送分时数据，最大时间跨度1.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN"
   *      >消息分析数据接口</a>
   */
  StatisticsGetMessageSummaryResponse getMessageSummaryHour(Date begin, Date end);

  /**
   * 获取消息发送周数据，最大时间跨度30.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN"
   *      >消息分析数据接口</a>
   */
  StatisticsGetMessageSummaryResponse getMessageSummaryWeek(Date begin, Date end);

  /**
   * 获取消息发送月数据，最大时间跨度30.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN"
   *      >消息分析数据接口</a>
   */
  StatisticsGetMessageSummaryResponse getMessageSummaryMonth(Date begin, Date end);

  /**
   * 获取消息发送分布数据，最大时间跨度15.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN"
   *      >消息分析数据接口</a>
   */
  StatisticsGetMessageDistributedResponse getMessageDistributed(Date begin, Date end);

  /**
   * 获取消息发送分布周数据，最大时间跨度30.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN"
   *      >消息分析数据接口</a>
   */
  StatisticsGetMessageDistributedResponse getMessageDistributedWeek(Date begin, Date end);

  /**
   * 获取消息发送分布月数据，最大时间跨度30.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141085&token=&lang=zh_CN"
   *      >消息分析数据接口</a>
   */
  StatisticsGetMessageDistributedResponse getMessageDistributedMonth(Date begin, Date end);

  /**
   * 获取接口分析数据，最大时间跨度30.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141086&token=&lang=zh_CN"
   *      >接口分析数据接口</a>
   */
  StatisticsGetInterfaceSummaryResponse getInterfaceSummary(Date begin, Date end);

  /**
   * 获取接口分析分时数据，最大时间跨度1.
   * 
   * @param begin
   *          获取数据的起始日期，begin_date和end_date的差值需小于“最大时间跨度”
   *          （比如最大时间跨度为1时，begin_date和end_date的差值只能为0，才能小于1），否则会报错
   * @param end
   *          获取数据的结束日期，end_date允许设置的最大值为昨日
   * @return see {@link StatisticsGetNewsSummaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141086&token=&lang=zh_CN"
   *      >接口分析数据接口</a>
   */
  StatisticsGetInterfaceSummaryResponse getInterfaceSummaryHour(Date begin, Date end);

}
