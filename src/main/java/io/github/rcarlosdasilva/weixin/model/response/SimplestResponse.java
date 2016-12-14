package io.github.rcarlosdasilva.weixin.model.response;

import com.google.gson.annotations.SerializedName;

import io.github.rcarlosdasilva.weixin.common.dictionary.ResultCode;

/**
 * 最简微信响应模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class SimplestResponse {

  private static final String errorResponseRegex = ".*errcode.*errmsg.*";
  private static final int resultInvalidCredential = ResultCode.RESULT_40001.getCode();
  private static final int resultInvalidToken = ResultCode.RESULT_40014.getCode();
  private static final int resultExpiredToken = ResultCode.RESULT_42001.getCode();

  @SerializedName("errcode")
  private int errorCode;
  @SerializedName("errmsg")
  private String errorMessage;

  /**
   * 响应数据类似错误代码，只包含errcode和errmsg.
   * 
   * @param json
   *          结果json
   * @return is error
   */
  public static boolean seemsLikeError(String json) {
    return json.matches(errorResponseRegex);
  }

  /**
   * 判断微信错误代码是否因为access_token导致，如果access_token过期或非法，
   * 有可能是因为其他地方请求过access_token，可以再抢救一下（用新的access_token重新请求一次）.
   * 
   * @return can retry
   */
  public boolean canSalvage() {
    return this.errorCode == resultInvalidCredential || this.errorCode == resultInvalidToken
        || this.errorCode == resultExpiredToken;
  }

  /**
   * 错误代码.
   * 
   * @return code
   */
  public int getErrorCode() {
    return errorCode;
  }

  /**
   * 错误信息.
   * 
   * @return message
   */
  public String getErrorMessage() {
    return errorMessage;
  }

}
