package io.github.rcarlosdasilva.weixin.terms

private const val URL_API_DOMAIN = "https://api.weixin.qq.com/"
private const val URL_MP_DOMAIN = "https://mp.weixin.qq.com/"
private const val URL_OPEN_DOMAIN = "https://open.weixin.qq.com/"
private const val URL_FILE_DOMAIN = "https://file.api.weixin.qq.com/"
private const val URL_API_DOMAIN_WITHOUT_SSL = "http://api.weixin.qq.com/"

// ------------------------ Certificate ----------------------
const val URL_CERTIFICATE_TOKEN = "${URL_API_DOMAIN}cgi-bin/token"
const val URL_CERTIFICATE_SERVER_IP = "${URL_API_DOMAIN}cgi-bin/getcallbackip"
const val URL_CERTIFICATE_JSAPI_TICKET = "${URL_API_DOMAIN}cgi-bin/ticket/getticket"
const val URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN = "${URL_API_DOMAIN}sns/oauth2/access_token"
const val URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_REFRESH = "${URL_API_DOMAIN}sns/oauth2/refresh_token"
const val URL_CERTIFICATE_WEB_AUTHORIZE_TOKEN_VERIFY = "${URL_API_DOMAIN}sns/auth"

const val URL_WEB_AUTHORIZE = "${URL_OPEN_DOMAIN}connect/oauth2/authorize"
// ======================== Certificate ======================

// ------------------------ User Tag ----------------------
const val URL_USER_TAG_CREATE = "${URL_API_DOMAIN}cgi-bin/tags/create"
const val URL_USER_TAG_LIST = "${URL_API_DOMAIN}cgi-bin/tags/get"
const val URL_USER_TAG_UPDATE = "${URL_API_DOMAIN}cgi-bin/tags/update"
const val URL_USER_TAG_DELETE = "${URL_API_DOMAIN}cgi-bin/tags/delete"
const val URL_USER_TAG_TAGGING_USER = "${URL_API_DOMAIN}cgi-bin/tags/members/batchtagging"
const val URL_USER_TAG_UNTAGGING_FROM_USER = "${URL_API_DOMAIN}cgi-bin/tags/members/batchuntagging"
const val URL_USER_TAG_LIST_BASE_USER = "${URL_API_DOMAIN}cgi-bin/tags/getidlist"
// ======================== User Tag ======================

// ------------------------ User ----------------------
const val URL_USER_REMARK_NAME = "${URL_API_DOMAIN}cgi-bin/user/info/updateremark"
const val URL_USER_INFO = "${URL_API_DOMAIN}cgi-bin/user/info"
const val URL_USER_INFO_LIST = "${URL_API_DOMAIN}cgi-bin/user/info/batchget"
const val URL_USER_ALL_OPENID_LIST = "${URL_API_DOMAIN}cgi-bin/user/get"
const val URL_USER_INFO_BY_WEB_AUTHORIZE = "${URL_API_DOMAIN}sns/userinfo"
const val URL_USER_OPENID_LIST_WITH_TAG = "${URL_API_DOMAIN}cgi-bin/user/tag/get"
// ======================== User ======================

// ------------------------ Black List ----------------------
const val URL_BLACK_LIST_QUERY = "${URL_API_DOMAIN}cgi-bin/tags/members/getblacklist"
const val URL_BLACK_LIST_APPEND = "${URL_API_DOMAIN}cgi-bin/tags/members/batchblacklist"
const val URL_BLACK_LIST_CANCEL = "${URL_API_DOMAIN}cgi-bin/tags/members/batchunblacklist"
// ======================== Black List ======================

// ------------------------ Menu ----------------------
const val URL_MENU_CREATE = "${URL_API_DOMAIN}cgi-bin/menu/create"
const val URL_MENU_QUERY = "${URL_API_DOMAIN}cgi-bin/menu/get"
const val URL_MENU_DELETE = "${URL_API_DOMAIN}cgi-bin/menu/delete"
const val URL_MENU_CONDITIONAL_CREATE = "${URL_API_DOMAIN}cgi-bin/menu/addconditional"
const val URL_MENU_CONDITIONAL_DELETE = "${URL_API_DOMAIN}cgi-bin/menu/delconditional"
const val URL_MENU_CONDITIONAL_TEST = "${URL_API_DOMAIN}cgi-bin/menu/trymatch"
const val URL_MENU_QUERY_COMPLETE = "${URL_API_DOMAIN}cgi-bin/get_current_selfmenu_info"
// ======================== Menu ======================

