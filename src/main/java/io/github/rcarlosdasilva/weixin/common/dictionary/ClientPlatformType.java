package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 客户端平台类型
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum ClientPlatformType {

  IOS(1), ANDROID(2), OTHER(3);

  private int code;

  private ClientPlatformType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
