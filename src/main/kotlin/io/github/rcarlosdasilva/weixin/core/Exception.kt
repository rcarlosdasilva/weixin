package io.github.rcarlosdasilva.weixin.core

import io.github.rcarlosdasilva.weixin.model.response.Response
import io.github.rcarlosdasilva.weixin.terms.ResultCode

class ExecuteException(val errorResponse: Response, val code: ResultCode) : RuntimeException() {
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

class ApiRequestException : RuntimeException {
  constructor(message: String?) : super(message)
  constructor(message: String?, cause: Throwable?) : super(message, cause)
  constructor(cause: Throwable?) : super(cause)

  companion object {
    private const val serialVersionUID = -4734762830146513711L
  }
}
