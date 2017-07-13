package io.github.rcarlosdasilva.weixin.core.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.rcarlosdasilva.weixin.common.dictionary.ResultCode;
import io.github.rcarlosdasilva.weixin.core.exception.ExecuteException;
import io.github.rcarlosdasilva.weixin.core.exception.MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException;
import io.github.rcarlosdasilva.weixin.core.json.Json;
import io.github.rcarlosdasilva.weixin.core.registry.Registration;
import io.github.rcarlosdasilva.weixin.model.response.SimplestResponse;

/**
 * 响应内容解析器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class ResponseParser {

  private static final Logger logger = LoggerFactory.getLogger(ResponseParser.class);

  private ResponseParser() {
    throw new IllegalStateException("ResponseParser class");
  }

  /**
   * 解析并封装响应结果为一个指定类型.
   * 
   * @param <T>
   *          The Type of element
   * @param target
   *          指定封装类型
   * @param json
   *          json响应字符串
   * @return 封装对象
   */
  @SuppressWarnings("unchecked")
  public static <T> T parse(Class<T> target, String json) {
    if (SimplestResponse.seemsLikeError(json)) {
      SimplestResponse errorResponse = Json.fromJson(json, SimplestResponse.class);
      Boolean success = errorResponse.getErrorCode() == ResultCode.RESULT_0.getCode();

      if (!success) {
        ResultCode resultCode = ResultCode.byCode(errorResponse.getErrorCode());
        if (resultCode == null) {
          logger.warn("未收录的微信错误代码: code [{}]", errorResponse.getErrorCode());
        }
        logger.error("微信请求错误：code [{}] -- message [{}]", errorResponse.getErrorCode(),
            errorResponse.getErrorMessage());

        if (errorResponse.isBadAccessToken()) {
          logger.error("微信说access_token不大行，那我觉着是不是还可以再抢救一下，再试一遍来");
          throw new MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException();
        }

        if (Registration.getInstance().getSetting().isThrowException()) {
          throw new ExecuteException(errorResponse);
        }

        if (target == Boolean.class) {
          return (T) Boolean.FALSE;
        } else {
          return null;
        }
      }
    }

    if (target == Boolean.class) {
      return (T) Boolean.TRUE;
    } else {
      return Json.fromJson(json, target);
    }
  }

}
