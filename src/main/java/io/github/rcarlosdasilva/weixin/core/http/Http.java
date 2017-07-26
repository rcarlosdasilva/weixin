package io.github.rcarlosdasilva.weixin.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.github.rcarlosdasilva.weixin.core.exception.HttpException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Http请求工具
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class Http {

  private static final Logger LOGGER = LoggerFactory.getLogger(Http.class);

  private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
      .connectTimeout(3, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
  private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
  private static final MediaType XML_TYPE = MediaType.parse("application/xml; charset=utf-8");
  private static final MediaType MULTI_FORM_TYPE = MultipartBody.FORM;

  private Http() {
  }

  private static Request generatePlainRequest(String url, HttpMethod method, String content,
      ContentType type) {
    MediaType mediaType = type == ContentType.JSON ? JSON_TYPE : XML_TYPE;
    content = Strings.nullToEmpty(content);

    okhttp3.Request.Builder builder = new Request.Builder().url(url);

    Request request;
    switch (method) {
      case GET:
        request = builder.build();
        break;
      case HEAD:
        request = builder.head().build();
        break;
      case POST:
        request = builder.post(RequestBody.create(mediaType, content)).build();
        break;
      case PUT:
        request = builder.put(RequestBody.create(mediaType, content)).build();
        break;
      case PATCH:
        request = builder.patch(RequestBody.create(mediaType, content)).build();
        break;
      case DELETE:
        request = builder.delete(RequestBody.create(mediaType, content)).build();
        break;
      default:
        request = null;
    }

    if (request == null) {
      throw new HttpException("Unsupported or unknown request method");
    }

    return request;
  }

  private static Request generateFormRequest(String url, HttpMethod method, List<FormData> form) {
    okhttp3.Request.Builder builder = new Request.Builder().url(url);
    okhttp3.FormBody.Builder formBuilder = new FormBody.Builder();

    if (form != null) {
      for (FormData data : form) {
        formBuilder.add(data.getKey(), data.getValue());
      }
    }

    Request request;
    switch (method) {
      case POST:
        request = builder.post(formBuilder.build()).build();
        break;
      case PUT:
        request = builder.put(formBuilder.build()).build();
        break;
      case PATCH:
        request = builder.patch(formBuilder.build()).build();
        break;
      case DELETE:
        request = builder.delete(formBuilder.build()).build();
        break;
      default:
        request = null;
    }

    if (request == null) {
      throw new HttpException("Unsupported or unknown request method");
    }

    return request;
  }

  /**
   * 发送请求.
   * 
   * @param url
   *          请求地址
   * @param method
   *          请求方法
   * @param content
   *          请求参数体
   * @param type
   *          指定请求内容格式，JSON或XML
   * @return response字符串
   */
  public static String requestWithBodyContent(String url, HttpMethod method, String content,
      ContentType type) {
    Request request = generatePlainRequest(url, method, content, type);
    Response response;
    try {
      response = CLIENT.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new HttpException(response.message());
      }
      return response.body().string();
    } catch (IOException ex) {
      LOGGER.error("weixin http", ex);
    }
    return "";
  }

  /**
   * 发送请求，并返回二进制流.
   * 
   * @param url
   *          请求地址
   * @param method
   *          请求方法
   * @param content
   *          请求参数体
   * @param type
   *          指定请求内容格式，JSON或XML
   * @return response二进制流
   */
  public static InputStream requestStreamWithBodyContent(String url, HttpMethod method,
      String content, ContentType type) {
    Request request = generatePlainRequest(url, method, content, type);
    Response response;
    try {
      response = CLIENT.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new HttpException(response.message());
      }
      return response.body().byteStream();
    } catch (IOException ex) {
      LOGGER.error("weixin http", ex);
    }
    return null;
  }

  /**
   * 发送请求，带Form表单数据.
   * 
   * <p>
   * 只支持POST，PUT，PATCH，DELETE方法
   * 
   * @param url
   *          地址
   * @param method
   *          请求方法
   * @param form
   *          表单数据.
   * @return response字符串
   */
  public static String requestWithForm(String url, HttpMethod method, List<FormData> form) {
    Request request = generateFormRequest(url, method, form);
    Response response;
    try {
      response = CLIENT.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new HttpException(response.message());
      }
      return response.body().string();
    } catch (IOException ex) {
      LOGGER.error("weixin http", ex);
    }
    return "";
  }

  /**
   * 发送请求，带Form表单数据，并返回二进制流.
   * 
   * <p>
   * 只支持POST，PUT，PATCH，DELETE方法
   * 
   * @param url
   *          地址
   * @param method
   *          请求方法
   * @param form
   *          表单数据.
   * @return response二进制流
   */
  public static InputStream requestStreamWithForm(String url, HttpMethod method,
      List<FormData> form) {
    Request request = generateFormRequest(url, method, form);
    Response response;
    try {
      response = CLIENT.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new HttpException(response.message());
      }
      return response.body().byteStream();
    } catch (IOException ex) {
      LOGGER.error("weixin http", ex);
    }
    return null;
  }

  /**
   * 以POST方法上传一个Multipart数据.
   * 
   * @param url
   *          请求地址
   * @param multiFile
   *          文件信息
   * @param additionalData
   *          附加表单数据
   * @return response字符串
   */
  public static String requestWithFile(String url, MultiFile multiFile,
      List<FormData> additionalData) {
    return requestWithFile(url, Lists.newArrayList(multiFile), additionalData);
  }

  /**
   * 以POST方法上传一个Multipart数据，内含多个文件.
   * 
   * @param url
   *          请求地址
   * @param multiFiles
   *          多个文件信息
   * @param additionalData
   *          附加表单数据
   * @return response字符串
   */
  public static String requestWithFile(String url, List<MultiFile> multiFiles,
      List<FormData> additionalData) {
    okhttp3.MultipartBody.Builder builder = new MultipartBody.Builder();
    builder.setType(MULTI_FORM_TYPE);
    if (additionalData != null) {
      for (FormData formData : additionalData) {
        builder.addFormDataPart(formData.getKey(), formData.getValue());
      }
    }

    for (MultiFile multiFile : multiFiles) {
      MediaType mediaType = MediaType.parse(multiFile.getContentType().getText());
      builder.addFormDataPart(multiFile.getFileKey(), multiFile.getFileName(),
          RequestBody.create(mediaType, multiFile.getFile()));
    }

    RequestBody body = builder.build();
    Request request = new Request.Builder().url(url).post(body).build();
    Response response;
    try {
      response = CLIENT.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new HttpException(response.message());
      }
      return response.body().string();
    } catch (IOException ex) {
      LOGGER.error("weixin http", ex);
    }
    return "";
  }

}
