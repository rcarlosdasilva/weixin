package io.github.rcarlosdasilva.weixin.model.request.common;

import io.github.rcarlosdasilva.weixin.common.ApiAddress;
import io.github.rcarlosdasilva.weixin.model.request.base.BasicRequest;

/**
 * 显示二维码请求模型
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class QrCodeShowRequest extends BasicRequest {

  private String ticket;

  public QrCodeShowRequest() {
    this.path = ApiAddress.URL_COMMON_QR_SHOW;
  }

  /**
   * 二维码请求Ticket.
   */
  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  @Override
  public String toString() {
    return new StringBuilder(this.path).append("?ticket=").append(this.ticket).toString();
  }

}
