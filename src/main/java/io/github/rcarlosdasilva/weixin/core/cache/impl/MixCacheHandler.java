package io.github.rcarlosdasilva.weixin.core.cache.impl;

/**
 * 存储混杂属性
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public class MixCacheHandler extends AbstractCacheHandler<Object> {

  private static final String DEFAULT_MARK = "mc_";
  private static final MixCacheHandler instance = new MixCacheHandler();

  private MixCacheHandler() {
    this.mark = DEFAULT_MARK;
  }

  public static MixCacheHandler getInstance() {
    return instance;
  }

}
