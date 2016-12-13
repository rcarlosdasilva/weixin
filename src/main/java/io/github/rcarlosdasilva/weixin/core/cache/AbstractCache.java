package io.github.rcarlosdasilva.weixin.core.cache;

public abstract class AbstractCache<T> implements Cache<T> {

  @Override
  public T lookup(Object value) {
    return null;
  }

}
