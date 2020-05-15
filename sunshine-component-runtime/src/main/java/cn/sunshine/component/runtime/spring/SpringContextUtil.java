package cn.sunshine.component.runtime.spring;

import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.util.ClassUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * spring上下文工具类,支持自定义的上下文
 * </p>
 *
 * @author wuj
 * @since 2019年7月18日
 */
@Slf4j
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class SpringContextUtil {
	
	private static final String SPRING_APPLICATION_CONTEXT_AWARE_CLASS = "cn.sunshine.component.runtime.spring.RuntimeApplicationContext";

	private static Class getSpringApplicationContextAwareClass() throws Exception {
		return ClassUtils.forName(SPRING_APPLICATION_CONTEXT_AWARE_CLASS,
				Thread.currentThread().getContextClassLoader());
	}

	/**
	 * 获取Spring Bean
	 * 
	 * @param beanName bean名称
	 * @return
	 */
	public static <E> E getBean(String beanName) {
		try {
			Class applicationContextAwareClass = getSpringApplicationContextAwareClass();
			Object result = MethodUtils.invokeStaticMethod(applicationContextAwareClass, "getBean",
					new Object[] { beanName });
			return (E) result;
		} catch (Exception e) {
			log.error("Error get spring applicationContext bean {}", beanName, e);
			throw new RuntimeException(e);
		}
	}

	public static <E> E getBeanByType(Class<? extends E> type) {
		try {
			Class applicationContextAwareClass = getSpringApplicationContextAwareClass();
			Object result = MethodUtils.invokeStaticMethod(applicationContextAwareClass, "getBeanByType",
					new Object[] { type });
			return (E) result;
		} catch (Exception e) {
			log.error("Error get spring applicationContext bean of type {}", type.getClass(), e);
			throw new RuntimeException(e);
		}
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		try {
			Class applicationContextAwareClass = getSpringApplicationContextAwareClass();
			Object result = MethodUtils.invokeStaticMethod(applicationContextAwareClass, "getBeansOfType",
					new Object[] { type });
			return (Map<String, T>) result;
		} catch (Exception e) {
			log.error("Error get spring applicationContext bean of type {}", type.getClass(), e);
			throw new RuntimeException(e);
		}
	}

	public static void initAutowireFields(Object instance) {
		try {
			Class applicationContextAwareClass = getSpringApplicationContextAwareClass();
			MethodUtils.invokeStaticMethod(applicationContextAwareClass, "initAutowireFields",
					new Object[] { instance });
		} catch (Exception e) {
			log.error("Error get spring applicationContext initAutowireFields {}", instance.getClass(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据注解获取Bean
	 * 
	 * @param type
	 * @return
	 */
	public static Map<String, Object> getBeansWithAnnotation(Class type) {
		try {
			Class applicationContextAwareClass = getSpringApplicationContextAwareClass();
			Object result = MethodUtils.invokeStaticMethod(applicationContextAwareClass, "getBeansWithAnnotation",
					new Object[] { type });
			return (Map<String, Object>) result;
		} catch (Exception e) {
			log.error("Error get spring applicationContext bean with annotation {}", type.getClass(), e);
			throw new RuntimeException(e);
		}
	}
}
