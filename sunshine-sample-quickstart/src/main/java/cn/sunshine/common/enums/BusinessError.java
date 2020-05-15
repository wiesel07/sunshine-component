package cn.sunshine.common.enums;

import cn.sunshine.component.common.api.IErrorCode;
import cn.sunshine.component.common.exception.SunshineException;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author wuj
 * @since 2020年4月1日
 */
public enum BusinessError implements IErrorCode {

	BIZ_WARN("1000000", "%s"), 
	CHECK_FAIL("1001001", "参数校验失败[%s]%s"),
	CHECK_PARAMS_VALID("1001001", "%S"),
	CHECK_NOT_READABLE("1001002", "参数无效或格式异常"), 
	CHECK_NOT_HANDLER("101004", "非法访问"),
	NOT_PERMISSION("101009", "无此访问权限"),
	SESSION_TIMEOUT("1002004", "系统闲置时间过长，请重新登录"), // 返回登录界面
	NO_LOGIN("1002000", "未登录!"), // 返回登录界面
	VAPTCHA_CODE_ERROR("1002001", "验证码错误!"), OPERATOR_CODE_OR_PWD_ERROR("1002002", "账号或者密码错误"),
	OPERATOR_STATUS_ERROR("1002003", "账号状态异常"), OLD_PWD_ERROR("10022004", "原始密码错误"), TIME_OUT("1008999", "请求超时"), // 返回登录界面
	SQL_ERROR("1008998", "系统异常"), SYS_ERROR("1008999", "系统异常[%s]");

	private final String code;
	private final String msg;

	BusinessError(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}
	
	public SunshineException toException(Object... args) {
		return new SunshineException(this,args);
	}


}
