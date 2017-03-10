package io.github.rcarlosdasilva.weixin.common;

/**
 * 微信约定常量值
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class Convention {

  public static final String DEFAULT_UNIQUE_WEIXIN_KEY = "__UNIQ__";
  public static final String DEFAULT_ENCODING = "UTF-8";

  public static final int AHEAD_OF_EXPIRED_SECONDS = 180;
  // public static final int AHEAD_OF_EXPIRED_TO_REFRESH_SECONDS = 120;

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
  // ==================== 接口请求 JSON 约定参数名 ===================

  // -------------------- 微信推送通知 XML 约定键名 -------------------
  public static final String WEIXIN_NOTIFICATION_RESPONSE_NOTHING = "success";

  public static final String WEIXIN_NOTIFICATION_XML_TAG_ORIGIN = "xml>";
  public static final String WEIXIN_NOTIFICATION_XML_TAG_NOTIFICATION = "notification>";
  public static final String WEIXIN_NOTIFICATION_XML_TAG_EVENT = "event>";
  public static final String WEIXIN_NOTIFICATION_XML_TAG_MESSAGE = "message>";

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
