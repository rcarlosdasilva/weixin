package io.github.rcarlosdasilva.weixin.test.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import io.github.rcarlosdasilva.weixin.api.Weixin;

/**
 * 其他常用
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class OtherCommons {

  /**
   * 二维码、短连接等等.
   */
  @SuppressWarnings("unused")
  public static void main(String[] args) throws Exception {
    RegisterAndUse.reg();
    // 微信ip
    List<String> ips = Weixin.withUnique().common().getWeixinIps();
    // 获取短连接
    String shortUrl = Weixin.withUnique().common().getShortUrl("http://www.google.com");
    // System.out.println(shortUrl);

    // 下面代码与二维码相关
    // 创建二维码
    // Weixin.withUnique().common().createQrWithTemporary(1, 1);
    // Weixin.withUnique().common().createQrWithUnlimited(1);
    // Weixin.withUnique().common().createQrWithUnlimited("1");
    // 获取二维码图片
    // InputStream is = Weixin.withUnique().common().qrImage(response);

    // 或者直接获取二维码图片
    byte[] data = Weixin.withUnique().common().qrImageWithTemporary(1, 1);
    File imageFile = new File("qr.jpg");
    FileOutputStream outStream = new FileOutputStream(imageFile);
    outStream.write(data);
    outStream.close();
  }

}
