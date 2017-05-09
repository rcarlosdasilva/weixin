package io.github.rcarlosdasilva.weixin.api.internal;

import java.io.File;
import java.util.List;

import io.github.rcarlosdasilva.weixin.common.dictionary.MediaType;
import io.github.rcarlosdasilva.weixin.model.request.media.bean.Article;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaAddMassResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaAddTemporaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaAddTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaCountTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaGetTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaListTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaTransformMassVideoResponse;

/**
 * 素材管理相关API
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface MediaApi {

  /**
   * 新增临时素材.
   * 
   * <p>
   * 公众号经常有需要用到一些临时性的多媒体素材的场景，例如在使用接口特别是发送消息时，对多媒体文件、多媒体消息的获取和调用等操作，
   * 是通过media_id来进行的。素材管理接口对所有认证的订阅号和服务号开放。通过本接口，公众号可以新增临时素材（即上传临时多媒体文件）。
   * <p>
   * 注意点：<br>
   * 1、临时素材media_id是可复用的。<br>
   * 2、媒体文件在微信后台保存时间为3天，即3天后media_id失效。 <br>
   * 3、上传临时素材的格式、大小限制与公众平台官网一致。 <br>
   * 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式 <br>
   * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式 <br>
   * 视频（video）：10MB，支持MP4格式 <br>
   * 缩略图（thumb）：64KB，支持JPG格式 <br>
   * 4、需使用https调用本接口。
   * 
   * @param type
   *          素材类型，不包含图文
   * @param fileName
   *          文件名
   * @param file
   *          素材文件
   * @return see {@link MediaAddTemporaryResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726&token=&lang=zh_CN"
   *      >新增临时素材</a>
   */
  MediaAddTemporaryResponse addTemporaryMedia(MediaType type, String fileName, File file);

  /**
   * 获取临时素材.
   * 
   * <p>
   * 公众号可以使用本接口获取临时素材（即下载临时的多媒体文件）。请注意，视频文件不支持https下载，调用该接口需http协议。
   * 本接口即为原“下载多媒体文件”接口。
   * 
   * @param mediaId
   *          媒体文件ID
   * @return 二进制流
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738727&token=&lang=zh_CN"
   *      >获取临时素材</a>
   */
  byte[] getTemporaryMedia(String mediaId);

  /**
   * 获取临时素材（高清语音素材获取接口）.
   * 
   * <p>
   * 公众号可以使用本接口获取从JSSDK的uploadVoice接口上传的临时语音素材，格式为speex，16K采样率。
   * 该音频比上文的临时素材获取接口（格式为amr，8K采样率）更加清晰，适合用作语音识别等对音质要求较高的业务。
   * 
   * @param mediaId
   *          媒体文件ID
   * @return 二进制流
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738727&token=&lang=zh_CN"
   *      >获取临时素材</a>
   */
  byte[] getTemporaryMediaWithHqAudio(String mediaId);

  /**
   * 新增其他类型永久素材.
   * <p>
   * 图片素材将进入公众平台官网素材管理模块中的默认分组。
   * <p>
   * 如需新增视频素材，请使用 {@link #addTimelessMediaVideo(String, File, String, String)}
   * 
   * @param type
   *          素材类型
   * @param fileName
   *          文件名
   * @param file
   *          素材文件
   * @return see {@link MediaAddTimelessResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738729&token=&lang=zh_CN"
   *      >新增其他类型永久素材</a>
   */
  MediaAddTimelessResponse addTimelessMedia(MediaType type, String fileName, File file);

  /**
   * 新增其他类型永久素材.
   * <p>
   * 新增永久视频素材需特别注意 在上传视频素材时需要POST另一个表单，id为description，包含素材的描述信息，内容格式为JSON
   * <p>
   * 
   * @param fileName
   *          文件名
   * @param file
   *          素材文件
   * @param title
   *          视频素材的标题
   * @param description
   *          视频素材的描述
   * @return see {@link MediaAddTimelessResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738729&token=&lang=zh_CN"
   *      >新增其他类型永久素材</a>
   */
  MediaAddTimelessResponse addTimelessMediaVideo(String fileName, File file, String title,
      String description);

  /**
   * 新增永久图文素材.
   * <p>
   * 请注意，在图文消息的具体内容中，将过滤外部的图片链接，开发者可以通过下述接口上传图片得到URL，放到图文内容中使用。
   * 
   * @param articles
   *          图文列表
   * @return 新增的图文消息素材的media_id
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738729&token=&lang=zh_CN"
   *      >新增永久图文素材</a>
   */
  String addTimelessMediaNews(List<Article> articles);

  /**
   * 获取永久素材.
   * <p>
   * 获取永久素材也可以获取公众号在公众平台官网素材管理模块中新建的图文消息、图片、语音、视频等素材
   * （但需要先通过获取素材列表来获知素材的media_id），临时素材无法通过本接口获取
   * 
   * @param mediaId
   *          要获取的素材的media_id
   * @return see {@link MediaGetTimelessResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738730&token=&lang=zh_CN"
   *      >获取永久素材</a>
   */
  MediaGetTimelessResponse getTimelessMedia(String mediaId);

  /**
   * 删除永久素材.
   * <p>
   * 请谨慎操作本接口，因为它可以删除公众号在公众平台官网素材管理模块中新建的图文消息、语音、视频等素材
   * （但需要先通过获取素材列表来获知素材的media_id），临时素材无法通过本接口删除
   * 
   * @param mediaId
   *          要删除的素材的media_id
   * @return 是否成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738731&token=&lang=zh_CN"
   *      >删除永久素材</a>
   */
  boolean deleteTimelessMedia(String mediaId);

  /**
   * 修改永久图文素材.
   * <p>
   * 也可以在公众平台官网素材管理模块中保存的图文消息（永久图文素材）
   * 
   * @param mediaId
   *          要修改的图文消息的media_id
   * @param index
   *          要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
   * @param article
   *          图文内容
   * @return 是否成功
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738732&token=&lang=zh_CN"
   *      >修改永久图文素材</a>
   */
  boolean updateTimelessMedia(String mediaId, int index, Article article);

  /**
   * 获取素材总数.
   * <p>
   * 永久素材的总数，也会计算公众平台官网素材管理中的素材，图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
   * 
   * @return see {@link MediaCountTimelessResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738733&token=&lang=zh_CN"
   *      >获取素材总数</a>
   */
  MediaCountTimelessResponse countTimelessMedia();

  /**
   * 获取素材列表.
   * <p>
   * 获取永久素材的列表，也包含公众号在公众平台官网素材管理模块中新建的图文消息、语音、视频等素材，临时素材无法通过本接口获取
   * 
   * @param type
   *          素材类型
   * @param offset
   *          从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
   * @param count
   *          返回素材的数量，取值在1到20之间
   * @return see {@link MediaListTimelessResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738734&token=&lang=zh_CN"
   *      >获取素材总数</a>
   */
  MediaListTimelessResponse listTimelessMedia(MediaType type, int offset, int count);

  /**
   * 上传图文消息内的图片获取URL.
   * 
   * <p>
   * 请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。
   * 
   * @param fileName
   *          文件名
   * @param file
   *          图片文件
   * @return 上传图片的URL，可用于后续群发中，放置到图文消息中
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >上传图文消息内的图片获取URL</a>
   */
  String addMassMediaImage(String fileName, File file);

  /**
   * 上传图文消息素材（群发）.
   * 
   * @param articles
   *          图文内容列表
   * @return see {@link MediaAddMassResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >上传图文消息素材</a>
   */
  MediaAddMassResponse addMassMediaNews(List<Article> articles);

  /**
   * 转换视频素材media_id.
   * 
   * @param mediaId
   *          通过基础支持中的上传下载多媒体文件得到media_id
   * @param title
   *          标题
   * @param description
   *          描述
   * @return see {@link MediaTransformMassVideoResponse}
   * @see <a href=
   *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN"
   *      >群发中对视频的特殊处理</a>
   */
  MediaTransformMassVideoResponse transformMassMediaVideo(String mediaId, String title,
      String description);

}
