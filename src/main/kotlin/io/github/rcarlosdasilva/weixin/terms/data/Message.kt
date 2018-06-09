package io.github.rcarlosdasilva.weixin.terms.data

/**
 * 客服消息推送类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class MessageType(val text: String) {

  /**
   * 文本
   */
  TEXT("text"),
  /**
   * 图片
   */
  IMAGE("image"),
  /**
   * 语音
   */
  VOICE("voice"),
  /**
   * 视频
   */
  VIDEO("video"),
  /**
   * 消息推送时的视频类型
   */
  MPVIDEO("mpvideo"),
  /**
   * 音乐
   */
  MUSIC("music"),
  /**
   * 图文消息（点击跳转到外链）
   */
  NEWS_EXTERNAL("news"),
  /**
   * 图文消息（点击跳转到图文消息页面）
   */
  NEWS_INTERNAL("mpnews"),
  /**
   * 卡券
   */
  CARD("wxcard");

  override fun toString(): String = text

  companion object {
    fun with(text: String?): MessageType? = text?.let { values().find { it.text.equals(text, ignoreCase = true) } }
  }

}

/**
 * 素材类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class MaterialType(val text: String) {

  /**
   * 图片
   */
  IMAGE("image"),
  /**
   * 语音
   */
  VOICE("voice"),
  /**
   * 视频
   */
  VIDEO("video"),
  /**
   * 缩略图
   */
  THUMBNAIL("thumb"),
  /**
   * 图文
   */
  NEWS("news");

  override fun toString(): String = text

}

/**
 * 图文消息评论类型
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class NewsCommentType(val code: Int) {

  /**
   * 普通评论和精选评论
   */
  ALL(0),
  /**
   * 普通评论
   */
  NORMAL(1),
  /**
   * 精选评论
   */
  STAR(2)

}


/**
 * 模板所在行业
 *
 * @author [Dean Zhao](mailto:rcarlosdasilva@qq.com)
 */
enum class Industry(val code: Int) {

  /** IT科技 互联网/电子商务 */
  IT_INTERNET_AND_E_COMMERCE(1),
  /** IT科技 IT软件与服务 */
  IT_SOFTWARE_AND_SERVICE(2),
  /** IT科技 IT硬件与设备 */
  IT_HARDWARE_AND_DEVICE(3),
  /** IT科技 电子技术 */
  IT_ELECTRONIC_TECHNIQUE(4),
  /** IT科技 通信与运营商 */
  IT_COMMUNICATION_AND_OPERATOR(5),
  /** IT科技 网络游戏 */
  IT_ONLINE_GAME(6),
  /** 金融业 银行 */
  FINANCIAL_BANK(7),
  /** 金融业 基金|理财|信托 */
  FINANCIAL_FUNK_ENTRUST(8),
  /** 金融业 保险 */
  FINANCIAL_INSURANCE(9),
  /** 餐饮 餐饮 */
  CATERING_INDUSTRY(10),
  /** 酒店旅游 酒店 */
  HOTEL_INDUSTRY(11),
  /** 酒店旅游 旅游 */
  TOURIST_INDUSTRY(12),
  /** 运输与仓储 快递 */
  EXPRESSAGE(13),
  /** 运输与仓储 物流 */
  PHYSICAL_DISTRIBUTION(14),
  /** 运输与仓储 仓储 */
  STORAGE(15),
  /** 教育 培训 */
  EDUCATION_TRAINING(16),
  /** 教育 院校 */
  EDUCATION_ACADEMY(17),
  /** 政府与公共事业 学术科研 */
  GOVERNMENT_ACADEMIC_RESEARCH(18),
  /** 政府与公共事业 交警 */
  GOVERNMENT_TRAFFIC_POLICE(19),
  /** 政府与公共事业 博物馆 */
  GOVERNMENT_MUSEUM(20),
  /** 政府与公共事业 公共事业|非盈利机构 */
  GOVERNMENT_NON_PROFIT_ORGANIZATIONS(21),
  /** 医药护理 医药医疗 */
  MEDICAL_DRUGS_AND_THERAPEUTICS(22),
  /** 医药护理 护理美容 */
  MEDICAL_NURSING_AND_BEAUTY(23),
  /** 医药护理 保健与卫生 */
  MEDICAL_HEALTH_AND_CARE(24),
  /** 交通工具 汽车相关 */
  VEHICLE_CAR(25),
  /** 交通工具 摩托车相关 */
  VEHICLE_MOTORCYCLE(26),
  /** 交通工具 火车相关 */
  VEHICLE_TRAIN(27),
  /** 交通工具 飞机相关 */
  VEHICLE_AIRPLANE(28),
  /** 房地产 建筑 */
  ESTATE_BUILDING(29),
  /** 房地产 物业 */
  ESTATE_PROPERTY(30),
  /** 消费品 消费品 */
  CONSUMER_GOODS(31),
  /** 商业服务 法律 */
  COMMERCIAL_SERVICE_LAW(32),
  /** 商业服务 会展 */
  COMMERCIAL_SERVICE_EXHIBITION(33),
  /** 商业服务 中介服务 */
  COMMERCIAL_SERVICE_AGENT(34),
  /** 商业服务 认证 */
  COMMERCIAL_SERVICE_AUTHENTICATION(35),
  /** 商业服务 审计 */
  COMMERCIAL_SERVICE_AUDIT(36),
  /** 文体娱乐 传媒 */
  MEDIA_INDUSTRY(37),
  /** 文体娱乐 体育 */
  SPORTS(38),
  /** 文体娱乐 娱乐休闲 */
  LEISURE_AND_ENTERTAINMENT(39),
  /** 印刷 印刷 */
  PRINTING_INDUSTRY(40),
  /** 其它 其它 */
  OTHER(41)

}
