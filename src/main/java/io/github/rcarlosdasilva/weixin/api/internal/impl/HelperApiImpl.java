package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.List;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.HelperApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.core.cache.impl.MixCacheHandler;

/**
 * 相关工具实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class HelperApiImpl extends BasicApi implements HelperApi {

  public HelperApiImpl(String accountKey) {
    this.accountKey = accountKey;
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
    } catch (Exception e) {
      return false;
    }
  }

}