// ------------------------ Template ----------------------
const val URL_TEMPLATE_INDUSTRY_SET = "${URL_API_DOMAIN}cgi-bin/template/api_set_industry"
const val URL_TEMPLATE_INDUSTRY_GET = "${URL_API_DOMAIN}cgi-bin/template/get_industry"
const val URL_TEMPLATE_APPEND = "${URL_API_DOMAIN}cgi-bin/template/api_add_template"
const val URL_TEMPLATE_DELETE = "${URL_API_DOMAIN}cgi-bin/template/del_private_template"
const val URL_TEMPLATE_QUERY = "${URL_API_DOMAIN}cgi-bin/template/get_all_private_template"
// ======================== Template ======================

// ------------------------ Customer Service ----------------------
const val URL_CUSTOMER_SERVICE_ACCOUNT_LIST = "${URL_API_DOMAIN}cgi-bin/customservice/getkflist"
const val URL_CUSTOMER_SERVICE_ACCOUNT_LIST_ONLINE = "${URL_API_DOMAIN}cgi-bin/customservice/getonlinekflist"
const val URL_CUSTOMER_SERVICE_ACCOUNT_INVITE_BINDING = "${URL_API_DOMAIN}customservice/kfaccount/inviteworker"
const val URL_CUSTOMER_SERVICE_ACCOUNT_APPEND = "${URL_API_DOMAIN}customservice/kfaccount/add"
const val URL_CUSTOMER_SERVICE_ACCOUNT_UPDATE = "${URL_API_DOMAIN}customservice/kfaccount/update"
const val URL_CUSTOMER_SERVICE_ACCOUNT_DELETE = "${URL_API_DOMAIN}customservice/kfaccount/del"
const val URL_CUSTOMER_SERVICE_ACCOUNT_UPLOAD_AVATAR = "${URL_API_DOMAIN}customservice/kfaccount/uploadheadimg"
const val URL_CUSTOMER_SERVICE_SESSION_CREATE = "${URL_API_DOMAIN}customservice/kfsession/create"
const val URL_CUSTOMER_SERVICE_SESSION_CLOSE = "${URL_API_DOMAIN}customservice/kfsession/close"
const val URL_CUSTOMER_SERVICE_SESSION_STATUS = "${URL_API_DOMAIN}customservice/kfsession/getsession"
const val URL_CUSTOMER_SERVICE_SESSION_LIST = "${URL_API_DOMAIN}customservice/kfsession/getsessionlist"
const val URL_CUSTOMER_SERVICE_SESSION_WAITINGS = "${URL_API_DOMAIN}customservice/kfsession/getwaitcase"
const val URL_CUSTOMER_SERVICE_MESSAGE_RECORDS = "${URL_API_DOMAIN}customservice/msgrecord/getmsglist"
// ======================== Customer Service ======================

// ------------------------ Message ----------------------
const val URL_MESSAGE_SEND_WITH_TEMPLATE = "${URL_API_DOMAIN}cgi-bin/message/template/send"
const val URL_MESSAGE_SEND_WITH_CUSTOM = "${URL_API_DOMAIN}cgi-bin/message/custom/send"
const val URL_MESSAGE_QUERY_AUTO_REPLY_STATUS = "${URL_API_DOMAIN}cgi-bin/get_current_autoreply_info"
const val URL_MESSAGE_SEND_WITH_MASS_FOR_TAG = "${URL_API_DOMAIN}cgi-bin/message/mass/sendall"
const val URL_MESSAGE_SEND_WITH_MASS_FOR_USERS = "${URL_API_DOMAIN}cgi-bin/message/mass/send"
const val URL_MESSAGE_DELETE_MASS = "${URL_API_DOMAIN}cgi-bin/message/mass/delete"
const val URL_MESSAGE_SEND_WITH_MASS_PREVIEW = "${URL_API_DOMAIN}cgi-bin/message/mass/preview"
const val URL_MESSAGE_QUERY_MASS_STATUS = "${URL_API_DOMAIN}cgi-bin/message/mass/get"
// ======================== Message ======================

