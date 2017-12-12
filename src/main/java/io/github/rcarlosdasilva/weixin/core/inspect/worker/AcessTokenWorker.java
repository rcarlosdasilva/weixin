package io.github.rcarlosdasilva.weixin.core.inspect.worker;

import java.util.Collection;
import java.util.Iterator;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.OpenPlatform;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.core.inspect.ProblemObject;
import io.github.rcarlosdasilva.weixin.core.inspect.WorkSheet;
import io.github.rcarlosdasilva.weixin.core.inspect.Worker;
import io.github.rcarlosdasilva.weixin.model.AccessToken;

public class AcessTokenWorker implements Worker<AccessToken> {

  @Override
  public boolean support(ProblemObject<AccessToken> problem) {
    return AccessToken.class == problem.getType();
  }

  @Override
  public void doit(final ProblemObject<AccessToken> problem) {
    if (WorkSheet.isWorking(problem.getMark())) {
      return;
    }

    WorkSheet.start(problem.getMark());
    new Thread(new Runnable() {

      @Override
      public void run() {
        Collection<AccessToken> accessTokens = problem.getDefaultObjects();
        if (accessTokens != null && !accessTokens.isEmpty()) {
          Iterator<AccessToken> iterator = accessTokens.iterator();
          while (iterator.hasNext()) {
            AccessToken accessToken = iterator.next();
            String key = accessToken.getAccountMark();

            if (Convention.DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN.equals(key)) {
              // 开放平台
              OpenPlatform.certificate().askAccessToken();
            } else {
              // 公众号
              Weixin.with(key).certificate().askAccessToken();
            }
          }
        }
        WorkSheet.done(problem.getMark());
      }
    }).start();
  }

}
