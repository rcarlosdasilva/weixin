package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.MediaApi;
import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.common.dictionary.MediaType;
import io.github.rcarlosdasilva.weixin.core.http.ContentType;
import io.github.rcarlosdasilva.weixin.core.http.FormData;
import io.github.rcarlosdasilva.weixin.core.http.Http;
import io.github.rcarlosdasilva.weixin.core.http.HttpMethod;
import io.github.rcarlosdasilva.weixin.core.parser.ResponseParser;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaAddMassImageRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaAddMassNewsRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaAddTemporaryRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaAddTimelessNewsRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaAddTimelessRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaCountTimelessRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaDeleteTimelessRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaGetTemporaryRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaGetTemporaryWithHqAudioRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaGetTimelessRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaListTimelessRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaTransformMassVideoRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.MediaUpdateTimelessNewsRequest;
import io.github.rcarlosdasilva.weixin.model.request.media.bean.Article;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaAddMassResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaAddTemporaryResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaAddTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaCountTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaGetTemporaryWithVideoResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaGetTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaListTimelessResponse;
import io.github.rcarlosdasilva.weixin.model.response.media.MediaTransformMassVideoResponse;

/**
 * 素材管理相关API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MediaApiImpl extends BasicApi implements MediaApi {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public MediaApiImpl(String accountKey) {
    super(accountKey);
  }

  @Override
  public MediaAddTemporaryResponse addTemporaryMedia(MediaType type, String fileName, File file) {
    Preconditions.checkArgument(MediaType.NEWS != type,
        "Not supported media type of News when add temporary");
    MediaAddTemporaryRequest requestModel = new MediaAddTemporaryRequest();
    requestModel.setType(type.getText());

    return upload(MediaAddTemporaryResponse.class, requestModel, Convention.MEDIA_FILE_UPLOAD_KEY,
        fileName, file, null);
  }

  @Override
  public byte[] getTemporaryMedia(String mediaId) {
    MediaGetTemporaryRequest requestModel = new MediaGetTemporaryRequest();
    requestModel.setMediaId(mediaId);

    InputStream is = getStream(requestModel);

    byte[] result = readStream(is);
    String text = new String(result);

    try {
      is.close();

      MediaGetTemporaryWithVideoResponse vedioResponse = ResponseParser
          .parse(MediaGetTemporaryWithVideoResponse.class, text);

      is = Http.requestStreamWithBodyContent(vedioResponse.getVideoUrl(), HttpMethod.GET, null,
          ContentType.JSON);
      result = readStream(is);
      is.close();
    } catch (Exception ex) {
      logger.error("media api get temporary media", ex);
    }

    return result;
  }

  @Override
  public byte[] getTemporaryMediaWithHqAudio(String mediaId) {
    MediaGetTemporaryWithHqAudioRequest requestModel = new MediaGetTemporaryWithHqAudioRequest();
    requestModel.setMediaId(mediaId);

    return readStream(getStream(requestModel));
  }

  @Override
  public MediaAddTimelessResponse addTimelessMedia(MediaType type, String fileName, File file) {
    Preconditions.checkArgument(MediaType.VIDEO != type,
        "Please invoke addTimelessMediaVideo for upload vedio");
    MediaAddTimelessRequest requestModel = new MediaAddTimelessRequest();
    requestModel.setType(type.getText());

    return upload(MediaAddTimelessResponse.class, requestModel, Convention.MEDIA_FILE_UPLOAD_KEY,
        fileName, file, null);
  }

  @Override
  public MediaAddTimelessResponse addTimelessMediaVideo(String fileName, File file, String title,
      String description) {
    MediaAddTimelessRequest requestModel = new MediaAddTimelessRequest();
    requestModel.setType(MediaType.VIDEO.getText());

    JsonObject obj = new JsonObject();
    obj.addProperty(Convention.MEDIA_VIDEO_FORM_TITLE, title);
    obj.addProperty(Convention.MEDIA_VIDEO_FORM_INTRODUCTION, description);

    FormData data = new FormData(Convention.MEDIA_VIDEO_FORM_KEY, obj.toString());

    return upload(MediaAddTimelessResponse.class, requestModel, Convention.MEDIA_FILE_UPLOAD_KEY,
        fileName, file, Lists.newArrayList(data));
  }

  @Override
  public String addTimelessMediaNews(List<Article> articles) {
    MediaAddTimelessNewsRequest requestModel = new MediaAddTimelessNewsRequest();
    requestModel.setArticles(articles);

    MediaAddTimelessResponse responseModel = post(MediaAddTimelessResponse.class, requestModel);
    return null == responseModel ? null : responseModel.getMediaId();
  }

  @Override
  public MediaGetTimelessResponse getTimelessMedia(String mediaId) {
    MediaGetTimelessRequest requestModel = new MediaGetTimelessRequest();
    requestModel.setMediaId(mediaId);

    MediaGetTimelessResponse responseModel = null;
    try {
      responseModel = post(MediaGetTimelessResponse.class, requestModel);
    } catch (JsonSyntaxException ex) {
      logger.info("Json字符串解析错误，尝试获取二进制流（文件），可能是在获取永久图片素材");
      responseModel = new MediaGetTimelessResponse();
      responseModel.setStream(readStream(postStream(requestModel)));
    }

    return responseModel;
  }

  @Override
  public boolean deleteTimelessMedia(String mediaId) {
    MediaDeleteTimelessRequest requestModel = new MediaDeleteTimelessRequest();
    requestModel.setMediaId(mediaId);

    return post(Boolean.class, requestModel);
  }

  @Override
  public boolean updateTimelessMedia(String mediaId, int index, Article article) {
    MediaUpdateTimelessNewsRequest requestModel = new MediaUpdateTimelessNewsRequest();
    requestModel.setMediaId(mediaId);
    requestModel.setIndex(index);
    requestModel.setArticle(article);

    return post(Boolean.class, requestModel);
  }

  @Override
  public MediaCountTimelessResponse countTimelessMedia() {
    MediaCountTimelessRequest requestModel = new MediaCountTimelessRequest();

    return get(MediaCountTimelessResponse.class, requestModel);
  }

  @Override
  public MediaListTimelessResponse listTimelessMedia(MediaType type, int offset, int count) {
    Preconditions.checkArgument(MediaType.THUMBNAIL != type,
        "Not supported media type of Thumb when list timeless");
    if (count > 20) {
      count = 20;
    }
    if (count < 1) {
      count = 1;
    }

    MediaListTimelessRequest requestModel = new MediaListTimelessRequest();
    requestModel.setType(type.getText());
    requestModel.setOffset(offset);
    requestModel.setCount(count);

    return post(MediaListTimelessResponse.class, requestModel);
  }

  @Override
  public String addMassMediaImage(String fileName, File file) {
    MediaAddMassImageRequest requestModel = new MediaAddMassImageRequest();

    MediaAddMassResponse responseModel = upload(MediaAddMassResponse.class, requestModel,
        Convention.MEDIA_FILE_UPLOAD_KEY, fileName, file, null);
    return null == responseModel ? null : responseModel.getUrl();
  }

  @Override
  public MediaAddMassResponse addMassMediaNews(List<Article> articles) {
    MediaAddMassNewsRequest requestModel = new MediaAddMassNewsRequest();
    requestModel.setArticles(articles);

    return post(MediaAddMassResponse.class, requestModel);
  }

  @Override
  public MediaTransformMassVideoResponse transformMassMediaVideo(String mediaId, String title,
      String description) {
    MediaTransformMassVideoRequest requestModel = new MediaTransformMassVideoRequest();
    requestModel.setMediaId(mediaId);
    requestModel.setTitle(title);
    requestModel.setDescription(description);

    return post(MediaTransformMassVideoResponse.class, requestModel);
  }

}
