package io.github.rcarlosdasilva.weixin.core.cache;

public interface Lookup<V extends Cacheable> {

  boolean isYou(String key, V obj);

}
