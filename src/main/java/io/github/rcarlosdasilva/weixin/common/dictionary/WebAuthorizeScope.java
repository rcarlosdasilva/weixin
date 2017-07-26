package io.github.rcarlosdasilva.weixin.common.dictionary;

/**
 * 网页授权Scope
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public enum WebAuthorizeScope {

  /**
   * snsapi_base.
   * 
   * <P>
   * 以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的， 并且是静默授权并自动跳转到回调页的。
   * 用户感知的就是直接进入了回调页（往往是业务页面）
   */
  BASE("snsapi_base"),
  /**
   * snsapi_userinfo.
   * 
   * <P>
   * 以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意， 并且由于用户同意过，
   * 所以无须关注，就可在授权后获取该用户的基本信息。
   */
  USERINFO("snsapi_userinfo");

  private String text;

  private WebAuthorizeScope(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return this.text;
  }

}
