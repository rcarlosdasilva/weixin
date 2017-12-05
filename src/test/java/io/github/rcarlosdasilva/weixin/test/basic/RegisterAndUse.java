package io.github.rcarlosdasilva.weixin.test.basic;

import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.core.Registry;
import io.github.rcarlosdasilva.weixin.model.WeixinAccount;
import io.github.rcarlosdasilva.weixin.test.Property;

/**
 * 如何使用Aha-Weixin第一步，注册公众号信息，并演示如何使用
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class RegisterAndUse {

  public static void main(String[] args) {
    reg();
    use();
  }

  /**
   * 该方法可以作为示例中的通用注册方法.
   */
  public static void reg() {
    /*
     * 注册一个公众号，并定义对应的 KEY，之后的任何操作都会以 KEY 来指定使用哪一个公众号响应。将src/main/resources下的
     * sample.properties 文件中的appId与appSecret替换成有效的值，可正常运行，
     * appId与appSecret就能满足所有的对微信API的主动调用
     */
    Registry.registerUnique(WeixinAccount.create(Property.get("appid"), Property.get("appsecret")));
    /*
     * 注册并完整其他信息。使用setBasic指定公众号为已认证的服务号。使用setServerSecurity指定服务器配置，用于消息加解密
     */
    // WeixinRegistry.registry(KEY, "appId",
    // "appSecret").setBasic(AccountType.SERVICE, true)
    // .setServerSecurity("token", "aesKey", EncryptionType.PLAIN_TEXT);
  }

  private static void use() {
    /*
     * 用 Weixin.with(KEY) 获取到 KEY 对应的公众号接口操作入口。helper为其中一个入口，
     * 具体查看每个入口的注释。下面代码演示判断一个IP是否是真正的微信服务器IP
     */
    boolean canBeTrust = Weixin.withUnique().helper().isLegalRequestIp("101.226.62.78");
    System.out.println("IP can be trust: " + canBeTrust);
  }

}
