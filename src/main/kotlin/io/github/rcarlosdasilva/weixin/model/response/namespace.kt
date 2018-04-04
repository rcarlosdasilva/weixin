package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.terms.ResultCode

/**
 * 最简微信响应模型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
class Response {

  /**
   * 错误代码.
   */
  @SerializedName("errcode")
  val errorCode = 0
  /**
   * 错误信息.
   */
  @SerializedName("errmsg")
  val errorMessage: String? = null

  /**
   * 判断微信错误代码是否因为access_token导致，如果access_token过期或非法，
   * 有可能是因为其他地方请求过access_token，可以再抢救一下（用新的access_token重新请求一次）.
   *
   * @return can retry
   */
  val isBadAccessToken: Boolean
    get() = this.errorCode == ResultCode.RESULT_40001.code
        || this.errorCode == ResultCode.RESULT_40014.code
        || this.errorCode == ResultCode.RESULT_42001.code

  companion object {

    private val errorResponseRegex = ".*errcode.*errmsg.*".toRegex()

    /**
     * 响应数据类似错误代码，只包含errcode和errmsg.
     *
     * @param json 结果json
     * @return is error
     */
    fun seemsLikeError(json: String): Boolean {
      return json.matches(errorResponseRegex)
    }
  }

}