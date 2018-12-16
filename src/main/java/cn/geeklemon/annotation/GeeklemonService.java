package cn.geeklemon.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 	该注解注释的类将被注册为服务提供类
 * @author kavingu
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface GeeklemonService {
	String value() default "";
}
