package io.github.rcarlosdasilva.weixin.core.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.rcarlosdasilva.weixin.common.dictionary.ResultCode;
import io.github.rcarlosdasilva.weixin.core.WeixinRegistry;
import io.github.rcarlosdasilva.weixin.core.exception.ExecuteException;
import io.github.rcarlosdasilva.weixin.core.exception.MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException;
import io.github.rcarlosdasilva.weixin.core.json.Json;
import io.github.rcarlosdasilva.weixin.model.response.SimplestResponse;

/**
 * 响应内容解析器
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class ResponseParser {

  private static final Logger logger = LoggerFactory.getLogger(ResponseParser.class);

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
      SimplestResponse error = Json.fromJson(json, SimplestResponse.class);
      Boolean success = error.getErrorCode() == ResultCode.RESULT_0.getCode();

      if (!success) {
        ResultCode resultCode = ResultCode.byCode(error.getErrorCode());
        if (resultCode == null) {
          logger.error("微信请求错误：code [{}] -- message [{}]", error.getErrorCode(),
              error.getErrorMessage());
        } else {
          logger.error("微信请求错误：code [{}] -- message [{}]", resultCode.getCode(),
              resultCode.getText());
        }

        if (error.canSalvage()) {
          logger.error("微信access_token不对，我觉着还可以抢救一下，再试一遍");
          throw new MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException();
        }

        if (WeixinRegistry.getConfiguration().isThrowException()) {
          throw new ExecuteException(error);
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
