package io.github.rcarlosdasilva.weixin.common;

/**
 * 微信约定常量值
 * 
 * @author <a href="mailto:rcarlosdasilva@qq.com">Dean Zhao</a>
 */
public class Convention {

  private Convention() {
    throw new IllegalStateException("Convention class");
  }

  public static final String DEFAULT_UNIQUE_WEIXIN_KEY = "__UNIQ__";
  public static final String DEFAULT_ENCODING = "UTF-8";

  public static final int AHEAD_OF_EXPIRED_SECONDS = 180;
  public static final int AHEAD_OF_EXPIRED_TO_REFRESH_SECONDS = 120;

  public static final String DEFAULT_CACHE_KEY_OPEN_PLATFORM_TICKET = "op_ticket";
  public static final String DEFAULT_CACHE_KEY_OPEN_PLATFORM_ACCESS_TOKEN = "op_access_token";

  public static final String DEFAULT_REDIS_KEY_PREFIX = "weixin";
  public static final String DEFAULT_REDIS_KEY_SEPARATOR = ":";
  public static final String DEFAULT_REDIS_KEY_PATTERN = "*";

  // -------------------- 接口请求 JSON 约定内容值 -------------------
  public static final int GLOBAL_TRUE_NUMBER = 1;
  public static final int GLOBAL_FALSE_NUMBER = 0;
  public static final long GLOBAL_FAIL_ID = -1;
  public static final int GLOBAL_FAIL_ID_INT = -1;

  public static final String COLOR_BLACK = "#000000";

  public static final String ACCESS_TOKEN_GRANT_TYPE = "client_credential";
  public static final String WEB_AUTHORIZE_ACCESS_TOKEN_GRANT_TYPE = "authorization_code";
  public static final String WEB_AUTHORIZE_ACCESS_TOKEN_REFRESH_GRANT_TYPE = "refresh_token";
  public static final String SHROT_URL_ACTION = "long2short";
  public static final String JS_TICKET_TYPE = "jsapi";

  public static final String CARD_QR_CODE_ACTION_SINGLE = "QR_CARD";
  public static final String CARD_QR_CODE_ACTION_MULTI = "QR_MULTIPLE_CARD";
  // ==================== 接口请求 JSON 约定内容值 ===================

  // -------------------- 接口请求 JSON 约定参数名 -------------------
  public static final String WEIXIN_IP_CACHE_KEY = "wick";

  public static final String TEMPLATE_DATA_BEGIN_KEY = "first";
  public static final String TEMPLATE_DATA_END_KEY = "remark";

  public static final String CUSTOM_AVATAR_UPLOAD_KEY = "media";
  public static final String MEDIA_FILE_UPLOAD_KEY = "media";
  public static final String MEDIA_VIDEO_FORM_KEY = "description";
  public static final String MEDIA_VIDEO_FORM_TITLE = "title";
  public static final String MEDIA_VIDEO_FORM_INTRODUCTION = "description";
  public static final String CARD_IMAGE_UPLOAD_KEY = "media";

  public static final String CARD_TYPE_KEY = "card_type";
  public static final String CARD_INFO_GROUP_KEY = "groupon";
  public static final String CARD_INFO_CASH_KEY = "cash";
  public static final String CARD_INFO_DISCOUNT_KEY = "discount";
  public static final String CARD_INFO_GIFT_KEY = "gift";
  public static final String CARD_INFO_GENERAL_GROUP_KEY = "general_coupon";

  public static final String WEIXIN_ACCESS_TOKEN_EXPIRES_IN_KEY = "expires_in";
  public static final String WEIXIN_ACCESS_TOKEN_KEY = "access_token";

  public static final String OPEN_PLATFORM_AUTH_ACCESS_TOKEN_KEY = "component_access_token";
  public static final String OPEN_PLATFORM_AUTH_LICENSED_INFORMATION_KEY = "authorization_info";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_INFORMATION_KEY = "authorizer_info";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_NICKNAME_KEY = "nick_name";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_LOGO_KEY = "head_img";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_TYPE_KEY = "service_type_info";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_VERIFIED_TYPE_KEY = "verify_type_info";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_MPID_KEY = "user_name";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_PRINCIPAL_NAME_KEY = "principal_name";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_NAME_KEY = "alias";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_QRCODE_URL_KEY = "qrcode_url";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_INFO_KEY = "business_info";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_STORE_KEY = "open_store";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SCAN_KEY = "open_scan";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_PAY_KEY = "open_pay";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_CARD_KEY = "open_card";
  public static final String OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SHAKE_KEY = "open_shake";
  public static final String OPEN_PLATFORM_AUTH_LICENSED_APPID_KEY = "authorizer_appid";
  public static final String OPEN_PLATFORM_AUTH_LICENSED_APPID_ALIAS_KEY = "appid";
  public static final String OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY = "authorizer_access_token";
  public static final String OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY = "expires_in";
  public static final String OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY = "authorizer_refresh_token";
  public static final String OPEN_PLATFORM_AUTH_LICENSED_FUNCTIONS_INFO_KEY = "func_info";

  // ==================== 接口请求 JSON 约定参数名 ===================

  // -------------------- 微信推送通知 XML 约定键名 -------------------
  public static final String WEIXIN_NOTIFICATION_RESPONSE_NOTHING = "success";

  public static final String WEIXIN_NOTIFICATION_XML_TAG_ORIGIN = "xml>";
  public static final String WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION = "notification>";
  public static final String WEIXIN_NOTIFICATION_XML_TAG_EVENT = "event>";
  public static final String WEIXIN_NOTIFICATION_XML_TAG_MESSAGE = "message>";
  public static final String WEIXIN_NOTIFICATION_XML_TAG_INFO = "info>";

  public static final String WEIXIN_NOTIFICATION_KEY_ITEM = "item";
  public static final String WEIXIN_NOTIFICATION_KEY_EVENT_PIC_LIST = "PicList";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_CONTENT = "Content";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_ID = "MediaId";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_MEDIA_THUMB_ID = "ThumbMediaId";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_TITLE = "Title";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_DESCRIPTION = "Description";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL = "MusicUrl";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_MUSIC_URL_HQ = "HQMusicUrl";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_COUNT = "ArticleCount";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_LIST = "Articles";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL = "Url";
  public static final String WEIXIN_NOTIFICATION_KEY_RESPONSE_NEWS_URL_PIC = "PicUrl";
  // ==================== 微信推送通知 XML 约定键名 ===================

}
