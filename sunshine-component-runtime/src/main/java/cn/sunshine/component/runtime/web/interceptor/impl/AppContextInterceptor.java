package cn.sunshine.component.runtime.web.interceptor.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import cn.sunshine.component.common.util.IDUtils;
import cn.sunshine.component.common.util.StrUtilExt;
import cn.sunshine.component.runtime.web.context.AppContext;
import cn.sunshine.component.runtime.web.interceptor.WebInterceptor;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2019年7月18日
 */
@Component
public class AppContextInterceptor implements WebInterceptor {

	/**
	 * 日志跟踪标识
	 */
	private static final String TRACE_ID = "TRACE_ID";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		AppContext appContext = new AppContext();
		AppContext.registerAppContext(appContext);

		// String traceId = UUID.randomUUID().toString();
		String traceId = IDUtils.new32UUID();
		appContext.setTraceId(traceId);
		if (StrUtilExt.isEmpty(MDC.get(TRACE_ID))) {
			MDC.put(TRACE_ID, traceId);
		}
		return true;
	}

//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) {
//		
//	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.remove(TRACE_ID);
		AppContext.unregisterAppContext();
	}

	@Override
	public int order() {
		return 1;
	}

	@Override
	public String[] patternUrl() {
		return new String[] { "**/*" };
	}

}
