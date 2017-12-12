package io.github.rcarlosdasilva.weixin.core.inspect;

import java.util.concurrent.ConcurrentHashMap;

public final class WorkSheet extends ConcurrentHashMap<String, Boolean> {

  private static final long serialVersionUID = 6640735131643513587L;

  private static final WorkSheet INSTANCE = new WorkSheet();

  private WorkSheet() {
  }

  public static void start(String mark) {
    INSTANCE.put(mark, true);
  }

  public static boolean isWorking(String mark) {
    return INSTANCE.containsKey(mark) && INSTANCE.get(mark);
  }

  public static void done(String mark) {
    INSTANCE.put(mark, false);
  }

}