// ------------------------ Media ----------------------
const val URL_MATERIAL_TEMPORARY_ADD = "${URL_API_DOMAIN}cgi-bin/media/upload"
const val URL_MATERIAL_TEMPORARY_GET = "${URL_API_DOMAIN}cgi-bin/media/get"
const val URL_MATERIAL_TEMPORARY_GET_HQ_AUDIO = "${URL_API_DOMAIN}cgi-bin/media/get/jssdk"
const val URL_MATERIAL_TIMELESS_ADD = "${URL_API_DOMAIN}cgi-bin/material/add_material"
const val URL_MATERIAL_TIMELESS_ADD_NEWS = "${URL_API_DOMAIN}cgi-bin/material/add_news"
const val URL_MATERIAL_TIMELESS_GET = "${URL_API_DOMAIN}cgi-bin/material/get_material"
const val URL_MATERIAL_TIMELESS_DELETE = "${URL_API_DOMAIN}cgi-bin/material/del_material"
const val URL_MATERIAL_TIMELESS_UPDATE = "${URL_API_DOMAIN}cgi-bin/material/update_news"
const val URL_MATERIAL_TIMELESS_COUNT = "${URL_API_DOMAIN}cgi-bin/material/get_materialcount"
const val URL_MATERIAL_TIMELESS_LIST = "${URL_API_DOMAIN}cgi-bin/material/batchget_material"
const val URL_MATERIAL_ADD_NEWS_IMAGE = "${URL_API_DOMAIN}cgi-bin/media/uploadimg"
const val URL_MATERIAL_ADD_NEWS = "${URL_API_DOMAIN}cgi-bin/media/uploadnews"
const val URL_MATERIAL_TRANSFORM_VIDEO = "${URL_API_DOMAIN}cgi-bin/media/uploadvideo"
// ======================== Media ======================

// ------------------------ NewsComment ----------------------
const val URL_NEWS_COMMENT_ABILITY_OPEN = "${URL_API_DOMAIN}cgi-bin/comment/open"
const val URL_NEWS_COMMENT_ABILITY_CLOSE = "${URL_API_DOMAIN}cgi-bin/comment/close"
const val URL_NEWS_COMMENT_LIST = "${URL_API_DOMAIN}cgi-bin/comment/list"
const val URL_NEWS_COMMENT_STAR = "${URL_API_DOMAIN}cgi-bin/comment/markelect"
const val URL_NEWS_COMMENT_UNSTAR = "${URL_API_DOMAIN}cgi-bin/comment/unmarkelect"
const val URL_NEWS_COMMENT_DELETE = "${URL_API_DOMAIN}cgi-bin/comment/delete"
const val URL_NEWS_COMMENT_REPLY = "${URL_API_DOMAIN}cgi-bin/comment/newsCommentReply/add"
const val URL_NEWS_COMMENT_REPLY_DELETE = "${URL_API_DOMAIN}cgi-bin/comment/newsCommentReply/delete"
// ======================== NewsComment ======================

