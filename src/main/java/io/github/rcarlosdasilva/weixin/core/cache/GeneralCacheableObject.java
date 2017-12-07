package io.github.rcarlosdasilva.weixin.core.cache;

import java.io.Serializable;

public class GeneralCacheableObject implements Serializable, Cacheable {

  private static final long serialVersionUID = 6464095895660138441L;

  private Object obj;

  public GeneralCacheableObject(Object obj) {
    this.obj = obj;
  }

  public Object getObj() {
    return obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }

}
