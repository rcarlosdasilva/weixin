package io.github.rcarlosdasilva.weixin.core.task;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 微信访问凭证（access_token, js_ticket）巡查器，可轮询凭证的有效性，并主动更新凭证
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CertificationInspector {

  private static final Logger logger = LoggerFactory.getLogger(CertificationInspector.class);

  private static final long SCHEDULE_EXECUTE_IMMEDIATELY = 0L;
  private static final long SCHEDULE_EXECUTE_PERIOD_TIME = 900L;

  private static final ScheduledThreadPoolExecutor executor;
  private static final Map<String, ScheduledFuture<?>> futures;
  private static final AccessTokenChecker accessTokenChecker;
  private static final JsTicketChecker jsTicketChecker;

  private CertificationInspector() {

  }

  static {
    executor = new ScheduledThreadPoolExecutor(2);
    futures = Maps.newHashMap();
    accessTokenChecker = new AccessTokenChecker();
    jsTicketChecker = new JsTicketChecker();
  }

  /**
   * 启动巡查器，需要手动开启，默认900秒间隔执行
   */
  public static void start() {
    start(SCHEDULE_EXECUTE_PERIOD_TIME);
  }

  /**
   * 启动巡查器，需要手动开启
   * 
   * @param periodTime
   *          微信凭证巡查器执行间隔时间，单位秒
   */
  public static void start(long periodTime) {
    logger.debug("启动巡查器");

    ScheduledFuture<?> atFuture = executor.scheduleAtFixedRate(accessTokenChecker,
        SCHEDULE_EXECUTE_IMMEDIATELY, periodTime, TimeUnit.SECONDS);
    futures.put(accessTokenChecker.getClass().toString(), atFuture);

    ScheduledFuture<?> jtFuture = executor.scheduleAtFixedRate(jsTicketChecker,
        SCHEDULE_EXECUTE_IMMEDIATELY, periodTime, TimeUnit.SECONDS);
    futures.put(jsTicketChecker.getClass().toString(), jtFuture);
  }

  /**
   * 停止巡查器
   */
  public static void stop() {
    for (String key : futures.keySet()) {
      ScheduledFuture<?> future = futures.get(key);

      if (future == null) {
        logger.error("找不到对应任务");
        continue;
      }

      boolean cancelled = future.cancel(true);
      if (!cancelled) {
        logger.error("无法停止任务，一般情况下因为任务已执行完毕");
      }

      futures.remove(key);
    }
  }

}
