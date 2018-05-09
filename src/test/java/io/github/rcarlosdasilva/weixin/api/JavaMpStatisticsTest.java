package io.github.rcarlosdasilva.weixin.api;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.response.StatisticsInterfaceSummaryResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaMpStatisticsTest {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static String key;

  @BeforeClass
  public static void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  @Test
  public void test01() {
    Calendar ca = Calendar.getInstance();
    ca.add(Calendar.DAY_OF_MONTH, -1);
    Date end = ca.getTime();
    ca.add(Calendar.DAY_OF_MONTH, -10);
    Date begin = ca.getTime();

    StatisticsInterfaceSummaryResponse response = Weixin.mp(key).getStatistics().getInterfaceSummary(begin, end);
    Assert.assertNotNull(response);
  }

}
