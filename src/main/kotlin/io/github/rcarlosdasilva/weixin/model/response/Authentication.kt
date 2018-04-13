package io.github.rcarlosdasilva.weixin.model.response

import com.google.gson.annotations.SerializedName
import io.github.rcarlosdasilva.weixin.model.AccessToken
import io.github.rcarlosdasilva.weixin.model.JsapiTicket
import io.github.rcarlosdasilva.weixin.terms.data.MpType
import io.github.rcarlosdasilva.weixin.terms.data.OpLicenseFunction
import io.github.rcarlosdasilva.weixin.terms.data.OpMpAuthentication
import java.io.Serializable

class MpAccessTokenResponse : AccessToken() {
  companion object {
    private const val serialVersionUID = -7038003373865024350L
  }
}

class JsapiTicketResponse : JsapiTicket() {
  companion object {
    private const val serialVersionUID = -1273892049807763672L
  }
}

class WaAccessTokenResponse : AccessToken() {
  /**
   * 用户唯一标识
   */
  @SerializedName("openid")
  val openId: String? = null
  /**
   * 用户授权的作用域，使用逗号（,）分隔
   */
  val scope: String? = null

  companion object {
    private const val serialVersionUID = -1030922028407583672L
  }
}

// ------------------------- 开放平台 ------------------------------

class OpAccessTokenResponse : AccessToken() {
  companion object {
    private const val serialVersionUID = -6667725430136016149L
  }
}

class OpPreAuthCodeResponse : Serializable {
  /**
   * 预授权码
   */
  @SerializedName("pre_auth_code")
  val preAuthCode: String? = null
  /**
   * 有效期，为20分钟（返回600）。
   */
  @SerializedName("expires_in")
  val expiresIn: String? = null

  companion object {
    private const val serialVersionUID = 4514336702159627804L
  }
}

class OpGetLicenseInformationResponse : Serializable {
  var licensedAccessToken = LicensedAccessToken()
  var licensingInformation: LicensingInformation? = null
  var licensorInformation: LicensorInformation? = null

  /**
   * 授权调用凭据（令牌）
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  class LicensedAccessToken : AccessToken() {
    companion object {
      private const val serialVersionUID = -7762412237643094852L
    }
  }

  /**
   * 授权信息
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  class LicensingInformation : Serializable {
    /**
     * 授权方appid，即公众号或小程序的appid
     */
    var appId: String? = null
    /**
     * 授权方授权的功能ID
     */
    val functionIds = mutableListOf<Int>()
    /**
     * 授权方授权的功能
     */
    val functions: List<OpLicenseFunction>
      get() = functionIds.map { OpLicenseFunction.with(it) }

    companion object {
      private const val serialVersionUID = -1687096878280046105L
    }
  }

  /**
   * 授权者信息
   *
   * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
   */
  class LicensorInformation : Serializable {
    /**
     * 授权方昵称(nick_name)
     */
    var nickName: String? = null
    /**
     * 授权方头像(head_img)
     */
    var logo: String? = null
    /**
     * 授权方公众号类型(service_type_info)
     *
     * 0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
     */
    var accountType: MpType? = null
    /**
     * 授权方认证类型(verify_type_info)
     *
     * -1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，
     * 3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，
     * 但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
     */
    var accountVerifiedType: OpMpAuthentication? = null
    /**
     * 授权方公众号的原始ID(user_name)
     */
    var mpId: String? = null
    /**
     * 公众号的主体名称(principal_name)
     */
    var principalName: String? = null
    /**
     * 授权方公众号所设置的微信号，可能为空(alias)
     */
    var accountName: String? = null
    /**
     * 二维码图片的URL，开发者最好自行也进行保存(qrcode_url)
     */
    var qrCodeUrl: String? = null
    /**
     * open_store:是否开通微信门店功能
     */
    var isBusinessStoreOpened: Boolean = false
    /**
     * open_scan:是否开通微信扫商品功能
     */
    var isBusinessScanOpened: Boolean = false
    /**
     * open_pay:是否开通微信支付功能
     */
    var isBusinessPayOpened: Boolean = false
    /**
     * open_card:是否开通微信卡券功能
     */
    var isBusinessCardOpened: Boolean = false
    /**
     * open_shake:是否开通微信摇一摇功能
     */
    var isBusinessShakeOpened: Boolean = false

    companion object {
      private const val serialVersionUID = 4147462423543629448L
    }
  }

  companion object {
    private const val serialVersionUID = 1897892931876269454L
  }
}


class OpGetLicensorOptionResponse {
  /**
   * 授权公众号或小程序的appid
   */
  @SerializedName("authorizer_appid")
  val licensorAppId: String? = null
  /**
   * 选项名称
   *
   * @return name
   */
  @SerializedName("option_name")
  val optionName: String? = null
  /**
   * 选项值
   *
   * @return value
   */
  @SerializedName("option_value")
  val value: String? = null
}
