package cn.sunshine.biz.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sunshine.biz.controller.req.LoginReq;
import cn.sunshine.biz.controller.resp.LoginResp;
import cn.sunshine.biz.controller.resp.VaptchaCodeResp;
import cn.sunshine.common.constant.BizConstant;
import cn.sunshine.common.enums.BusinessError;
import cn.sunshine.component.common.annotation.NoAuthLogin;
import cn.sunshine.component.common.api.CommonResp;
import cn.sunshine.component.common.util.DateUtilExt;
import cn.sunshine.component.common.util.IDUtils;
import cn.sunshine.component.common.util.IPUtils;
import cn.sunshine.component.common.util.RandomStringUtil;
import cn.sunshine.component.common.util.StrUtilExt;
import cn.sunshine.component.common.util.ValidateCodeUtil;
import cn.sunshine.component.log.biz.entity.SysLoginLog;
import cn.sunshine.component.log.biz.service.ISysLoginLogService;
import cn.sunshine.component.log.common.annotation.Log;
import cn.sunshine.component.log.common.thread.LogThreadPoolHelper;
import cn.sunshine.component.log.common.thread.LoginLogThread;
import cn.sunshine.component.redis.service.IRedisService;
import cn.sunshine.component.runtime.web.session.CommonSession;
import cn.sunshine.component.runtime.web.session.ISessionService;
import cn.sunshine.component.runtime.web.session.TokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月11日
 */
@Api(tags = "权限控制 API")
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

	@Autowired
	private IRedisService redisService;

	@Autowired
	private ISessionService sessionService;

	@Autowired
	private ISysLoginLogService loginLogService;

	@Value("${vaptcha.code.enable}")
	private boolean vaptchaCodeEnable;

	@Log(ignore = true)
	@NoAuthLogin
	@PostMapping("/login")
	@ApiOperation(value = "登录api", notes = "第一次登录异常后，需要获取验证码(启动验证码时)")
	public CommonResp<LoginResp> login(@RequestBody @Valid LoginReq loginReq, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("登录信息：{}", loginReq);
		String sessionId = request.getSession().getId();
		if (vaptchaCodeEnable) {
			// 开启验证码
			if (redisService.hasKey(getCodeKey(sessionId))) {
				String randomCode = (String) redisService.get(getCodeKey(sessionId));
				// 进行验证码判断
				if (!StrUtilExt.endWithIgnoreCase(randomCode, loginReq.getVaptchaCode())) {
					// 验证码失效或者不正确
					throw BusinessError.VAPTCHA_CODE_ERROR.toException();
				}
				//
			}
		}
//		// 判断密码是否正确
//		if (user == null ) {
//			// 密码错误 启动验证码
//			if (vaptchaCodeEnable) {
//				String randomCode =(String)redisService.get(getCodeKey(sessionId));
//				if (!StrUtilExt.isBlank(randomCode)) {
//					redisService.set(getCodeKey(sessionId), randomCode, 10);
//				} 
//			}
//			throw BusinessError.OPERATOR_CODE_OR_PWD_ERROR.toException();
//		}

		// 获取用户机构信息

		// 获取用户菜单信息

		// 返回用户信息

		// user.getUserTypeCode(), roleIds);
		LoginResp loginResp = new LoginResp();
		loginResp.setUserId(1L);
		loginResp.setUserCode("admin");
		loginResp.setUserName("测试");
		loginResp.setUserType("测试用户");
		loginResp.setModifiedPassword(true);// 未激活 需要强制修改密码

		CommonSession session = new CommonSession();
		session.setSid(IDUtils.new32UUID());
		session.setIp(IPUtils.getIpAddr(request));
		session.setLogin(true);
		session.setLoginTime(DateUtilExt.now());

		sessionService.set(session);
		// 设置token
		Cookie cookie = new Cookie(BizConstant.TOKEN_FLAG, session.getSid());
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		// 更新用户信息

		redisService.del(getCodeKey(sessionId));

		// 异步保存登录日志
		SysLoginLog loginLog = new SysLoginLog();
		LogThreadPoolHelper.FIXED_THREAD_POOL.execute(new LoginLogThread(loginLog, loginLogService));

		return new CommonResp<LoginResp>(loginResp);

	}

	@PostMapping("/logout")
	@ApiOperation(value = "退出登录", notes = "退出登录")
	public CommonResp<String> logout(HttpServletRequest request, HttpServletResponse response) {
		CommonSession commonSession = TokenManager.getSession();

		// userService.updateLoginoutInfo(commonSession.getUser().getUserId());// 更新登出时间
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				// 移除token
				if (BizConstant.TOKEN_FLAG.equals(cookie.getName())) {
					String token = cookie.getValue();
					sessionService.remove(token);
				}
			}
		}
		// 更新登录日志 用户退出时间
		return new CommonResp<>("");
	}
	
	
	@GetMapping("/vaptchaCode")
	@ApiOperation(value = "获取验证码", notes = "获取验证码")
	public CommonResp<VaptchaCodeResp> getVaptchaCode(HttpServletRequest request, HttpServletResponse response) {
		VaptchaCodeResp resp = new VaptchaCodeResp();
		// String imgKey = IDUtils.newGUID();
		String randomCode = RandomStringUtil.getNumber(4);
		log.info("验证码：{}", randomCode);
		resp.setImg(ValidateCodeUtil.getImageBase(randomCode));
		String sessionId = request.getSession().getId();
		// 五分钟
		redisService.set(getCodeKey(sessionId), randomCode, 300);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		return new CommonResp<VaptchaCodeResp>(resp);
	}


	private String getCodeKey(String key) {
		return BizConstant.CACHE_VAPTCHA_CODE + key;
	}
}
