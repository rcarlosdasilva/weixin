package io.github.rcarlosdasilva.weixin.model.request.base;

import io.github.rcarlosdasilva.weixin.core.json.Freeze;
import io.github.rcarlosdasilva.weixin.core.json.Json;

public abstract class BasicRequest implements Request {

  @Freeze
  protected String path;
  @Freeze
  protected String accessToken;

  @Override
  public String toUrl() {
    return this.toString();
  }

  @Override
  public void updateAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toJson() {
    return Json.toJson(this, this.getClass());
  }

}
