package io.github.rcarlosdasilva.weixin.core.json.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthAccessTokenResponse;

public class OpenPlatformAuthAccessTokenResponseTypeAdapter
    extends TypeAdapter<OpenPlatformAuthAccessTokenResponse> {

  @Override
  public void write(JsonWriter out, OpenPlatformAuthAccessTokenResponse value) throws IOException {

  }

  @Override
  public OpenPlatformAuthAccessTokenResponse read(JsonReader in) throws IOException {
    OpenPlatformAuthAccessTokenResponse model = new OpenPlatformAuthAccessTokenResponse();

    in.beginObject();

    while (in.hasNext()) {
      switch (in.nextName()) {
        case Convention.OPEN_PLATFORM_AUTH_ACCESS_TOKEN_KEY: {
          model.setAccessToken(in.nextString());
          break;
        }
        case Convention.WEIXIN_ACCESS_TOKEN_EXPIRES_IN_KEY: {
          model.setExpiresIn(in.nextInt());
          break;
        }
      }
    }

    in.endObject();

    return model;
  }

}
