package io.github.rcarlosdasilva.weixin.model.request.base;

/**
 * 请求模型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public interface Request {

  /**
   * 转换模型数据成URL.
   * 
   * @return 链接
   */
  public String toUrl();

  /**
   * 转换模型数据为JSON格式字符串.
   * 
   * @return JSON数据
   */
  public String toJson();

  /**
   * 更新access_token.
   * <P>
   * 对公众号是access_token，对开放平台是component_access_token
   * 
   * @param accessToken
   *          access_token
   */
  public void updateAccessToken(String accessToken);

}
