package io.github.rcarlosdasilva.weixin.model.request.common;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 语义理解请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
// TODO 没实现
@SuppressWarnings("unused")
public class SemanticAnalyzeRequest extends BasicRequest {

  private String appid;
  private String uid;
  private String query;
  private String category;
  private double latitude;
  private double longitude;
  private String city;
  private String region;

  public SemanticAnalyzeRequest(String accessToken) {
    this.accessToken = accessToken;
    this.path = ApiAddress.URL_COMMON_SEMANTIC_ANALYZE;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setRegion(String region) {
    this.region = region;
  }

}
