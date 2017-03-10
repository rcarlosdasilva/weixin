package io.github.rcarlosdasilva.weixin.test;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerTest {

  public static void start() {
    ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
    Timest xx1 = new TimerTest().new Timest("A");
    Timest xx2 = new TimerTest().new Timest("B");
    Timest xx3 = new TimerTest().new Timest("C");
    ScheduledFuture<?> aa = stpe.schedule(xx1, 5, TimeUnit.SECONDS);
    ScheduledFuture<?> bb = stpe.schedule(xx2, 10, TimeUnit.SECONDS);
    ScheduledFuture<?> cc = stpe.schedule(xx3, 15, TimeUnit.SECONDS);

    bb.cancel(true);
  }

  public static void main(String[] args) {
    TimerTest.start();
  }

  class Timest implements Runnable {

    private String abc;

    public Timest(String abc) {
      this.abc = abc;
    }

    @Override
    public void run() {
      System.out.println("runing after ..." + abc);
    }

  }

}