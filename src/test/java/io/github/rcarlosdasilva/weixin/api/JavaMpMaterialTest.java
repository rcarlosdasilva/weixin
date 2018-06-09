package io.github.rcarlosdasilva.weixin.api;

import com.google.common.collect.Lists;
import io.github.rcarlosdasilva.weixin.core.Weixin;
import io.github.rcarlosdasilva.weixin.mix.TestHelper;
import io.github.rcarlosdasilva.weixin.model.request.Article;
import io.github.rcarlosdasilva.weixin.model.response.*;
import io.github.rcarlosdasilva.weixin.terms.data.MaterialType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JavaMpMaterialTest {

  private static String key;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @BeforeClass
  public static void before() {
    key = TestHelper.INSTANCE.initSingle();
  }

  /**
   * 临时素材
   */
  @Test
  public void test01() throws URISyntaxException {
    Random r = new Random();
    String filename = "file_" + r.nextInt() + ".jpg";
    URL url = ClassLoader.getSystemResource("img_1.jpg");
    File file = new File(url.toURI());

    MaterialAddTemporaryResponse response = Weixin.mp(key).getMaterial()
        .addTemporaryMaterial(MaterialType.IMAGE, filename, file);
    Assert.assertNotNull(response);
    Assert.assertNotNull(response.getId());

    byte[] stream = Weixin.mp(key).getMaterial().getTemporaryMaterial(response.getId());
    Assert.assertNotNull(stream);
  }

  /**
   * 永久素材添加、获取、删除
   */
  @Test
  public void test02() throws URISyntaxException {
    Random r = new Random();
    String imageFilename = "file_" + r.nextInt() + ".jpg";
    URL url = ClassLoader.getSystemResource("img_2.jpg");
    File imageFile = new File(url.toURI());

    MaterialAddTimelessResponse imageResponse = Weixin.mp(key).getMaterial()
        .addTimelessMaterial(MaterialType.IMAGE, imageFilename, imageFile);
    Assert.assertNotNull(imageResponse);
    Assert.assertNotNull(imageResponse.getId());

    String videoFilename = "file_" + r.nextInt() + ".mp4";
    url = ClassLoader.getSystemResource("video_1.mp4");
    File videoFile = new File(url.toURI());

    MaterialAddTimelessResponse videoResponse = Weixin.mp(key).getMaterial()
        .addTimelessMaterialVideo(videoFilename, videoFile, "测试视频" + videoFilename, "描述.");
    Assert.assertNotNull(videoResponse);
    Assert.assertNotNull(videoResponse.getId());

    MaterialGetTimelessResponse image = Weixin.mp(key).getMaterial().getTimelessMaterial(imageResponse.getId());
    Assert.assertNotNull(image);
    Assert.assertNotNull(image.getStream());

    MaterialGetTimelessResponse video = Weixin.mp(key).getMaterial().getTimelessMaterial(videoResponse.getId());
    Assert.assertNotNull(video);
    Assert.assertNotNull(video.getTitle());
    Assert.assertNotNull(video.getDownloadUrl());

    boolean deleted = Weixin.mp(key).getMaterial().deleteTimelessMaterial(imageResponse.getId());
    deleted &= Weixin.mp(key).getMaterial().deleteTimelessMaterial(videoResponse.getId());
    Assert.assertTrue(deleted);
  }

  /**
   * 总数
   */
  @Test
  public void test03() {
    MaterialCountTimelessResponse response = Weixin.mp(key).getMaterial().countTimelessMaterial();
    Assert.assertNotNull(response);
  }

  /**
   * 图文消息，新增，修改
   * TODO 测试未通过，微信接口报不支持文件类型错误
   */
  @Test
  public void test04() throws URISyntaxException {
    Random r = new Random();
    int num = r.nextInt();

    URL url1 = ClassLoader.getSystemResource("img_1.jpg");
    URL url2 = ClassLoader.getSystemResource("img_2.jpg");
    File imageFile1 = new File(url1.toURI());
    File imageFile2 = new File(url2.toURI());

    // 封面
    MaterialAddTimelessResponse imageResponse = Weixin.mp(key).getMaterial()
        .addTimelessMaterial(MaterialType.THUMBNAIL, String.valueOf(num), imageFile2);
    // 内容图片
    String imageUrl = Weixin.mp(key).getMaterial().addNewsImage(String.valueOf(num), imageFile1);

    // 新增
    Article articleNew = new Article(
        "单图文" + num,
        imageResponse.getId(),
        "作者",
        "单图文摘要",
        true,
        "正文内容" + num + "<img src='" + imageUrl + "' />",
        "http://www.baidu.com");
    List<Article> articles = Lists.newArrayList(articleNew);

    String materialId = Weixin.mp(key).getMaterial().addTimelessMaterialNews(articles);
    Assert.assertNotNull(materialId);

    // 修改
    Article articleUpdate = new Article(
        "单图文修改" + num,
        imageResponse.getId(),
        "作者",
        "单图文摘要",
        true,
        "正文内容修改" + num + "<img src='" + imageUrl + "' />",
        "http://www.baidu.com");
    boolean updated = Weixin.mp(key).getMaterial().updateTimelessMaterialNews(materialId, 0, articleUpdate);
    Assert.assertTrue(updated);

    MaterialListTimelessResponse response = Weixin.mp(key).getMaterial().listTimelessMaterial(MaterialType.NEWS, 0, 10);
    Assert.assertNotNull(response);
    Assert.assertTrue(response.getItemCount() > 1);
  }

}
