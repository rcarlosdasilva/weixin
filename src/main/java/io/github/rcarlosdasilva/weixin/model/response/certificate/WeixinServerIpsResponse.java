package io.github.rcarlosdasilva.weixin.model.response.certificate;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WeixinServerIpsResponse {

  @SerializedName("ip_list")
  private List<String> ipList;

  /**
   * 微信服务器IP地址列表.
   * 
   * @return ips
   */
  public List<String> getIpList() {
    return ipList;
  }

}
