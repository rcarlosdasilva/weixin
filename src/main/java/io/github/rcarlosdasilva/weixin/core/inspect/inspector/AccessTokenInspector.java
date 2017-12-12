package io.github.rcarlosdasilva.weixin.core.inspect.inspector;

import java.util.List;

import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.Lookup;
import io.github.rcarlosdasilva.weixin.core.inspect.Inspector;
import io.github.rcarlosdasilva.weixin.core.inspect.ProblemObject;
import io.github.rcarlosdasilva.weixin.core.inspect.WorkSheet;
import io.github.rcarlosdasilva.weixin.model.AccessToken;

public class AccessTokenInspector implements Inspector {

  private static final String MARK = AccessToken.class.getName();

  @Override
  public ProblemObject<?> inspect() {
    if (WorkSheet.isWorking(MARK)) {
      return null;
    }

    List<AccessToken> results = CacheHandler.of(AccessToken.class)
        .lookupAll(new Lookup<AccessToken>() {

          @Override
          public boolean isYou(String key, AccessToken obj) {
            return obj.isExpired();
          }
        });

    if (results == null || results.isEmpty()) {
      return null;
    }

    ProblemObject<AccessToken> problem = new ProblemObject<>(AccessToken.class, MARK);
    problem.setDefaultObjects(results);
    return problem;
  }

}
