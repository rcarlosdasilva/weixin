package io.github.rcarlosdasilva.weixin.core.inspect;

import java.util.Collection;
import java.util.HashMap;

public class ProblemObject<T> extends HashMap<String, Object> {

  private static final long serialVersionUID = 7274480696501276585L;

  private static final String DEFAULT_DATA_KEY = "__object__";
  private static final String DEFAULT_DATAS_KEY = "__objects__";

  private final Class<T> type;
  private final String mark;

  public ProblemObject(Class<T> type, String mark) {
    this.type = type;
    this.mark = mark;
  }

  public Class<T> getType() {
    return type;
  }

  public String getMark() {
    return mark;
  }

  public void setDefaultObject(T value) {
    this.put(DEFAULT_DATA_KEY, value);
  }

  public void setDefaultObjects(Collection<T> values) {
    this.put(DEFAULT_DATAS_KEY, values);
  }

  @SuppressWarnings("unchecked")
  public T getDefaultObject() {
    return (T) this.get(DEFAULT_DATA_KEY);
  }

  @SuppressWarnings("unchecked")
  public Collection<T> getDefaultObjects() {
    return (Collection<T>) this.get(DEFAULT_DATAS_KEY);
  }

}
