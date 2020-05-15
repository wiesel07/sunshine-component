package cn.sunshine.common.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import cn.sunshine.common.enums.BusinessError;
import cn.sunshine.component.common.annotation.NoAuthLogin;
import cn.sunshine.component.common.exception.SunshineException;
import cn.sunshine.component.common.util.StrUtilExt;
import cn.sunshine.component.runtime.web.entity.UserVo;
import cn.sunshine.component.runtime.web.interceptor.WebInterceptor;
import cn.sunshine.component.runtime.web.session.CommonSession;
import cn.sunshine.component.runtime.web.session.ISessionService;
import cn.sunshine.component.runtime.web.session.TokenManager;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月1日
 */
@Slf4j
@Component
public class AuthInterceptor implements WebInterceptor {

	
	@Autowired
	private ISessionService sessionService;

	public static final String TOKEN_FLAG = "sunshineToken";

//	@Value("#{'${spring.profiles.active}' == 'dev'}")
//	public boolean isDebug = false;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		NoAuthLogin nologinAnnotation;

//		if (isDebug) {
//			return true ;
//		}

		if (handler instanceof HandlerMethod) {
			nologinAnnotation = ((HandlerMethod) handler).getMethodAnnotation(NoAuthLogin.class);
		} else {
			return true;
		}

		// 无需登录校验
		if (nologinAnnotation != null) {
			return true;
		}

		// 获取token
		String token = getToken(request, response);
		CommonSession commonSession = sessionService.get(token);
		if (commonSession == null) {
			UserVo userVo =new UserVo();
			userVo.setUserCode("007");
			userVo.setUserName("邦德");
			
			commonSession =new CommonSession();
			commonSession.setUser(userVo);
//			log.error("缓存中token不存在或者已过期");
//			throw BusinessError.SESSION_TIMEOUT.toException();
		}
		log.info("commonSession:{}",commonSession);
		// 重置过期时间
		sessionService.set(commonSession);
		// 设置系统上下文
		TokenManager.putSession(commonSession);
		return true;
	}

	private String getToken(HttpServletRequest httpServletRequest, HttpServletResponse response) throws SunshineException {
		String token = httpServletRequest.getParameter(TOKEN_FLAG);
		if (StrUtilExt.isEmpty(token)) {
			Cookie[] cookies = httpServletRequest.getCookies();
			if (cookies == null)
				throw BusinessError.NO_LOGIN.toException();
			for (Cookie cookie : cookies) {
				if (TOKEN_FLAG.equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
			if (StrUtilExt.isBlank(token)) {
				log.error("该请求不存在token");
				throw BusinessError.NO_LOGIN.toException();
			}

		}
		return token;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		 TokenManager.removeSession();
	}
	
	@Override
	public int order() {
		return 0;
	}

	@Override
	public String[] patternUrl() {
		return new String[] { "/**"};
	}

}
