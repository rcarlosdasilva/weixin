package io.github.rcarlosdasilva.weixin.model.request.base;

/**
 * 基本开放平台请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class BasicOpenPlatformRequest extends BasicRequest {

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?component_access_token=").append(this.accessToken)
        .toString();
  }

}