// ------------------------ Statistics ----------------------
const val URL_STATISTICS_USER_SUMMARY = "${URL_API_DOMAIN}datacube/getusersummary"
const val URL_STATISTICS_USER_CUMULATE = "${URL_API_DOMAIN}datacube/getusercumulate"
const val URL_STATISTICS_NEWS_SUMMARY = "${URL_API_DOMAIN}datacube/getarticlesummary"
const val URL_STATISTICS_NEWS_TOTAL = "${URL_API_DOMAIN}datacube/getarticletotal"
const val URL_STATISTICS_NEWS_READ = "${URL_API_DOMAIN}datacube/getuserread"
const val URL_STATISTICS_NEWS_READ_HOUR = "${URL_API_DOMAIN}datacube/getuserreadhour"
const val URL_STATISTICS_NEWS_SHARE = "${URL_API_DOMAIN}datacube/getusershare"
const val URL_STATISTICS_NEWS_SHARE_HOUR = "${URL_API_DOMAIN}datacube/getusersharehour"
const val URL_STATISTICS_MESSAGE_SUMMARY = "${URL_API_DOMAIN}datacube/getupstreammsg"
const val URL_STATISTICS_MESSAGE_HOUR = "${URL_API_DOMAIN}datacube/getupstreammsghour"
const val URL_STATISTICS_MESSAGE_WEEK = "${URL_API_DOMAIN}datacube/getupstreammsgweek"
const val URL_STATISTICS_MESSAGE_MONTH = "${URL_API_DOMAIN}datacube/getupstreammsgmonth"
const val URL_STATISTICS_MESSAGE_DISTRIBUTED = "${URL_API_DOMAIN}datacube/getupstreammsgdist"
const val URL_STATISTICS_MESSAGE_DISTRIBUTED_WEEK = "${URL_API_DOMAIN}datacube/getupstreammsgdistweek"
const val URL_STATISTICS_MESSAGE_DISTRIBUTED_MONTH = "${URL_API_DOMAIN}datacube/getupstreammsgdistmonth"
const val URL_STATISTICS_INTERFACE_SUMMARY = "${URL_API_DOMAIN}datacube/getinterfacesummary"
const val URL_STATISTICS_INTERFACE_SUMMARY_HOUR = "${URL_API_DOMAIN}datacube/getinterfacesummaryhour"
// ======================== Statistics ======================

// ------------------------ Common & Helper ----------------------
const val URL_COMMON_SHORT_URL = "${URL_API_DOMAIN}cgi-bin/shorturl"
const val URL_COMMON_QR_CREATE = "${URL_API_DOMAIN}cgi-bin/qrcode/create"
const val URL_COMMON_QR_SHOW = "${URL_MP_DOMAIN}cgi-bin/showqrcode"

const val URL_COMMON_SEMANTIC_ANALYZE = "${URL_API_DOMAIN}semantic/semproxy/search"

const val URL_HELPER_RESET_QUOTA = "${URL_API_DOMAIN}cgi-bin/clear_quota"
// ======================== Common & Helper ======================

// ------------------------ Open Platform Auth ----------------------
const val URL_OPEN_PLATFORM_ACCESS_TOKEN = "${URL_API_DOMAIN}cgi-bin/component/api_component_token"
const val URL_OPEN_PLATFORM_PRE_AUTH_CODE = "${URL_API_DOMAIN}cgi-bin/component/api_create_preauthcode"
const val URL_OPEN_PLATFORM_LICENSE_INFORMATION = "${URL_API_DOMAIN}cgi-bin/component/api_query_auth"
const val URL_OPEN_PLATFORM_REFRESH_LICENSOR_ACCESS_TOKEN = "${URL_API_DOMAIN}cgi-bin/component/api_authorizer_token"
const val URL_OPEN_PLATFORM_LICENSOR_INFORMATION = "${URL_API_DOMAIN}cgi-bin/component/api_get_authorizer_info"
const val URL_OPEN_PLATFORM_GET_LICENSOR_OPTION = "${URL_API_DOMAIN}cgi-bin/component/api_get_authorizer_option"
const val URL_OPEN_PLATFORM_SET_LICENSOR_OPTION = "${URL_API_DOMAIN}cgi-bin/component/api_set_authorizer_option"
const val URL_OPEN_PLATFORM_RESET_QUOTA = "${URL_API_DOMAIN}cgi-bin/component/clear_quota"

const val URL_OPEN_PLATFORM_WEB_AUTHORIZE_TOKEN = "${URL_API_DOMAIN}sns/oauth2/component/access_token"
const val URL_OPEN_PLATFORM_WEB_AUTHORIZE_TOKEN_REFRESH = "${URL_API_DOMAIN}sns/oauth2/component/refresh_token"

const val URL_OPEN_PLATFORM_AUTHORIZE = "${URL_MP_DOMAIN}cgi-bin/componentloginpage"
// ======================== Open Platform Auth ======================