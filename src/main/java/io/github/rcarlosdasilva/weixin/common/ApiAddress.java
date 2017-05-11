package io.github.rcarlosdasilva.weixin.common;

/**
 * 公众号API地址
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class ApiAddress {

  private static final String URL_API_DOMAIN = "https://api.weixin.qq.com/";
  private static final String URL_MP_DOMAIN = "https://mp.weixin.qq.com/";
  private static final String URL_OPEN_DOMAIN = "https://open.weixin.qq.com/";
  @SuppressWarnings("unused")
  private static final String URL_FILE_DOMAIN = "https://file.api.weixin.qq.com/";
  @SuppressWarnings("unused")
  private static final String URL_API_DOMAIN_WITHOUT_SSL = "http://api.weixin.qq.com/";

  // ------------------------ Certificate ----------------------
  public static final String URL_CERTIFICATE_TOKEN = URL_API_DOMAIN + "cgi-bin/token";
  public static final String URL_CERTIFICATE_SERVER_IP = URL_API_DOMAIN + "cgi-bin/getcallbackip";
  public static final String URL_CERTIFICATE_JS_TICKET = URL_API_DOMAIN
      + "cgi-bin/ticket/getticket";
  public static final String URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN = URL_API_DOMAIN
      + "sns/oauth2/access_token";
  public static final String URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_REFRESH = URL_API_DOMAIN
      + "sns/oauth2/refresh_token";
  public static final String URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_VERIFY = URL_API_DOMAIN
      + "sns/auth";
  // ======================== Certificate ======================

  // ------------------------ User Group ----------------------
  public static final String URL_USER_GROUP_CREATE = URL_API_DOMAIN + "cgi-bin/groups/create";
  public static final String URL_USER_GROUP_LIST = URL_API_DOMAIN + "cgi-bin/groups/get";
  public static final String URL_USER_GROUP_GET = URL_API_DOMAIN + "cgi-bin/groups/getid";
  public static final String URL_USER_GROUP_UPDATE = URL_API_DOMAIN + "cgi-bin/groups/update";
  public static final String URL_USER_GROUP_MOVE_MEMBER = URL_API_DOMAIN
      + "cgi-bin/groups/members/update";
  public static final String URL_USER_GROUP_MOVE_MEMBER_BATCH = URL_API_DOMAIN
      + "cgi-bin/groups/members/batchupdate";
  public static final String URL_USER_GROUP_DELETE = URL_API_DOMAIN + "cgi-bin/groups/delete";
  // ======================== User Group ======================

  // ------------------------ User Tag ----------------------
  public static final String URL_USER_TAG_CREATE = URL_API_DOMAIN + "cgi-bin/tags/create";
  public static final String URL_USER_TAG_LIST = URL_API_DOMAIN + "cgi-bin/tags/get";
  public static final String URL_USER_TAG_UPDATE = URL_API_DOMAIN + "cgi-bin/tags/update";
  public static final String URL_USER_TAG_DELETE = URL_API_DOMAIN + "cgi-bin/tags/delete";
  public static final String URL_USER_TAG_TAGGING_USER = URL_API_DOMAIN
      + "cgi-bin/tags/members/batchtagging";
  public static final String URL_USER_TAG_UNTAGGING_FROM_USER = URL_API_DOMAIN
      + "cgi-bin/tags/members/batchuntagging";
  public static final String URL_USER_TAG_LIST_BASE_USER = URL_API_DOMAIN
      + "cgi-bin/tags/getidlist";
  // ======================== User Tag ======================

  // ------------------------ User ----------------------
  public static final String URL_USER_REMARK_NAME = URL_API_DOMAIN
      + "cgi-bin/user/info/updateremark";
  public static final String URL_USER_INFO = URL_API_DOMAIN + "cgi-bin/user/info";
  public static final String URL_USER_INFO_LIST = URL_API_DOMAIN + "cgi-bin/user/info/batchget";
  public static final String URL_USER_ALL_OPENID_LIST = URL_API_DOMAIN + "cgi-bin/user/get";
  public static final String URL_USER_INFO_BY_WEB_AUTHORIZE = URL_API_DOMAIN + "sns/userinfo";
  public static final String URL_USER_OPENID_LIST_WITH_TAG = URL_API_DOMAIN
      + "cgi-bin/user/tag/get";
  // ======================== User ======================

  // ------------------------ Black List ----------------------
  public static final String URL_BLACK_LIST_QUERY = URL_API_DOMAIN
      + "cgi-bin/tags/members/getblacklist";
  public static final String URL_BLACK_LIST_APPEND = URL_API_DOMAIN
      + "cgi-bin/tags/members/batchblacklist";
  public static final String URL_BLACK_LIST_CANCEL = URL_API_DOMAIN
      + "cgi-bin/tags/members/batchunblacklist";
  // ======================== Black List ======================

  // ------------------------ Menu ----------------------
  public static final String URL_MENU_CREATE = URL_API_DOMAIN + "cgi-bin/menu/create";
  public static final String URL_MENU_QUERY = URL_API_DOMAIN + "cgi-bin/menu/get";
  public static final String URL_MENU_DELETE = URL_API_DOMAIN + "cgi-bin/menu/delete";
  public static final String URL_MENU_CONDITIONAL_CREATE = URL_API_DOMAIN
      + "cgi-bin/menu/addconditional";
  public static final String URL_MENU_CONDITIONAL_DELETE = URL_API_DOMAIN
      + "cgi-bin/menu/delconditional";
  public static final String URL_MENU_CONDITIONAL_TEST = URL_API_DOMAIN + "cgi-bin/menu/trymatch";
  public static final String URL_MENU_QUERY_COMPLETE = URL_API_DOMAIN
      + "cgi-bin/get_current_selfmenu_info";
  // ======================== Menu ======================

  // ------------------------ Template ----------------------
  public static final String URL_TEMPLATE_INDUSTRY_SET = URL_API_DOMAIN
      + "cgi-bin/template/api_set_industry";
  public static final String URL_TEMPLATE_INDUSTRY_GET = URL_API_DOMAIN
      + "cgi-bin/template/get_industry";
  public static final String URL_TEMPLATE_APPEND = URL_API_DOMAIN
      + "cgi-bin/template/api_add_template";
  public static final String URL_TEMPLATE_DELETE = URL_API_DOMAIN
      + "cgi-bin/template/del_private_template";
  public static final String URL_TEMPLATE_QUERY = URL_API_DOMAIN
      + "cgi-bin/template/get_all_private_template";
  // ======================== Template ======================

  // ------------------------ Custom ----------------------
  public static final String URL_CUSTOM_ACCOUNT_LIST = URL_API_DOMAIN
      + "cgi-bin/customservice/getkflist";
  public static final String URL_CUSTOM_ACCOUNT_LIST_ONLINE = URL_API_DOMAIN
      + "cgi-bin/customservice/getonlinekflist";
  public static final String URL_CUSTOM_ACCOUNT_INVITE_BINDING = URL_API_DOMAIN
      + "customservice/kfaccount/inviteworker";
  public static final String URL_CUSTOM_ACCOUNT_APPEND = URL_API_DOMAIN
      + "customservice/kfaccount/add";
  public static final String URL_CUSTOM_ACCOUNT_UPDATE = URL_API_DOMAIN
      + "customservice/kfaccount/update";
  public static final String URL_CUSTOM_ACCOUNT_DELETE = URL_API_DOMAIN
      + "customservice/kfaccount/del";
  public static final String URL_CUSTOM_ACCOUNT_UPLOAD_AVATAR = URL_API_DOMAIN
      + "customservice/kfaccount/uploadheadimg";
  public static final String URL_CUSTOM_SESSION_CREATE = URL_API_DOMAIN
      + "customservice/kfsession/create";
  public static final String URL_CUSTOM_SESSION_CLOSE = URL_API_DOMAIN
      + "customservice/kfsession/close";
  public static final String URL_CUSTOM_SESSION_STATUS = URL_API_DOMAIN
      + "customservice/kfsession/getsession";
  public static final String URL_CUSTOM_SESSION_LIST = URL_API_DOMAIN
      + "customservice/kfsession/getsessionlist";
  public static final String URL_CUSTOM_SESSION_WAITINGS = URL_API_DOMAIN
      + "customservice/kfsession/getwaitcase";
  public static final String URL_CUSTOM_MESSAGE_RECORDS = URL_API_DOMAIN
      + "customservice/msgrecord/getmsglist";
  // ======================== Custom ======================

  // ------------------------ Message ----------------------
  public static final String URL_MESSAGE_SEND_WITH_TEMPLATE = URL_API_DOMAIN
      + "cgi-bin/message/template/send";
  public static final String URL_MESSAGE_SEND_WITH_CUSTOM = URL_API_DOMAIN
      + "cgi-bin/message/custom/send";
  public static final String URL_MESSAGE_QUERY_AUTO_REPLY_STATUS = URL_API_DOMAIN
      + "cgi-bin/get_current_autoreply_info";
  public static final String URL_MESSAGE_SEND_WITH_MASS_FOR_TAG = URL_API_DOMAIN
      + "cgi-bin/message/mass/sendall";
  public static final String URL_MESSAGE_SEND_WITH_MASS_FOR_USERS = URL_API_DOMAIN
      + "cgi-bin/message/mass/send";
  public static final String URL_MESSAGE_DELETE_MASS = URL_API_DOMAIN
      + "cgi-bin/message/mass/delete";
  public static final String URL_MESSAGE_SEND_WITH_MASS_PREVIEW = URL_API_DOMAIN
      + "cgi-bin/message/mass/preview";
  public static final String URL_MESSAGE_QUERY_MASS_STATUS = URL_API_DOMAIN
      + "cgi-bin/message/mass/get";
  // ======================== Message ======================

  // ------------------------ Media ----------------------
  public static final String URL_MEDIA_TEMPORARY_ADD = URL_API_DOMAIN + "cgi-bin/media/upload";
  public static final String URL_MEDIA_TEMPORARY_GET = URL_API_DOMAIN + "cgi-bin/media/get";
  public static final String URL_MEDIA_TEMPORARY_GET_HQ_AUDIO = URL_API_DOMAIN
      + "cgi-bin/media/get/jssdk";
  public static final String URL_MEDIA_TIMELESS_ADD = URL_API_DOMAIN
      + "cgi-bin/material/add_material";
  public static final String URL_MEDIA_TIMELESS_ADD_NEWS = URL_API_DOMAIN
      + "cgi-bin/material/add_news";
  public static final String URL_MEDIA_TIMELESS_GET = URL_API_DOMAIN
      + "cgi-bin/material/get_material";
  public static final String URL_MEDIA_TIMELESS_DELETE = URL_API_DOMAIN
      + "cgi-bin/material/del_material";
  public static final String URL_MEDIA_TIMELESS_UPDATE = URL_API_DOMAIN
      + "cgi-bin/material/update_news";
  public static final String URL_MEDIA_TIMELESS_COUNT = URL_API_DOMAIN
      + "cgi-bin/material/get_materialcount";
  public static final String URL_MEDIA_TIMELESS_LIST = URL_API_DOMAIN
      + "cgi-bin/material/batchget_material";
  public static final String URL_MEDIA_MASS_ADD_IMAGE = URL_API_DOMAIN + "cgi-bin/media/uploadimg";
  public static final String URL_MEDIA_MASS_ADD_NEWS = URL_API_DOMAIN + "cgi-bin/media/uploadnews";
  public static final String URL_MEDIA_MASS_TRANSFORM_VIDEO = URL_API_DOMAIN
      + "cgi-bin/media/uploadvideo";
  // ======================== Media ======================

  // ------------------------ Statistics ----------------------
  public static final String URL_STATISTICS_USER_SUMMARY = URL_API_DOMAIN
      + "datacube/getusersummary";
  public static final String URL_STATISTICS_USER_CUMULATE = URL_API_DOMAIN
      + "datacube/getusercumulate";
  public static final String URL_STATISTICS_NEWS_SUMMARY = URL_API_DOMAIN
      + "datacube/getarticlesummary";
  public static final String URL_STATISTICS_NEWS_TOTAL = URL_API_DOMAIN
      + "datacube/getarticletotal";
  public static final String URL_STATISTICS_NEWS_READ = URL_API_DOMAIN + "datacube/getuserread";
  public static final String URL_STATISTICS_NEWS_READ_HOUR = URL_API_DOMAIN
      + "datacube/getuserreadhour";
  public static final String URL_STATISTICS_NEWS_SHARE = URL_API_DOMAIN + "datacube/getusershare";
  public static final String URL_STATISTICS_NEWS_SHARE_HOUR = URL_API_DOMAIN
      + "datacube/getusersharehour";
  public static final String URL_STATISTICS_MESSAGE_SUMMARY = URL_API_DOMAIN
      + "datacube/getupstreammsg";
  public static final String URL_STATISTICS_MESSAGE_HOUR = URL_API_DOMAIN
      + "datacube/getupstreammsghour";
  public static final String URL_STATISTICS_MESSAGE_WEEK = URL_API_DOMAIN
      + "datacube/getupstreammsgweek";
  public static final String URL_STATISTICS_MESSAGE_MONTH = URL_API_DOMAIN
      + "datacube/getupstreammsgmonth";
  public static final String URL_STATISTICS_MESSAGE_DISTRIBUTED = URL_API_DOMAIN
      + "datacube/getupstreammsgdist";
  public static final String URL_STATISTICS_MESSAGE_DISTRIBUTED_WEEK = URL_API_DOMAIN
      + "datacube/getupstreammsgdistweek";
  public static final String URL_STATISTICS_MESSAGE_DISTRIBUTED_MONTH = URL_API_DOMAIN
      + "datacube/getupstreammsgdistmonth";
  public static final String URL_STATISTICS_INTERFACE_SUMMARY = URL_API_DOMAIN
      + "datacube/getinterfacesummary";
  public static final String URL_STATISTICS_INTERFACE_SUMMARY_HOUR = URL_API_DOMAIN
      + "datacube/getinterfacesummaryhour";
  // ======================== Statistics ======================

  // ------------------------ Helper ----------------------
  public static final String URL_COMMON_SHORT_URL = URL_API_DOMAIN + "cgi-bin/shorturl";
  public static final String URL_COMMON_QR_CREATE = URL_API_DOMAIN + "cgi-bin/qrcode/create";
  public static final String URL_COMMON_QR_SHOW = URL_MP_DOMAIN + "cgi-bin/showqrcode";

  public static final String URL_COMMON_SEMANTIC_ANALYZE = URL_API_DOMAIN
      + "semantic/semproxy/search";

  public static final String URL_WEB_AUTHORIZE = URL_OPEN_DOMAIN + "connect/oauth2/authorize";
  // ======================== Helper ======================

}
