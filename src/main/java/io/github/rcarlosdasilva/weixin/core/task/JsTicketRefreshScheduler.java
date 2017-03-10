package io.github.rcarlosdasilva.weixin.core.task;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.rcarlosdasilva.weixin.api.Weixin;

public class JsTicketRefreshScheduler {

  private static final Logger logger = LoggerFactory.getLogger(JsTicketRefreshScheduler.class);
  private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
  private static final Map<String, ScheduledFuture<?>> futures = Maps.newHashMap();

  /**
   * 订阅自动刷新jsapi_ticket任务，一般用于在获取新的jsapi_ticket后，于过期之前定时执行刷新jsapi_ticket，以保证缓存中的jsapi_ticket一致处于可用状态.
   * 
   * @param key
   *          自动刷新标识
   * @param 延迟多久后执行，单位秒
   */
  public static void subscribe(String key, long delay) {
    logger.debug("正在订阅自动刷新access_token作业");

    ScheduledFuture<?> future = executor.schedule(new Runnable() {

      @Override
      public void run() {
        Weixin.with(key).certificate().refreshJsTicket();
      }

    }, delay, TimeUnit.SECONDS);

    futures.put(key, future);
  }

  /**
   * 退订自动刷新jsapi_ticket任务，一般用于强制在强制刷新jsapi_ticket时退订，
   * 因为在获取新的jsapi_ticket后会订阅新的自动刷新任务，退订可以防止不正确的jsapi_ticket自动刷新逻辑.
   * 
   * @param key
   *          自动刷新标识
   */
  public static void unsubscribe(String key) {
    logger.info("正在退订自动刷新jsapi_ticket作业");

    ScheduledFuture<?> future = futures.get(key);
    if (future == null) {
      logger.error("无法停止jsapi_ticket自动刷新任务，找不到对应任务");
      return;
    }

    boolean cancelled = future.cancel(true);
    if (!cancelled) {
      logger.error("无法停止jsapi_ticket自动刷新任务，一般情况下因为任务已执行完毕");
    }

    futures.remove(key);
  }

}
