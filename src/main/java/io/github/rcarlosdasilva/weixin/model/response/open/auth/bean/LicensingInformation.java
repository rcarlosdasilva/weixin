package io.github.rcarlosdasilva.weixin.model.response.open.auth.bean;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.common.dictionary.OpenPlatformLisensableFunction;

/**
 * 授权信息
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class LicensingInformation implements Serializable {

  private static final long serialVersionUID = -1687096878280046105L;

  private String appId;
  private List<Integer> functionIds = Lists.newArrayList();
  private List<OpenPlatformLisensableFunction> functions = null;

  /**
   * 授权方appid.
   * <p>
   * 即公众号或小程序的appid
   * 
   * @return appid
   */
  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  /**
   * 添加授权方授权的功能id.
   * 
   * @param functionId
   *          id
   */
  public void addLicencedFunction(Integer functionId) {
    this.functionIds.add(functionId);
  }

  /**
   * 添加授权方授权的功能.
   * 
   * @return 授权功能列表
   */
  public synchronized List<OpenPlatformLisensableFunction> getLicencedFunctions() {
    if (functions == null) {
      functions = Lists.newArrayList();
      for (Integer id : functionIds) {
        functions.add(OpenPlatformLisensableFunction.byCode(id));
      }
    }
    return functions;
  }

}
