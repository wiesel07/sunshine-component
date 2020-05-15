package cn.sunshine.component.runtime.web.interceptor;

import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * <p>
 * 拦截接口类便于框架统一设置
 * </p>
 *
 * @author wuj
 * @since 2019年7月18日
 */
public interface WebInterceptor extends AsyncHandlerInterceptor {

	/**
	 * 当前拦截器处理顺序 越小 越先执行
	 * 
	 * @return
	 */
	int order();

	/**
	 * 拦截的URL模式
	 * 
	 * @return
	 */
	String[] patternUrl();
}
