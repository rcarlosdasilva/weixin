package io.github.rcarlosdasilva.weixin.model.request.base;

import io.github.rcarlosdasilva.weixin.core.json.Freeze;
import io.github.rcarlosdasilva.weixin.core.json.Json;

/**
 * 基本请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class BasicRequest implements Request {

  @Freeze
  protected String accessToken;
  @Freeze
  protected String path;

  @Override
  public String toUrl() {
    return this.toString();
  }

  @Override
  public String toJson() {
    return Json.toJson(this, this.getClass());
  }

  @Override
  public void updateAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   * 重写生成URL策略，可根据不同策略重写.
   */
  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .toString();
  }

}
