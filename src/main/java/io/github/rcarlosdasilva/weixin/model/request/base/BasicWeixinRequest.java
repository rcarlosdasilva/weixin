package io.github.rcarlosdasilva.weixin.model.request.base;

/**
 * 基本公众号请求模型
 * 
 * @author Dean Zhao (rcarlosdailva@qq.com)
 */
public class BasicWeixinRequest extends BasicRequest {

  /**
   * 重写生成URL策略，可根据不同策略重写.
   */
  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?access_token=").append(this.accessToken)
        .toString();
  }

}
