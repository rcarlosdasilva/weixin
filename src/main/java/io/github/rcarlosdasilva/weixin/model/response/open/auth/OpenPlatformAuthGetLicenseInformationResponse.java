package io.github.rcarlosdasilva.weixin.model.response.open.auth;

import java.io.Serializable;

import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensedAccessToken;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensingInformation;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensorInfromation;

public class OpenPlatformAuthGetLicenseInformationResponse implements Serializable {

  private static final long serialVersionUID = 1897892931876269454L;

  private LicensedAccessToken licensedAccessToken = new LicensedAccessToken();
  private LicensingInformation licensingInformation;
  private LicensorInfromation licensorInfromation;

  public LicensedAccessToken getLicensedAccessToken() {
    return licensedAccessToken;
  }

  public LicensingInformation getLicensingInformation() {
    return licensingInformation;
  }

  public void setLicensingInformation(LicensingInformation licensingInformation) {
    this.licensingInformation = licensingInformation;
  }

  public LicensorInfromation getLicensorInfromation() {
    return licensorInfromation;
  }

  public void setLicensorInfromation(LicensorInfromation licensorInfromation) {
    this.licensorInfromation = licensorInfromation;
  }

}
