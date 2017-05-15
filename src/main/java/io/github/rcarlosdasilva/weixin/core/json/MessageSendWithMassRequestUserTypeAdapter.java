package io.github.rcarlosdasilva.weixin.core.json;

import java.io.IOException;

import com.google.common.base.Strings;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.github.rcarlosdasilva.weixin.model.request.message.MessageSendWithMassRequestUser;

public class MessageSendWithMassRequestUserTypeAdapter
    extends TypeAdapter<MessageSendWithMassRequestUser> {

  @Override
  public void write(JsonWriter out, MessageSendWithMassRequestUser value) throws IOException {
    if (!Strings.isNullOrEmpty(value.getUser())) {
      out.value(value.getUser());
    } else if (value.getUsers() != null && value.getUsers().size() > 1) {
      out.beginArray();
      for (String user : value.getUsers()) {
        out.value(user);
      }
      out.endArray();
    } else {
      out.nullValue();
    }
  }

  @Override
  public MessageSendWithMassRequestUser read(JsonReader in) throws IOException {
    return null;
  }

}
