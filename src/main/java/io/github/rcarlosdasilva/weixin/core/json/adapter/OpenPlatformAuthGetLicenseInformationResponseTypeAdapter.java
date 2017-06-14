package io.github.rcarlosdasilva.weixin.core.json.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensingInformation;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.bean.LicensorInfromation;

public class OpenPlatformAuthGetLicenseInformationResponseTypeAdapter
    extends TypeAdapter<OpenPlatformAuthGetLicenseInformationResponse> {

  @Override
  public void write(JsonWriter out, OpenPlatformAuthGetLicenseInformationResponse value)
      throws IOException {
    // 不需要实现
  }

  @Override
  public OpenPlatformAuthGetLicenseInformationResponse read(JsonReader in) throws IOException {
    OpenPlatformAuthGetLicenseInformationResponse model = new OpenPlatformAuthGetLicenseInformationResponse();

    in.beginObject();
    while (in.hasNext()) {
      switch (in.nextName()) {
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_INFORMATION_KEY:
          readLicensedAccessToken(in, model);
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_INFORMATION_KEY:
          readLicensorInformation(in, model);
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY:
          model.getLicensedAccessToken().setAccessToken(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY:
          model.getLicensedAccessToken().setExpiresIn(in.nextInt());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY:
          model.getLicensedAccessToken().setRefreshToken(in.nextString());
          break;
        default:
      }
    }

    in.endObject();

    return model;
  }

  private void readLicensedAccessToken(JsonReader in,
      OpenPlatformAuthGetLicenseInformationResponse model) throws IOException {
    LicensingInformation licensingInformation = new LicensingInformation();
    in.beginObject();

    while (in.hasNext()) {
      switch (in.nextName()) {
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_APPID_ALIAS_KEY:
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_APPID_KEY:
          licensingInformation.setAppId(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_KEY:
          model.getLicensedAccessToken().setAccessToken(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_ACCESS_TOKEN_EXPIRES_IN_KEY:
          model.getLicensedAccessToken().setExpiresIn(in.nextInt());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_REFRESH_TOKEN_KEY:
          model.getLicensedAccessToken().setRefreshToken(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSED_FUNCTIONS_INFO_KEY: {
          in.beginArray();

          while (in.hasNext()) {
            in.beginObject();
            in.nextName();
            in.beginObject();
            in.nextName();
            licensingInformation.addLicencedFunction(in.nextInt());
            in.endObject();
            in.endObject();
          }

          in.endArray();
          break;
        }
        default:
      }
    }

    in.endObject();
    model.setLicensingInformation(licensingInformation);
  }

  private void readLicensorInformation(JsonReader in,
      OpenPlatformAuthGetLicenseInformationResponse model) throws IOException {
    LicensorInfromation licensorInfromation = new LicensorInfromation();
    in.beginObject();

    while (in.hasNext()) {
      switch (in.nextName()) {
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_NICKNAME_KEY:
          licensorInfromation.setNickName(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_LOGO_KEY:
          licensorInfromation.setLogo(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_TYPE_KEY: {
          in.beginObject();
          in.nextName();
          licensorInfromation.setAccountType(in.nextInt());
          in.endObject();
          break;
        }
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_VERIFIED_TYPE_KEY: {
          in.beginObject();
          in.nextName();
          licensorInfromation.setAccountVerifiedType(in.nextInt());
          in.endObject();
          break;
        }
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_MPID_KEY:
          licensorInfromation.setMpId(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_PRINCIPAL_NAME_KEY:
          licensorInfromation.setPrincipalName(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_ACCOUNT_NAME_KEY:
          licensorInfromation.setAccountName(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_QRCODE_URL_KEY:
          licensorInfromation.setQrCodeUrl(in.nextString());
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_INFO_KEY:
          readBusinessInfo(in, licensorInfromation);
          break;
        default:
      }
    }

    in.endObject();
    model.setLicensorInfromation(licensorInfromation);
  }

  private void readBusinessInfo(JsonReader in, LicensorInfromation licensorInfromation)
      throws IOException {
    in.beginObject();
    while (in.hasNext()) {
      switch (in.nextName()) {
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_STORE_KEY:
          licensorInfromation.setBusinessStoreOpened(in.nextInt() == Convention.GLOBAL_TRUE_NUMBER);
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SCAN_KEY:
          licensorInfromation.setBusinessScanOpened(in.nextInt() == Convention.GLOBAL_TRUE_NUMBER);
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_PAY_KEY:
          licensorInfromation.setBusinessPayOpened(in.nextInt() == Convention.GLOBAL_TRUE_NUMBER);
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_CARD_KEY:
          licensorInfromation.setBusinessCardOpened(in.nextInt() == Convention.GLOBAL_TRUE_NUMBER);
          break;
        case Convention.OPEN_PLATFORM_AUTH_LICENSOR_BUSINESS_SHAKE_KEY:
          licensorInfromation.setBusinessShakeOpened(in.nextInt() == Convention.GLOBAL_TRUE_NUMBER);
          break;
        default:
      }
    }
    in.endObject();
  }

}
