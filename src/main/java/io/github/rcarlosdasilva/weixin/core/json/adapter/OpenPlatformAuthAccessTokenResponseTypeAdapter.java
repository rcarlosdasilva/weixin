package io.github.rcarlosdasilva.weixin.core.json.adapter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.github.rcarlosdasilva.weixin.common.Convention;
import io.github.rcarlosdasilva.weixin.model.response.open.auth.OpenPlatformAuthAccessTokenResponse;

public class OpenPlatformAuthAccessTokenResponseTypeAdapter
    extends TypeAdapter<OpenPlatformAuthAccessTokenResponse> {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void write(JsonWriter out, OpenPlatformAuthAccessTokenResponse value) throws IOException {
    // 不需要实现
  }

  @Override
  public OpenPlatformAuthAccessTokenResponse read(JsonReader in) throws IOException {
    OpenPlatformAuthAccessTokenResponse model = new OpenPlatformAuthAccessTokenResponse();

    in.beginObject();
    while (in.hasNext()) {
      String key = in.nextName();
      switch (key) {
        case Convention.OPEN_PLATFORM_AUTH_ACCESS_TOKEN_KEY:
          model.setAccessToken(in.nextString());
          break;

        case Convention.WEIXIN_ACCESS_TOKEN_EXPIRES_IN_KEY:
          model.setExpiresIn(in.nextInt());
          break;
        default:
          if (in.hasNext()) {
            String value = in.nextString();
            logger.warn("未知的json键值： [{}: {}]", key, value);
          }
      }
    }
    in.endObject();

    return model;
  }

}
