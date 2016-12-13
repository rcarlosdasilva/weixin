package io.github.rcarlosdasilva.weixin.model.request.base;

/**
 * 请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
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
   */
  public void updateAccessToken(String accessToken);

}
