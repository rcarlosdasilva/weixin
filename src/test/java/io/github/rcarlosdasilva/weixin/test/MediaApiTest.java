package io.github.rcarlosdasilva.weixin.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.test.basic.RegisterAndUse;

public class MediaApiTest {

  @BeforeClass
  public static void setUpBeforeClass() {
    RegisterAndUse.reg();
  }

  @Test
  public void testTemporyary() throws Exception {
    // File file = new File("D:\\123.jpg");
    // System.out.println(file.exists());
    // Assert.assertTrue(file.exists());
    //
    // MediaAddTemporaryResponse response = Weixin.withUnique().media()
    // .addTemporaryMedia(MediaType.IMAGE, "abcxyz.jpg", file);
    // Assert.assertNotNull(response.getMediaId());
    // Assert.assertNotNull(response.getType());
    // Assert.assertNotNull(response.getCreatedAt());
    // System.out.println(response.getMediaId());
    //
    // Thread.sleep(2000);
    //
    // byte[] buffer =
    // Weixin.withUnique().media().getTemporaryMedia(response.getMediaId());
    // InputStream is = Weixin.withUnique().media()
    // .getTemporaryMedia("Co1RG2pGu38BVc58WhfJqKVSZ3ujL3-M6avj2Xy_KIMxJR8013TVnso2pJWMDHNV");
    // // 图片
    byte[] buffer = Weixin.withUnique().media()
        .getTemporaryMedia("Ej3zDhXh5LWPumqOeOAGmZcslH6JYn-4tilk4w4QrxVjRwB7QnRtCdN9qWR6u639");// 视频
    Assert.assertNotNull(buffer);

    OutputStream os = new FileOutputStream(new File("D:\\xyz003.mp4"));
    // int bytesRead = 0;
    // byte[] buffer = new byte[8192];
    // while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
    // System.out.println("reading");
    os.write(buffer);
    // }
    os.close();
    // is.close();
    System.out.println("done!");
  }

}
