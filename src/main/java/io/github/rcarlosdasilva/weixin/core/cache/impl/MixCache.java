package io.github.rcarlosdasilva.weixin.core.cache.impl;

/**
 * 存储混杂属性
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MixCache extends AbstractCache<Object> {

  private static final String DEFAULT_MARK = "mc_";
  private static final MixCache instance = new MixCache();

  private MixCache() {
    this.mark = DEFAULT_MARK;
  }

  public static MixCache getInstance() {
    return instance;
  }

}
