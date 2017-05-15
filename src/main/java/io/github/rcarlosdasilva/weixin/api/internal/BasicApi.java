package io.github.rcarlosdasilva.weixin.api.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;

import io.github.rcarlosdasilva.weixin.api.Weixin;
import io.github.rcarlosdasilva.weixin.core.exception.MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException;
import io.github.rcarlosdasilva.weixin.core.http.ContentType;
import io.github.rcarlosdasilva.weixin.core.http.FormData;
import io.github.rcarlosdasilva.weixin.core.http.Http;
import io.github.rcarlosdasilva.weixin.core.http.HttpMethod;
import io.github.rcarlosdasilva.weixin.core.http.MultiFile;
import io.github.rcarlosdasilva.weixin.core.parser.ResponseParser;
import io.github.rcarlosdasilva.weixin.model.request.base.Request;
import io.github.rcarlosdasilva.weixin.model.request.certificate.AccessTokenRequest;

/**
 * API访问基础类
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class BasicApi {

  protected String accountKey;

  private int getRetryTimes() {
    return Weixin.with(this.accountKey).info().getRetryTimes();
  }

  private String getAccessToken() {
    return Weixin.with(this.accountKey).certificate().askAccessToken();
  }

  private void updateAccessToken(Request requestModel) {
    if (!(requestModel instanceof AccessTokenRequest)) {
      requestModel.updateAccessToken(getAccessToken());
    }
  }

  /**
   * 刷新access_token.
   */
  private void refreshAccessToken() {
    Weixin.with(this.accountKey).certificate().refreshAccessToken();
  }

  /**
   * 发送post请求.
   * 
   * @param <T>
   *          The Type of element
   * @param target
   *          响应的封装类型
   * @param requestModel
   *          请求模型
   * @return 响应封装对象
   */
  protected <T> T post(Class<T> target, Request requestModel) {
    updateAccessToken(requestModel);

    return new BasicApi.RetryableRunner<T>() {

      @SuppressWarnings("unchecked")
      @Override
      <R> R pending() {
        String responseText = Http.requestWithBodyContent(requestModel.toUrl(), HttpMethod.POST,
            requestModel.toJson(), ContentType.JSON);
        R response = (R) ResponseParser.parse(target, responseText);
        return response;
      }

    }.run();

  }

  /**
   * 使用get方法获取二进制流.
   * 
   * @param requestModel
   *          请求模型
   * @return 二进制流
   */
  protected InputStream postStream(Request requestModel) {
    updateAccessToken(requestModel);

    return new BasicApi.RetryableRunner<InputStream>() {

      @SuppressWarnings("unchecked")
      @Override
      <R> R pending() {
        return (R) Http.requestStreamWithBodyContent(requestModel.toUrl(), HttpMethod.POST,
            requestModel.toJson(), ContentType.JSON);
      }

    }.run();

  }

  /**
   * 发送get请求.
   * 
   * @param <T>
   *          The Type of element
   * @param target
   *          响应的封装类型
   * @param requestModel
   *          请求模型
   * @return 响应封装对象
   */
  protected <T> T get(Class<T> target, Request requestModel) {
    updateAccessToken(requestModel);

    return new BasicApi.RetryableRunner<T>() {

      @SuppressWarnings("unchecked")
      @Override
      <R> R pending() {
        String responseText = Http.requestWithBodyContent(requestModel.toUrl(), HttpMethod.GET,
            null, ContentType.JSON);
        return (R) ResponseParser.parse(target, responseText);
      }

    }.run();

  }

  /**
   * 使用get方法获取二进制流.
   * 
   * @param requestModel
   *          请求模型
   * @return 二进制流
   */
  protected InputStream getStream(Request requestModel) {
    updateAccessToken(requestModel);

    return new BasicApi.RetryableRunner<InputStream>() {

      @SuppressWarnings("unchecked")
      @Override
      <R> R pending() {
        return (R) Http.requestStreamWithBodyContent(requestModel.toUrl(), HttpMethod.GET, null,
            ContentType.JSON);
      }

    }.run();

  }

  /**
   * 上传一个文件（post）.
   * 
   * @param <T>
   *          The Type of element
   * @param target
   *          响应的封装类型
   * @param requestModel
   *          请求模型
   * @param key
   *          文件标识
   * @param fileName
   *          文件名
   * @param file
   *          文件路径
   * @param additionalData
   *          附加数据，可当做参数传递出去
   * @return 响应封装对象
   */
  protected <T> T upload(Class<T> target, Request requestModel, String key, String fileName,
      File file, List<FormData> additionalData) {
    updateAccessToken(requestModel);

    return new BasicApi.RetryableRunner<T>() {

      @SuppressWarnings("unchecked")
      @Override
      <R> R pending() {
        String responseText = Http.requestWithFile(requestModel.toUrl(),
            new MultiFile(key, fileName, file), additionalData);
        return (R) ResponseParser.parse(target, responseText);
      }

    }.run();

  }

  protected byte[] readStream(InputStream is) {
    try {
      return ByteStreams.toByteArray(is);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 接口请求执行器
   * 
   * @author Dean Zhao (rcarlosdasilva@qq.com)
   * @param <R>
   *          返回类型
   */
  abstract class RetryableRunner<R> {

    private final Logger logger = LoggerFactory.getLogger(RetryableRunner.class);

    /**
     * 执行.
     * <p>
     * 开始执行方法 pending 中定义的内容，并在access_token无效时，进行刷新access_token同时重新尝试执行
     * N(N=times) 次 pending 方法，直至执行成功。
     * 
     * 重试次数：在使用WeixinRegistry.registry()注册时可用setRetryTimes方法设置，默认2次，表示当 pending
     * 方法因 access_token 执行失败时的重试次数
     */
    R run() {
      int times = 0;
      R result = null;
      while (true) {
        try {
          result = pending();
          break;
        } catch (MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException ex) {
          if (times++ >= getRetryTimes()) {
            logger.error("For:{} >> 失败！已尝试重新执行{}次", accountKey, times - 1);
            break;
          }
          logger.error("For:{} >> 失败！第{}次尝试重新执行", accountKey, times);

          refreshAccessToken();
        }
      }

      return result;
    }

    /**
     * 具体接口请求执行内容.
     */
    @SuppressWarnings("hiding")
    abstract <R> R pending();

  }

}
