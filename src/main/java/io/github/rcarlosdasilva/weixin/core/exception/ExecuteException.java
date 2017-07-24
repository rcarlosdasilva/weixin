package io.github.rcarlosdasilva.weixin.core.exception;

import io.github.rcarlosdasilva.weixin.common.dictionary.ResultCode;
import io.github.rcarlosdasilva.weixin.model.response.SimplestResponse;

public class ExecuteException extends RuntimeException {

  private static final long serialVersionUID = -7464703447095351195L;

  private SimplestResponse errorResponse;
  private ResultCode code;

  public ExecuteException(SimplestResponse errorResponse, ResultCode code) {
    this.errorResponse = errorResponse;
    this.code = code;
  }

  public SimplestResponse getErrorResponse() {
    return errorResponse;
  }

  public ResultCode getCode() {
    return code;
  }

}
