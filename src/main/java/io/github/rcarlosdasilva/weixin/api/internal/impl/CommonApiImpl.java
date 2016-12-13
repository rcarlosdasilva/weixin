package io.github.rcarlosdasilva.weixin.api.internal.impl;

import java.io.InputStream;
import java.util.List;

import io.github.rcarlosdasilva.weixin.api.internal.BasicApi;
import io.github.rcarlosdasilva.weixin.api.internal.CommonApi;
import io.github.rcarlosdasilva.weixin.common.dictionary.QrCodeAction;
import io.github.rcarlosdasilva.weixin.model.request.common.QrCodeCreateRequest;
import io.github.rcarlosdasilva.weixin.model.request.common.QrCodeShowRequest;
import io.github.rcarlosdasilva.weixin.model.request.common.ShortUrlRequest;
import io.github.rcarlosdasilva.weixin.model.request.common.WeixinServerIpsRequest;
import io.github.rcarlosdasilva.weixin.model.response.certificate.WeixinServerIpsResponse;
import io.github.rcarlosdasilva.weixin.model.response.common.QrCodeCreateResponse;
import io.github.rcarlosdasilva.weixin.model.response.common.ShortUrlResponse;

/**
 * 公共相关API实现
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class CommonApiImpl extends BasicApi implements CommonApi {

  @Override
  public List<String> getWeixinIps() {
    WeixinServerIpsRequest requestModel = new WeixinServerIpsRequest();

    WeixinServerIpsResponse responseModel = get(WeixinServerIpsResponse.class, requestModel);

    return responseModel == null ? null : responseModel.getIpList();
  }

  @Override
  public String getShortUrl(String url) {
    ShortUrlRequest requestModel = new ShortUrlRequest();
    requestModel.setUrl(url);

    ShortUrlResponse responseModel = post(ShortUrlResponse.class, requestModel);

    return responseModel == null ? null : responseModel.getShortUrl();
  }

  @Override
  public QrCodeCreateResponse createQrWithTemporary(long expireSeconds, int sceneId) {
    QrCodeCreateRequest requestModel = new QrCodeCreateRequest();
    requestModel.setAction(QrCodeAction.TEMPORARY);
    requestModel.setExpireSeconds(expireSeconds);
    requestModel.setSceneId(sceneId);

    return requestCreateQr(requestModel);
  }

  @Override
  public QrCodeCreateResponse createQrWithUnlimited(int sceneId) {
    QrCodeCreateRequest requestModel = new QrCodeCreateRequest();
    requestModel.setAction(QrCodeAction.UNLIMITED_WITH_ID);
    requestModel.setSceneId(sceneId);

    return requestCreateQr(requestModel);
  }

  @Override
  public QrCodeCreateResponse createQrWithUnlimited(String sceneString) {
    QrCodeCreateRequest requestModel = new QrCodeCreateRequest();
    requestModel.setAction(QrCodeAction.UNLIMITED_WITH_STRING);
    requestModel.setSceneString(sceneString);

    return requestCreateQr(requestModel);
  }

  private QrCodeCreateResponse requestCreateQr(QrCodeCreateRequest requestModel) {
    return post(QrCodeCreateResponse.class, requestModel);
  }

  @Override
  public InputStream qrImage(QrCodeCreateResponse qrResponse) {
    QrCodeShowRequest requestModel = new QrCodeShowRequest();
    requestModel.setTicket(qrResponse.getTicket());

    return getStream(requestModel);
  }

  @Override
  public InputStream qrImageWithTemporary(long expireSeconds, int senceId) {
    QrCodeCreateResponse responseModel = createQrWithTemporary(expireSeconds, senceId);
    return qrImage(responseModel);
  }

  @Override
  public InputStream qrImageWithUnlimited(int senceId) {
    QrCodeCreateResponse responseModel = createQrWithUnlimited(senceId);
    return qrImage(responseModel);
  }

  @Override
  public InputStream qrImageWithUnlimited(String senceString) {
    QrCodeCreateResponse responseModel = createQrWithUnlimited(senceString);
    return qrImage(responseModel);
  }

}
