package cn.sunshine.component.log.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sunshine.component.common.util.DateUtilExt;
import cn.sunshine.component.common.util.IPUtils;
import cn.sunshine.component.common.util.JsonUtil;
import cn.sunshine.component.common.util.StrUtilExt;
import cn.sunshine.component.log.biz.entity.SysOperLog;
import cn.sunshine.component.log.biz.service.ISysOperLogService;
import cn.sunshine.component.log.common.annotation.Log;
import cn.sunshine.component.log.common.enums.LogStatusEnum;
import cn.sunshine.component.log.common.thread.LogThreadPoolHelper;
import cn.sunshine.component.log.common.thread.OperLogThread;
import cn.sunshine.component.runtime.web.session.CommonSession;
import cn.sunshine.component.runtime.web.session.TokenManager;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月10日
 */
@EnableAspectJAutoProxy
@Aspect
@Component
@Slf4j
public class LogAspect {

//	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//	public void logPointCut() {
//
//	}
	@Autowired
	ISysOperLogService operLogService;
	
	@Pointcut("@annotation(cn.sunshine.component.log.common.annotation.Log)")

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long beginTime = System.currentTimeMillis();
		ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		String uri = requestAttr.getRequest().getRequestURI();
		if (log.isDebugEnabled()) {
			log.debug("开始计时: {}  URI: {}",DateUtilExt.now(), uri);
		}
		// 执行目标操作
		Object object = joinPoint.proceed();
		// 执行时长(毫秒)
		long endTime = System.currentTimeMillis();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		// 判断是否包含了 无需记录日志的注解
		Log log = AnnotationUtils.getAnnotation(method, Log.class);

		// 忽略日志
		if (log == null || log.ignore()) {
			return object;
		}
		// 异步存储日志
		saveLog(joinPoint, log, beginTime, endTime);
		return object;
	}

	private void saveLog(ProceedingJoinPoint joinPoint, Log log, long beginTIme, long endTime) {
		SysOperLog operLog = new SysOperLog();
		HttpServletRequest request = getHttpServletRequest();
		operLog.setOperIp(IPUtils.getIpAddr(request));
		operLog.setStartTime(DateUtilExt.date(beginTIme));
		operLog.setEndTime(DateUtilExt.date(endTime));
		operLog.setTotalTime(endTime - beginTIme);
		operLog.setReqUrl(request.getRequestURI());
		operLog.setStatus(LogStatusEnum.SUCCESS.getValue());
		// 请求的参数
		Object[] args = joinPoint.getArgs();
		try {
			String params = JsonUtil.toJsonString(args[0]);
			operLog.setReqParam(params);
		} catch (Exception e) {

		}
		if (log != null) {
			operLog.setOperDesc(log.desc());
			operLog.setRemark("");
		} else {
			operLog.setOperDesc("");
			operLog.setRemark("");
		}
		// 获取用户信息
		CommonSession commonSession = TokenManager.getSession();
		if (StrUtilExt.isBlank(commonSession.getSid())) {
			operLog.setUserId(-1L);
			operLog.setUserCode("-");
			operLog.setUserName("-");
		} else {
			operLog.setUserId(commonSession.getUser().getUserId());
			operLog.setUserCode(commonSession.getUser().getUserCode());
			operLog.setUserName(commonSession.getUser().getUserName());
		}
		
		// 获取操作说明
		ApiOperation apiOperation = ((MethodSignature) joinPoint.getSignature()).getMethod()
				.getAnnotation(ApiOperation.class);
		if (apiOperation!=null) {
			operLog.setRemark(apiOperation.value());
		}
		
		// 异步保存日志
		LogThreadPoolHelper.FIXED_THREAD_POOL.execute(new OperLogThread(operLog, operLogService));
	}

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	
//	/**
//	 * 处理完请求后执行
//	 *
//	 * @param joinPoint 切点
//	 */
//	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
//	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
//		handleLog(joinPoint, null, jsonResult);
//	}
//
//	/**
//	 * 拦截异常操作
//	 * 
//	 * @param joinPoint 切点
//	 * @param e         异常
//	 */
//	@AfterThrowing(value = "logPointCut()", throwing = "e")
//	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
//		handleLog(joinPoint, e, null);
//	}
	
}
