package io.github.rcarlosdasilva.weixin.terms

const val DEFAULT_ENCODING = "UTF-8"

const val AHEAD_OF_EXPIRED_SECONDS = 180
const val AHEAD_OF_EXPIRED_TO_REFRESH_SECONDS = 120

const val DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET = "op_ticket"
const val DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN = "op_access_token"

const val DEFAULT_REDIS_KEY_PREFIX = "weixin"
const val DEFAULT_REDIS_KEY_SEPARATOR = ":"
const val DEFAULT_REDIS_KEY_PATTERN = "*"

const val UNIQUE_OP_ACCOUNT_CACHE_KEY = "unique_op_account"
const val ACCOUNT_PLATFORM_TYPE_OP = "op"
const val ACCOUNT_PLATFORM_TYPE_MP = "mp"

// -------------------- 接口请求 JSON 约定内容值 -------------------
const val GLOBAL_TRUE_NUMBER = 1
const val GLOBAL_FALSE_NUMBER = 0
const val GLOBAL_FAIL_ID: Long = -1
const val GLOBAL_FAIL_ID_INT = -1

const val COLOR_BLACK = "#000000"

const val ACCESS_TOKEN_GRANT_TYPE = "client_credential"
const val WEB_AUTHORIZE_ACCESS_TOKEN_GRANT_TYPE = "authorization_code"
const val WEB_AUTHORIZE_ACCESS_TOKEN_REFRESH_GRANT_TYPE = "refresh_token"
const val SHROT_URL_ACTION = "long2short"
const val JSAPI_TICKET_TYPE = "jsapi"

const val CARD_QR_CODE_ACTION_SINGLE = "QR_CARD"
const val CARD_QR_CODE_ACTION_MULTI = "QR_MULTIPLE_CARD"
// ==================== 接口请求 JSON 约定内容值 ===================

// -------------------- 接口请求 JSON 约定参数名 -------------------
const val WEIXIN_IP_CACHE_KEY = "wick"

const val TEMPLATE_DATA_BEGIN_KEY = "first"
const val TEMPLATE_DATA_END_KEY = "remark"

const val CUSTOM_AVATAR_UPLOAD_KEY = "media"
const val MATERIAL_FILE_UPLOAD_KEY = "media"
const val MATERIAL_VIDEO_FORM_KEY = "description"
const val MATERIAL_VIDEO_FORM_TITLE = "title"
const val MATERIAL_VIDEO_FORM_INTRODUCTION = "description"
const val CARD_IMAGE_UPLOAD_KEY = "media"

const val CARD_TYPE_KEY = "card_type"
const val CARD_INFO_GROUP_KEY = "groupon"
const val CARD_INFO_CASH_KEY = "cash"
const val CARD_INFO_DISCOUNT_KEY = "discount"
const val CARD_INFO_GIFT_KEY = "gift"
const val CARD_INFO_GENERAL_GROUP_KEY = "general_coupon"

const val WEIXIN_ACCESS_TOKEN_EXPIRES_IN_KEY = "expires_in"
const val WEIXIN_ACCESS_TOKEN_KEY = "access_token"

const val OPEN_PLATFORM_AUTH_ACCESS_TOKEN_KEY = "component_access_token"
const val OPEN_PLATFORM_AUTH_LICENSING_INFORMATION_KEY = "authorization_info"
const val OPEN_PLATFORM_AUTH_LICENSOR_INFORMATION_KEY = "authorizer_info"
const val OPEN_PLATFORM_AUTH_LICENSOR_NICKNAME_KEY = "nick_name"
const val OPEN_PLATFORM_AUTH_LICENSOR_LOGO_KEY = "head_img"
const val OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_TYPE_KEY = "service_type_info"
const val OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_VERIFIED_TYPE_KEY = "verify_type_info"
const val OPEN_PLATFORM_AUTH_LICENSOR_MPID_KEY = "user_name"
const val OPEN_PLATFORM_AUTH_LICENSOR_PRINCIPAL_NAME_KEY = "principal_name"
const val OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_NAME_KEY = "alias"
const val OPEN_PLATFORM_AUTH_LICENSOR_QRCODE_URL_KEY = "qrcode_url"
const val OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_INFO_KEY = "business_info"
const val OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_STORE_KEY = "open_store"
const val OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SCAN_KEY = "open_scan"
const val OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_PAY_KEY = "open_pay"
const val OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_CARD_KEY = "open_card"
const val OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SHAKE_KEY = "open_shake"
const val OPEN_PLATFORM_AUTH_LICENSED_APPID_KEY = "authorizer_appid"
const val OPEN_PLATFORM_AUTH_LICENSED_APPID_ALIAS_KEY = "appid"
const val OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY = "authorizer_access_token"
const val OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY = "expires_in"
const val OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY = "authorizer_refresh_token"
const val OPEN_PLATFORM_AUTH_LICENSED_FUNCTIONS_INFO_KEY = "func_info"

// ==================== 接口请求 JSON 约定参数名 ===================

// -------------------- 微信推送通知 XML 约定键名 -------------------
const val WEIXIN_NOTIFICATION_RESPONSE_NOTHING = "success"

const val WEIXIN_NOTIFICATION_XML_TAG_ORIGIN = "xml>"
const val WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION = "notification>"
const val WEIXIN_NOTIFICATION_XML_TAG_EVENT = "event>"
const val WEIXIN_NOTIFICATION_XML_TAG_MESSAGE = "message>"
const val WEIXIN_NOTIFICATION_XML_TAG_INFO = "info>"

const val WEIXIN_NOTIFICATION_KEY_ITEM = "item"
const val WEIXIN_NOTIFICATION_KEY_EVENT_PIC_LIST = "PicList"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_CONTENT = "Content"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_MATERIAL_ID = "MediaId"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_MATERIAL_THUMB_ID = "ThumbMediaId"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE = "Title"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION = "Description"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL = "MusicUrl"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL_HQ = "HQMusicUrl"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_COUNT = "ArticleCount"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_LIST = "Articles"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL = "Url"
const val WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL_PIC = "PicUrl"
// ==================== 微信推送通知 XML 约定键名 ===================