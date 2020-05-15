package cn.sunshine.component.attach.enums;

import cn.sunshine.component.common.api.IErrorCode;
import cn.sunshine.component.common.exception.SunshineException;

/**
 * <p>
 * 附件错误枚举类
 * </p>
 *
 * @author wuj
 * @since 2020-03-16
 */
public enum AttachError implements IErrorCode {

	ATTACH_NOT_EXISTS("AT001000", "上传文件不存在!"),
	ATTACH_NOT_EMPTY("AT001001", "上传文件大小不能为空!"),
	ATTACH_FILE_NOT_EXISTS("AT001002", "文件不存在!"),
	ATTACH_FILE_UPLOAD_EXCEPTION("AT002000", "文件上传失败:%s"),	
	ATTACH_FILE_GET_UPLOAD_EXCEPTION("AT002001", "获取上传文件异常:%s"),

	ATTACH_ITEM_NOT_UPLOAD("AT004000", "附件项%s未上传!"),

	ATTACH_DOWNLOAD_FAIL("AT999999","下载失败"),
	CHECK_PARAMS_VALID("1001001", "%S"),

	;

	String code;
	String msg;

	AttachError(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

//	public SunshineException toException(Object... args) {
//		return new SunshineException(this.code, String.format(msg, args));
//	}

	public SunshineException toException(Object... args) {
		return new SunshineException(this,args);
	}
}
