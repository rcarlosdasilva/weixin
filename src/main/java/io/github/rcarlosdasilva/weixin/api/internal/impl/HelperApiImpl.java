package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.base.Strings;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.HelperApi;
import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.Utils;
import io.github.rcarlosdasilva.weixin.common.dictionary.WebAuthorizeScope;
import io.github.rcarlosdasilva.weixin.core.cache.impl.AccountCache;
import io.github.rcarlosdasilva.weixin.core.cache.impl.MixCache;
import io.github.rcarlosdasilva.weixin.model.Account;
import io.github.rcarlosdasilva.weixin.model.JsapiSignature;

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
  public String webAuthorize(WebAuthorizeScope scope, String redirectTo, String param) {
    Account account = AccountCache.instance().get(this.accountKey);

    return new StringBuilder(ApiAddress.URL_WEB_AUTHORIZE).append("?appid=")
        .append(account.getAppId()).append("&redirect_uri=").append(Utils.urlEncode(redirectTo))
        .append("&response_type=code&scope=").append(scope)
        .append(Strings.isNullOrEmpty(param) ? "" : ("&state=" + param)).append("#wechat_redirect")
        .toString();
  }

  @Override
  public String webAuthorize(WebAuthorizeScope scope, String redirectTo) {
    return webAuthorize(scope, redirectTo, null);
  }

  @Override
  public JsapiSignature generateJsapiSignature(String url) {
    Account account = AccountCache.instance().get(this.accountKey);
    String ticket = Weixin.with(this.accountKey).certificate().askJsTicket();
    String timestamp = Long.toString(System.currentTimeMillis() / 1000);
    String nonce = UUID.randomUUID().toString();

    String raw = new StringBuilder("jsapi_ticket=").append(ticket).append("&noncestr=")
        .append(nonce).append("&timestamp=").append(timestamp).append("&url=").append(url)
        .toString();
    String signature = null;

    signature = DigestUtils.sha1Hex(raw);

    return new JsapiSignature(account.getAppId(), ticket, signature, url, timestamp, nonce);
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean isLegalRequestIp(String ip) {
    Object obj = MixCache.instance().get(Convention.WEIXIN_IP_CACHE_KEY);
    final List<String> ips;
    if (obj != null && (obj instanceof List)) {
      ips = (List<String>) obj;
    } else {
      ips = Weixin.with(this.accountKey).common().getWeixinIps();
      MixCache.instance().put(Convention.WEIXIN_IP_CACHE_KEY, ips);
    }

    return ips.contains(ip.trim());
  }

}
