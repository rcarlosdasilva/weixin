package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.HelperApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.ResultCode;
import io.github.rcarlosdasilva.weixin.core.cache.impl.MixCacheHandler;
import io.github.rcarlosdasilva.weixin.core.exception.ExecuteException;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import io.github.rcarlosdasilva.weixin.model.request.helper.HelperResetQuotaRequest;

/**
 * 相关工具实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class HelperApiImpl extends BasicApi implements HelperApi {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public HelperApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public boolean resetQuota() {
    HelperResetQuotaRequest requestModel = new HelperResetQuotaRequest();
    requestModel.setAppId(Registration.account(accountKey).getAppId());

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
    Object obj = MixCacheHandler.getInstance().get(Convention.WEIXIN_IP_CACHE_KEY);
    final List<String> ips;
    if (obj != null && (obj instanceof List)) {
      ips = (List<String>) obj;
    } else {
      ips = Weixin.with(this.accountKey).common().getWeixinIps();
      MixCacheHandler.getInstance().put(Convention.WEIXIN_IP_CACHE_KEY, ips);
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
