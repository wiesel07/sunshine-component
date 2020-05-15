package cn.sunshine.component.runtime.web.context;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 框架系统上下文对象
 * </p>
 *
 * @author wuj
 * @since 2019年7月18日
 */
@Slf4j
public class AppContext {

	private static ThreadLocal<AppContext> appContext = new ThreadLocal<AppContext>();


	/**
	 * spring的应用上下文
	 */
//	private static org.springframework.context.ApplicationContext springApplicationContext = null;

	/**
	 * 注册当前上下文对象
	 * 
	 * @param context 上下文对象
	 */
	public static void registerAppContext(AppContext context) {
		appContext.set(context);
	}

	/**
	 * 反注册当前上下文对象
	 */
	public static void unregisterAppContext() {
		appContext.remove();
	}

	public static AppContext getAppContext() {
		AppContext context = appContext.get();
		if (context != null)
			return context;
		synchronized (appContext) {
			context = appContext.get();
			if (context != null)
				return context;
			context = new AppContext();
			registerAppContext(context);
		}
		return context;
	}
	
	@Getter
	@Setter
	private String traceId;

//	/**
//	 * 根据类名搜索相应的bean对象
//	 * 
//	 * @param       <T> 类模板标识返回值类型
//	 * @param clazz 模板类
//	 * @return bean对象
//	 */
//	public <T> T lookup(Class<T> clazz) {
//		if (getBeanContext() == null)
//			return null;
//		return getBeanContext().getBean(clazz);
//	}
//
//	/**
//	 * 根据类型取得所有
//	 * 
//	 * @param type 接口类
//	 * @return 取得所有的实现bean
//	 */
//	public <T> Map<String, T> getBeansOfType(Class<T> type) {
//		return getBeanContext().getBeansOfType(type);
//	}
//
//	/**
//	 * 根据bean名称和bean类型搜索相应的bean对象
//	 * 
//	 * @param          <T> 类模板标识返回值类型
//	 * @param beanName bean名称
//	 * @param clazz    模板类
//	 * @return bean对象
//	 */
//	public <T> T lookup(String beanName, Class<T> clazz) {
//		if (getBeanContext() == null)
//			return null;
//		return getBeanContext().getBean(beanName, clazz);
//	}
//
//	/**
//	 * 根据bean名称搜索相应的bean对象
//	 * 
//	 * @param beanName bean名称
//	 * @return bean对象
//	 */
//	public Object lookup(String beanName) {
//		if (getBeanContext() == null)
//			return null;
//		return getBeanContext().getBean(beanName);
//	}
//
//	/**
//	 * 取得Bean的上下文对象
//	 * 
//	 * @return Bean的上下文对象
//	 */
//	public org.springframework.context.ApplicationContext getBeanContext() {
//		return getSpringApplicationContext();
//	}
//
//	/**
//	 * 取得spring的上下文对象
//	 * 
//	 * @return spring的上下文对象
//	 */
//	private static org.springframework.context.ApplicationContext getSpringApplicationContext() {
//		if (springApplicationContext == null) {
//			log.info("获取spring上下文对象");
//			springApplicationContext = RuntimeApplicationContext.getContext();
//		}
//		return springApplicationContext;
//	}



}
