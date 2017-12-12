package io.github.rcarlosdasilva.weixin.core.inspect;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ImplementingClassMatchProcessor;
import io.github.rcarlosdasilva.weixin.core.Registry;

@SuppressWarnings("rawtypes")
public class InspectDispatcher implements Runnable {

  private final long interval;
  private final List<Inspector> inspectors;
  private final List<Worker> workers;

  private InspectDispatcher() {
    String packageName = this.getClass().getPackage().getName();

    interval = Registry.setting().getInspectDispatchIntervalInMill();
    inspectors = Lists.newArrayList();
    workers = Lists.newArrayList();

    FastClasspathScanner fcs = new FastClasspathScanner(packageName);
    fcs.matchClassesImplementing(Inspector.class, new ImplementingClassMatchProcessor<Inspector>() {

      @Override
      public void processMatch(Class<? extends Inspector> implementingClass) {
        try {
          inspectors.add(implementingClass.newInstance());
        } catch (InstantiationException | IllegalAccessException ex) {
          // do nothing
        }
      }
    });

    fcs.matchClassesImplementing(Worker.class, new ImplementingClassMatchProcessor<Worker>() {

      @Override
      public void processMatch(Class<? extends Worker> implementingClass) {
        try {
          workers.add(implementingClass.newInstance());
        } catch (InstantiationException | IllegalAccessException ex) {
          // do nothing
        }
      }
    });

    fcs.scan();
  }

  public static void main(String[] args) {
    InspectDispatcher.startup();
  }

  public static void startup() {
    new Thread(new InspectDispatcher()).start();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      for (Inspector inspector : inspectors) {
        ProblemObject<?> problem = inspector.inspect();
        if (problem == null) {
          continue;
        }

        for (Worker worker : workers) {
          if (worker.support(problem)) {
            worker.doit(problem);
          }
        }
      }

      try {
        Thread.sleep(interval);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
  }

}
