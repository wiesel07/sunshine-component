package cn.sunshine.component.log.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * <p>
 * 日志注解
 * </p>
 *
 * @author wuj
 * @since 2020年4月9日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Log {

	@AliasFor("desc")
	String value() default "";
	
	@AliasFor("value")
	String desc() default "";
	
	boolean ignore() default false;
}
