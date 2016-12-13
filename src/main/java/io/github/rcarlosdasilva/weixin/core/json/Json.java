package io.github.rcarlosdasilva.weixin.core.json;

import com.google.common.base.Preconditions;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON工具
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public final class Json {

  private static final Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
      .disableHtmlEscaping().addSerializationExclusionStrategy(new ExclusionStrategy() {

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
          Freeze anno = field.getAnnotation(Freeze.class);
          return anno != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
          // TODO Auto-generated method stub
          return false;
        }
      }).create();

  public static <T> String toJson(Object obj, Class<T> clazz) {
    Preconditions.checkNotNull(obj);
    return gson.toJson(obj, clazz);
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    Preconditions.checkNotNull(json);
    return gson.fromJson(json, clazz);
  }

}
