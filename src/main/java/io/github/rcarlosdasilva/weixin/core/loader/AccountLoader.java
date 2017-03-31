package io.github.rcarlosdasilva.weixin.core.loader;

import java.util.List;
import java.util.Map;

import io.github.rcarlosdasilva.weixin.model.Account;

/**
 * 微信公众号加载器
 *
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
public interface AccountLoader {

  /**
   * 加载需要注册的微信公众号信息，加载后会自动注册.
   * <p>
   * 注册时默认使用公众号的AppId作为Key，需要使用其他值做Key，可使用{@link #loadAsMap()}。两个方法只加载一个，优先加载{@link #loadAsMap()}
   * 
   * @return 公众号集合
   */
  List<Account> loadAsList();

  /**
   * 加载需要注册的微信公众号信息，加载后会自动注册.
   * <p>
   * 返回的Map中，Key就是注册公众号时使用的Key。与{@link #loadAsList()}两个方法只加载一个，优先加载{@link #loadAsMap()}
   * 
   * @return 公众号集合
   */
  Map<String, Account> loadAsMap();

}
