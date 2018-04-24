package io.github.rcarlosdasilva.weixin.core

import io.github.rcarlosdasilva.weixin.model.response.Response
import io.github.rcarlosdasilva.weixin.terms.ResultCode

class ExecuteException : RuntimeException {
  lateinit var errorResponse: Response
  lateinit var code: ResultCode

  constructor(errorResponse: Response, code: ResultCode) {
    this.errorResponse = errorResponse
    this.code = code
  }

  constructor(message: String?, cause: Throwable?) : super(message, cause)
  constructor(message: String?) : super(message)

  companion object {
    private const val serialVersionUID = -7464703447095351195L
  }
}

/**
 * 名字就是搞笑用的
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class MaydayMaydaySaveMeBecauseAccessTokenSetMeFuckUpException : RuntimeException() {
  companion object {
    private const val serialVersionUID = -8535962890149581715L
  }
}

class ApiRequestException(message: String?) : RuntimeException(message) {
  companion object {
    private const val serialVersionUID = -4734762830146513711L
  }
}
