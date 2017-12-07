package io.github.rcarlosdasilva.weixin.api.weixin.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.rcarlosdasilva.weixin.api.BasicApi;
import io.github.rcarlosdasilva.weixin.api.weixin.HelperApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.ResultCode;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.core.cache.CacheHandler;
import io.github.rcarlosdasilva.weixin.core.cache.GeneralCacheableObject;
import io.github.rcarlosdasilva.weixin.core.exception.ExecuteException;
import io.github.rcarlosdasilva.weixin.model.request.helper.HelperResetQuotaRequest;

/**
 * 相关工具实现
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class HelperApiImpl extends BasicApi implements HelperApi {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public HelperApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public boolean resetQuota() {
    HelperResetQuotaRequest requestModel = new HelperResetQuotaRequest();
    requestModel.setAppId(Registry.lookup(accountKey).getAppId());

    try {
      return post(Boolean.class, requestModel);
    } catch (ExecuteException ex) {
      if (ex.getCode() != null && ex.getCode() == ResultCode.RESULT_48006) {
        return false;
      }
      throw ex;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean isLegalRequestIp(String ip) {
    GeneralCacheableObject cacheableObject = CacheHandler.of(GeneralCacheableObject.class)
        .get(Convention.WEIXIN_IP_CACHE_KEY);
    final List<String> ips;
    if (cacheableObject != null) {
      ips = (List<String>) cacheableObject.getObj();
    } else {
      ips = Weixin.with(this.accountKey).common().getWeixinIps();
      CacheHandler.of(GeneralCacheableObject.class).put(Convention.WEIXIN_IP_CACHE_KEY,
          new GeneralCacheableObject(ips));
    }

    return ips.contains(ip.trim());
  }

  @Override
  public boolean isUsable() {
    try {
      Weixin.with(accountKey).certificate().askAccessToken();
      return true;
    } catch (Exception ex) {
      logger.trace("weixin helper api", ex);
      return false;
    }
  }

}
