package io.github.rcarlosdasilva.weixin.core.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import io.github.rcarlosdasilva.weixin.core.json.adapter.AccessTokenResponseTypeAdapter;
import io.github.rcarlosdasilva.weixin.core.json.adapter.MessageSendWithMassRequestUserTypeAdapter;
import io.github.rcarlosdasilva.weixin.core.json.adapter.OpenPlatformAuthAccessTokenResponseTypeAdapter;
import io.github.rcarlosdasilva.weixin.core.json.adapter.OpenPlatformAuthGetLicenseInformationResponseTypeAdapter;
import io.github.rcarlosdasilva.weixin.model.request.message.MessageSendWithMassRequestUser;
import io.github.rcarlosdasilva.weixin.model.response.certificate.AccessTokenResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthAccessTokenResponse;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthGetLicenseInformationResponse;

public class CustomTypeAdapterFactory implements TypeAdapterFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    Class<? super T> clazz = type.getRawType();
    if (clazz == MessageSendWithMassRequestUser.class) {
      return (TypeAdapter<T>) new MessageSendWithMassRequestUserTypeAdapter().nullSafe();
    } else if (clazz == AccessTokenResponse.class) {
      return (TypeAdapter<T>) new AccessTokenResponseTypeAdapter().nullSafe();
    } else if (clazz == OpenPlatformAuthAccessTokenResponse.class) {
      return (TypeAdapter<T>) new OpenPlatformAuthAccessTokenResponseTypeAdapter().nullSafe();
    } else if (clazz == OpenPlatformAuthGetLicenseInformationResponse.class) {
      return (TypeAdapter<T>) new OpenPlatformAuthGetLicenseInformationResponseTypeAdapter()
          .nullSafe();
    }
    return null;
  }

}
