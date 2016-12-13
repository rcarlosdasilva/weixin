package io.github.rcarlosdasilva.weixin.core.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记某个字段不被Gson序列化为JSON字符串
 * 
 * @author Dean Zhao (rcarlosdasilva@qq.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Freeze {

}
